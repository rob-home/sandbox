package sandbox.dbexport;

rm import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Date;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateFormatUtils;

public class WaitForPing {
    private final int port;
    private int maxWaitMs = NumberUtils.toInt(System.getenv("WAIT_FOR_PING"), 0);

    public WaitForPing(final int port) {
        this.port = port;
    }

    public static WaitForPing port(final int port) {
        return new WaitForPing(port);
    }

    public WaitForPing maxWaitMs(final int maxWaitMs) {
        this.maxWaitMs = maxWaitMs;
        return this;
    }

    public void start() {

        if (maxWaitMs > 0) {
            long startTime = System.currentTimeMillis();
            ServerSocket serverSocket = null;
            Socket socket = null;

            try {
                serverSocket = new ServerSocket(port);
                serverSocket.setSoTimeout(maxWaitMs);

                System.out.println(String.format("WaitForPing - URL=[http://%s:%d], maxWait=[%dms]", "localhost", serverSocket.getLocalPort(),
                        this.maxWaitMs));

                socket = serverSocket.accept();
            } catch (SocketTimeoutException e) {
                System.out.println("WaitForPing - Timed out after " + maxWaitMs + "ms");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (socket != null) {
                    try {
                        String response = "WaitForPing - Finished after " + (System.currentTimeMillis() - startTime) + "ms";
                        System.out.println(response);

                        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                        out.write("HTTP/1.0 200 OK\r\n");
                        out.write("Last-modified: " + DateFormatUtils.format(new Date(), "EEE, dd MMM yyyy HH:mm:ss zzz") + "\r\n");
                        out.write("\r\n");
                        out.write(response);

                        out.flush();
                        out.close();
                        socket.close();
                        serverSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}