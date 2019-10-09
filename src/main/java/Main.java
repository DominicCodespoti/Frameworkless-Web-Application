import Model.PersonDatabase;
import Model.LocalPersonDatabase;
import View.OutputGenerator;
import View.Server;
import View.WebPageOutputGenerator;

public class Main {

  public static void main(String[] args) throws Exception {
    PersonDatabase personDatabase = new LocalPersonDatabase();
    OutputGenerator outputGenerator = new WebPageOutputGenerator();
    Server server = new Server(personDatabase, outputGenerator, 8080);
    server.start();
  }
}
