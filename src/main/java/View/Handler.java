package View;

import static java.nio.file.Files.readString;

import Controller.PersonDatabaseController;
import Model.IPersonDatabase;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;

public class Handler implements HttpHandler {

  private PersonDatabaseController personDatabaseController;

  public Handler(IPersonDatabase personDatabase) {
    personDatabaseController = new PersonDatabaseController(personDatabase);
  }

  @Override
  public void handle(HttpExchange exchange) throws IOException {

    byte[] bytes = exchange.getRequestBody().readAllBytes();
    String[] keyValue = new String(bytes).split("=");

    //View takes paramaters to display, formats it, and updats the html accordingly, and returns it
    //Controller takes information from view, IE, user to delete, and passes it to model and the request type, it also controls output
    //Model holds data and performs business logic

    String output = readString(Paths.get("Index.html"));
    String response = personDatabaseController.translateRequestToQuery(exchange, keyValue);

    output = output.replace("{{Title}}", "Hello World");
    output = output.replace("{{Body}}", response);

    exchange.sendResponseHeaders(200, output.getBytes().length);
    OutputStream os = exchange.getResponseBody();
    os.write(output.getBytes());
    os.close();
  }
}
