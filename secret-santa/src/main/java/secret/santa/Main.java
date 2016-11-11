package secret.santa;

import static spark.Spark.*;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

public class Main
{
    public static void main(String [] args) throws Exception {
        
        staticFileLocation("/static");
        
        get("/hello", (req, res) -> {
            return new ModelAndView(null, "trial001.html");
        }, new FreeMarkerEngine());
        
        get("/stop", (req, res) -> {
            stop();
            return "stop";
        });
    }
}
