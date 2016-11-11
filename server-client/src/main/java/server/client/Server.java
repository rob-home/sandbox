package server.client;

public class Server
{
    private final int port;
    
    private Server(int port)
    {
        this.port = port;
    }
    
    public Server port(int port)
    {
        return new Server(port);
    }
}
