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

import javax.crypto.EncryptedPrivateKeyInfo;

import org.apache.commons.lang3.StringUtils;

import secret.santa.ConfigCache.UserConfig;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import com.google.gson.Gson;

public class Main
{
    private static final int TIMEOUT = 3600;

    private static final FluentList<UserConfig> DEFAULT_USERS = FluentList.<UserConfig>with()
            .item(UserConfig.with().username("robert.obrien").password(Encryption.hash("@test")).firstName("Rob").lastName("O'Brien").email("robert.obrien@landmark.co.uk"));
    
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
        final FluentMap<String, UserConfig> userConfig = config.getAllUsers();
        DEFAULT_USERS.forEach(u -> {
            if (!userConfig.containsKey(u.username())) userConfig.put(u.username(), u);
        });
        config.setAllUsers(userConfig);
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
            if (StringUtils.isBlank("" + req.session().attribute("username"))) halt(401, "No user session");
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
                        
                        if (userConfig != null && 
                                userConfig.password().equals(StringUtils.startsWith(authData.get("password"), "@") ? authData.get("password") : Encryption.hash(authData.get("password")))) {
                            req.session().attribute("username", authData.get("username"));
                            return "success";
                        }
                        
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    halt(401, "Username or password is incorrect");
                    return "";
        });

        get("/logout", (req, res) -> {
            req.session().removeAttribute("username");
                res.redirect("/");
                return "";
            });
        
        get("/validate", (req, res) -> {
           return ""; 
        });
        
        get("/userdata", (req, res) -> {
            return ""; 
        });
         
        post("/userdata", (req, res) -> {
            return ""; 
        });
         
        post("/reset/password", (req, res) -> {
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
