package secret.santa;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.staticFileLocation;
import static spark.Spark.stop;

import java.util.Date;

import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

public class Main
{
    private final ConfigCache config;
    public static void main(String [] args) throws Exception {
        new Main(args[0]).init();
    }

    private Main(String configFile) {
        config = new ConfigCache(configFile);
    }
    
    
    public void init() throws Exception {
        
        System.out.println(config.toString());
        
        config.setConfiguration("test.value", "hello");

        System.out.println(config.toString());

        port(2512);
        staticFileLocation("/static");
        
        before((req, res) -> {
            System.out.println("001 - " + req.uri());
            System.out.println("002 - " + req.url());
            System.out.println("003 - " + req.contextPath());
            System.out.println("004 - " + req.servletPath());
        });
        
        get("/hello", (req, res) -> {
            return new ModelAndView(null, "trial001.html");
        }, new FreeMarkerEngine());
        
        get("/stop", (req, res) -> {
            stop();
            return FluentMap.withObject().keyValue("stop", new Date()).toJson();
        });
    }
}
