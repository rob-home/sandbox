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
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import com.google.gson.Gson;

public class Main
{
    private static final int TIMEOUT = 3600;
    
    private final ConfigCache config;
    public static void main(String [] args) throws Exception {
        new Main(args[0]).init();
    }

    private Main(String configFile) {
        config = new ConfigCache(configFile);
    }
    
    
    public void init() throws Exception {
        config.setConfiguration("test.value", "hello");
        port(2512);
        staticFileLocation("/static");
                
      before((req, res) -> {
          System.out.println("URI=[" + req.uri() + "]");
          if (req.uri().equals("/login") || req.uri().equals("/")) return;
          System.out.println("SESSION=[" + req.session().attribute("username") + "]");
          if (StringUtils.isBlank(req.session().attribute("username"))) halt(401, "No user session");
      });
  
//      before((req, res) -> {
//      String uri = req.uri();
//      String username = req.cookie("username");
//      String sessionid = req.cookie("sessionid");
//      if (!uri.equals("/login") && !uri.equals("/")) {
//          System.out.println("VALIDATE");
//          if (StringUtils.isBlank(username) || StringUtils.isBlank(sessionid)) res.redirect("/");
//          if (!sessionid.equals(config.get(username + ".sessionid"))) res.redirect("/");
//      }
//  });
  
        get("/", (req, res) -> {
            return new ModelAndView(null, "index.html");
        }, new FreeMarkerEngine());
        
        get("/stop", (req, res) -> {
            stop();
            return FluentMap.withObject().keyValue("stop", new Date()).toJson();
        });
        
        post("/login", (req, res) -> {
            Map<String, String> authData = jsonToMap(req.body());
            if(!config.get(StringUtils.lowerCase(authData.get("username")) + ".password").equals(authData.get("password")))
                halt(401, "Username or password is incorrect");
            req.session().attribute("username", authData.get("username"));
//            res.cookie("username", authData.get("username"), TIMEOUT);
//            res.cookie("sessionid", config.setValue(authData.get("username") + ".sessionid", UUID.randomUUID().toString()), TIMEOUT);
            return "success";
        });
        
        get("/logout", (req, res) -> {
            req.session().removeAttribute("username");
//            res.cookie("username", "", 0);
//            res.cookie("sessionid", "", 0);
            res.redirect("/");
            return "";
        });
    }
    
    private Map<String, String> jsonToMap(String json) {
        Gson gson = new Gson(); 
        Map<String,String> map = new HashMap<String,String>();
        return (Map<String,String>) gson.fromJson(json, map.getClass());
    }
}
