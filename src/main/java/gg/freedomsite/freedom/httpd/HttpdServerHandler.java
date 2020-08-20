package gg.freedomsite.freedom.httpd;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class HttpdServerHandler
{

    private HttpServer httpServer;

    public HttpdServerHandler()
    {
        try {
            this.httpServer = HttpServer.create(new InetSocketAddress("localhost", 8901), 0);
            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
            httpServer.setExecutor(threadPoolExecutor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addHandler(String requestKey, HttpHandler handler)
    {
        httpServer.createContext(requestKey, handler);
    }

    public void start()
    {
        httpServer.start();
    }

    public void stop()
    {
        httpServer.stop(0);
    }

}
