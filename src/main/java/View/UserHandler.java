package View;

import static java.nio.file.Files.readString;

import Controller.PersonDatabaseController;
import Model.IPersonDatabase;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.Iterator;

public class UserHandler implements HttpHandler {

  private PersonDatabaseController personDatabaseController;

  public UserHandler(IPersonDatabase personDatabase) {
    personDatabaseController = new PersonDatabaseController(personDatabase);
  }

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    Iterator iterator = personDatabaseController.getUsers().iterator();
    String output = readString(Paths.get("Index.html"));
    StringBuilder response = new StringBuilder();

    while (iterator.hasNext()){
      response.append(iterator.next()).append("\n");
    }

    output = output.replace("{{Title}}", "Hello World");
    output = output.replace("{{Body}}", response.toString());


    exchange.sendResponseHeaders(200, output.getBytes().length);
    OutputStream os = exchange.getResponseBody();
    os.write(output.getBytes());
    os.close();
  }
}
