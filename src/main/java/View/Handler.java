package View;

import Model.PersonDatabase;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;

public class Handler implements HttpHandler {

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    PersonDatabase database = new PersonDatabase();

    byte[] bytes = exchange.getRequestBody().readAllBytes();
    String[] keyValue = new String(bytes).split("=");
    String key = "";
    String value = "";

    if (keyValue.length == 2) {
      key = keyValue[0];
      value = keyValue[1];
    }

    String response = "Welcome!";

//    switch (exchange.getRequestMethod()) {
//      case "GET":
//        response = database.getReply();
//        break;
//      case "POST":
//        response = database.postReply(value);
//        break;
//      case "PUT":
//        response = database.putReply(key, value);
//        break;
//      case "DELETE":
//        response = database.deleteReply(value);
//        break;
//    }

    exchange.sendResponseHeaders(200, response.getBytes().length);
    OutputStream os = exchange.getResponseBody();
    os.write(response.getBytes());
    os.close();
  }
}
