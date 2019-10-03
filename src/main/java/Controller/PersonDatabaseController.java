package Controller;

import Model.IPersonDatabase;
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
    String output = "";

    if (arguments.length == 2) {
      key = arguments[0];
      value = arguments[1];
    } else if (arguments.length == 1) {
      value = arguments[0];
    }

    switch (exchange.getRequestMethod()) {
      case "GET":
        output = personDatabase.getQuery();
        break;
      case "POST":
        output = personDatabase.postQuery(value);
        break;
      case "PUT":
        output = personDatabase.putQuery(key, value);
        break;
      case "DELETE":
        output = personDatabase.deleteQuery(value);
        break;
    }
    return output;
  }
}
