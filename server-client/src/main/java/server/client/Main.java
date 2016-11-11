package server.client;
import static spark.Spark.*;

import java.net.ServerSocket;


public class Main
{
    public static void main(String [] args) throws Exception
    {
        ServerSocket socket = new ServerSocket(0);
        int port = socket.getLocalPort();
        socket.close();

        System.out.println("PORT=[" + port + "]");
        
        port(port);
        get("/hello", (req, res) -> "Hello Rob");
    }
}
