package Controller;

import Model.PersonDatabase;
import Model.Response;
import Utilities.RequestValidator;
import View.OutputGenerator;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.util.Set;

public class PersonDatabaseController {

  private PersonDatabase personDatabase;
  private OutputGenerator outputGenerator;

  public PersonDatabaseController(PersonDatabase personDatabase, OutputGenerator outputGenerator) {
    this.personDatabase = personDatabase;
    this.outputGenerator = outputGenerator;
  }

  public Response translateRequestToQuery(HttpExchange exchange, String[] arguments) throws IOException {
    String key = "";
    String value = "";
    String output = "Incorrect request type!";
    int statusCode = 400;

    if (arguments.length == 2) {
      key = arguments[0];
      value = arguments[1];
    } else if (arguments.length == 1) {
      value = arguments[0];
    }

    switch (exchange.getRequestMethod()) { //Decouple exchange and switch naming
      case "GET":
        output = outputGenerator.getOutputGenerator(personDatabase.getAll());
        statusCode = 200;
        break;
      case "POST":
        if (RequestValidator.isValid(value, personDatabase.getAll())) {
          personDatabase.add(value);
          output = outputGenerator.postOutput();
          statusCode = 200;
        } else {
          output = outputGenerator.postErrorOutput();
          statusCode = 403;
        }
        break;
      case "PUT":
        if (RequestValidator.isValid(value, personDatabase.getAll())) {
          personDatabase.change(key, value);
          output = outputGenerator.putOutput();
          statusCode = 200;
        } else {
          output = outputGenerator.putErrorOutput();
          statusCode = 403;
        }
        break;
      case "DELETE":
        if (RequestValidator.isPersonImportant(value)) {
          personDatabase.remove(value);
          output = outputGenerator.deleteOutput();
          statusCode = 200;
        } else {
          output = outputGenerator.deleteErrorOutput();
          statusCode = 403;
        }
        break;
    }
    Response serverResponse = new Response();
    serverResponse.setServerOutput(output);
    serverResponse.setServerStatusCode(statusCode);
    return serverResponse;
  }

  public Set<String> getUsers() {
    return personDatabase.getAll();
  }
}
