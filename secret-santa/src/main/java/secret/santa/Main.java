package secret.santa;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFileLocation;
import static spark.Spark.stop;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import org.apache.commons.lang3.StringUtils;

import org.apache.commons.lang3.builder.ToStringBuilder;

import secret.santa.ConfigCache.UserConfig;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;


import com.google.gson.Gson;

public class Main
{
    private static final int TIMEOUT = 3600;

    private final ConfigCache config;

    public static void main(String[] args) throws Exception
    {
        new Main(args[0]).init();
    }

    private Main(String configFile)
    {
        config = new ConfigCache(configFile);
    }

    public void initUsers() throws Exception{
        if (config.getConfig().containsKey("user.config")) return;
        config.setUser(UserConfig.with().username("robert.obrien").password("test").firstName("Rob").lastName("O'Brien").email("robert.obrien@landmark.co.uk"));
        config.getAllUsers().entrySet().forEach(e -> System.out.println("KEY=[" + e.getKey() + "], VALUE=[" + e.getValue() + "]"));
    }
    
    public void init() throws Exception
    {
        initUsers();
        
        port(config.getConfig().getInt("port", 2512));
        staticFileLocation("/static");

        before((req, res) -> {
            
            System.out.println("URI=[" + req.uri() + "], USER=[" + req.session().attribute("username") + "]");
            
            if (req.uri().equals("/login") || req.uri().equals("/")) return;
            if (StringUtils.isBlank(req.session().attribute("username"))) halt(401, "No user session");
        });

        get("/", (req, res) -> {
            return new ModelAndView(null, "index.html");
        }, new FreeMarkerEngine());

        get("/stop", (req, res) -> {
            stop();
            return FluentMap.withObject().keyValue("stop", new Date()).toJson();
        });

        post("/login",
                (req, res) -> {
                    try {
                        Map<String, String> authData = jsonToMap(req.body());
                        UserConfig userConfig = config.getUser(StringUtils.lowerCase(authData.get("username")));
                        System.out.println("USER=[NAME=[" + StringUtils.lowerCase(authData.get("username")) + "], DETAIL=[" + userConfig + "]]");
                        
                        if (userConfig == null || !userConfig.password().equals(authData.get("password")))
                            halt(401, "Username or password is incorrect");
                        req.session().attribute("username", authData.get("username"));
                        
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return "success";
                });

//        get("/logout", (req, res) -> {
//            req.session().removeAttribute("username");
//            return new ModelAndView(null, "index.html");
//        }, new FreeMarkerEngine());

        get("/logout", (req, res) -> {
            req.session().removeAttribute("username");
                res.redirect("/");
                return "";
            });
        
        get("/validate", (req, res) -> {
           return ""; 
        });
    }

    private Map<String, String> jsonToMap(String json)
    {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<String, String>();
        return (Map<String, String>) gson.fromJson(json, map.getClass());
    }
}
