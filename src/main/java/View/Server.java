package View;

import Model.IPersonDatabase;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {

  private HttpServer httpServer;

  public Server(IPersonDatabase personDatabase, int port) {
    try {
      httpServer = HttpServer.create(new InetSocketAddress(port), 0);
      httpServer.createContext("/index", new IndexHandler(personDatabase));
      httpServer.createContext("/users", new UserHandler(personDatabase));
      httpServer.setExecutor(null);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void start() {
    this.httpServer.start();
  }

  public void stop() {
    this.httpServer.stop(0);
  }
}
