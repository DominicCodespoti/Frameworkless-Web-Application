package Controller;

import Model.IPersonDatabase;
import Utilities.RequestValidator;
import View.OutputGenerator;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;

public class PersonDatabaseController {

  private IPersonDatabase personDatabase;

  public PersonDatabaseController(IPersonDatabase personDatabase) {
    this.personDatabase = personDatabase;
  }

  public String translateRequestToQuery(HttpExchange exchange, String[] arguments) throws IOException {
    String key = "";
    String value = "";
    String output = "Error, request failed!";

    if (arguments.length == 2) {
      key = arguments[0];
      value = arguments[1];
    } else if (arguments.length == 1) {
      value = arguments[0];
    }

    switch (exchange.getRequestMethod()) {
      case "GET":
        output = OutputGenerator.getOutputGenerator(personDatabase.getAll());
        break;
      case "POST":
        if (RequestValidator.isValid(value, personDatabase.getAll())) {
          personDatabase.add(value);
          output = OutputGenerator.postOutput();
        } else {
          output = OutputGenerator.postErrorOutput();
        }
        break;
      case "PUT":
        if (RequestValidator.isValid(value, personDatabase.getAll())) {
          personDatabase.change(key, value);
          output = OutputGenerator.putOutput();
        } else {
          output = OutputGenerator.putErrorOutput();
        }
        break;
      case "DELETE":
        if (RequestValidator.isPersonImportant(value)) {
          personDatabase.remove(value);
          output = OutputGenerator.deleteOutput();
        } else {
          output = OutputGenerator.deleteErrorOutput();
        }
        break;
    }
    return output;
  }
}
