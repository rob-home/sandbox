package sandbox.dbexport;

public class TestWaitForPing
{
    public static void main(String [] args) throws Exception
    {
        WaitForPing.port(1234).maxWaitMs(60000).start();
    }
}
