package View;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {

  private HttpServer httpServer;

  public Server() {
    try {
      httpServer = HttpServer.create(new InetSocketAddress(8080), 0);
      httpServer.createContext("/", new Handler());
      httpServer.setExecutor(null);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void start() {
    this.httpServer.start();
  }
}