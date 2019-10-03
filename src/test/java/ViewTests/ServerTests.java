package ViewTests;

import Model.PersonDatabaseStub;
import View.Server;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ServerTests {

  private Server testServer = new Server(new PersonDatabaseStub());

  private void sendRequest(String paramOne, String paramTwo, String requestMethod, URLConnection con)
      throws IOException {
    HttpURLConnection http = (HttpURLConnection) con;
    http.setRequestMethod(requestMethod); // PUT is another valid option
    http.setDoOutput(true);
    String sj = paramOne + "=" + paramTwo;
    if (paramOne.isEmpty() && paramTwo.isEmpty()) {
      sj = "";
    }
    byte[] out = sj.getBytes(StandardCharsets.UTF_8);
    int length = out.length;

    http.setFixedLengthStreamingMode(length);
    http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
    http.connect();
    try (OutputStream os = http.getOutputStream()) {
      os.write(out);
    }
  }

  @Before
  public void initializeServer() {
    testServer.start();
  }

  @After
  public void cleanUpServer() {
    testServer.stop();
  }

  @Test
  public void testServerRespondsToGetRequestWithCorrectData() throws IOException {
    URL url = new URL("http://localhost:8080");
    URLConnection con = url.openConnection();

    sendRequest("", "", "GET", con);

    LineNumberReader reader = new LineNumberReader(new InputStreamReader(con.getInputStream()));

    for (int x = 0; x < 13; x++) {
      reader.readLine();
    }

    Assert
        .assertEquals("Hello Dominic, Anton, and Long - the time on the server is 12 AM on Monday", reader.readLine());
  }

  @Test
  public void testServerRespondsToPostRequestWithCorrectData() throws IOException {
    URL url = new URL("http://localhost:8080");
    URLConnection con = url.openConnection();

    sendRequest("", "Test", "POST", con);

    LineNumberReader reader = new LineNumberReader(new InputStreamReader(con.getInputStream()));

    for (int x = 0; x < 13; x++) {
      reader.readLine();
    }

    Assert.assertEquals("Hello Dominic, Anton, Long, and Test - the time on the server is 12 AM on Monday",
        reader.readLine());
  }

  @Test
  public void testServerRespondsToPutRequestWithCorrectData() throws IOException {
    URL url = new URL("http://localhost:8080");
    URLConnection con = url.openConnection();

    sendRequest("Anton", "Max", "PUT", con);

    LineNumberReader reader = new LineNumberReader(new InputStreamReader(con.getInputStream()));

    for (int x = 0; x < 13; x++) {
      reader.readLine();
    }

    Assert.assertEquals("Hello Dominic, Max, and Long - the time on the server is 12 AM on Monday", reader.readLine());
  }

  @Test
  public void testServerRespondsToDeleteRequestWithCorrectData() throws IOException {
    URL url = new URL("http://localhost:8080");
    URLConnection con = url.openConnection();

    sendRequest("", "Long", "DELETE", con);

    LineNumberReader reader = new LineNumberReader(new InputStreamReader(con.getInputStream()));

    for (int x = 0; x < 13; x++) {
      reader.readLine();
    }

    Assert.assertEquals("Hello Dominic and Anton - the time on the server is 12 AM on Monday", reader.readLine());
  }
}
