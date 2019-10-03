import Model.PersonDatabase;
import View.Server;

public class Main {

  public static void main(String[] args) throws Exception {
    Server server = new Server(new PersonDatabase(), 8080);
    server.start();
  }
}
