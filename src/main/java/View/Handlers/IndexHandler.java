package View.Handlers;

import static java.nio.file.Files.readString;

import Controller.PersonDatabaseController;
import Model.PersonDatabase;
import Model.Response;
import View.OutputGenerator;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;

public class IndexHandler implements HttpHandler {

  private PersonDatabaseController personDatabaseController;

  public IndexHandler(PersonDatabase personDatabase, OutputGenerator outputGenerator) {
    personDatabaseController = new PersonDatabaseController(personDatabase, outputGenerator);
  }

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    byte[] bytes = exchange.getRequestBody().readAllBytes();
    String[] keyValue = new String(bytes).split("=");

    String output = readString(Paths.get("Index.html"));
    Response response = personDatabaseController.translateRequestToQuery(exchange, keyValue);

    output = output.replace("{{Title}}", "Hello World");
    output = output.replace("{{Body}}", response.getServerOutput());

    exchange.sendResponseHeaders(response.getServerStatusCode(), output.getBytes().length);
    OutputStream os = exchange.getResponseBody();
    os.write(output.getBytes());
    os.close();
  }
}
