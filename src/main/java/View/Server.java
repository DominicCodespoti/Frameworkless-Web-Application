package View;

import Model.PersonDatabase;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {

  private HttpServer httpServer;

  public Server(PersonDatabase personDatabase, OutputGenerator outputGenerator, int port) {
    try {
      httpServer = HttpServer.create(new InetSocketAddress(port), 0);
      httpServer.createContext("/index", new IndexHandler(personDatabase, outputGenerator));
      httpServer.createContext("/users", new UserHandler(personDatabase, outputGenerator));
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
