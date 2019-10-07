package ViewTests;

import ModelTests.PersonDatabaseStub;
import View.Server;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ServerTests {

  private Server testServer;
  private URL url;
  private URLConnection con;

  private void sendRequest(String paramOne, String paramTwo, String requestMethod, URLConnection con)
      throws IOException {
    HttpURLConnection http = (HttpURLConnection) con;
    http.setRequestMethod(requestMethod); // PUT is another valid option
    http.setDoOutput(true);
    String sj = paramOne + "=" + paramTwo;
    if (paramOne.isEmpty() && paramTwo.isEmpty()) {
      sj = "";
    } else if (paramOne.isEmpty()) {
      sj = sj.split("=")[1];
    }
    byte[] out = sj.getBytes(StandardCharsets.UTF_8);
    int length = out.length;

    http.setFixedLengthStreamingMode(length);
    http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
    OutputStream os = http.getOutputStream();
    OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);
    osw.write(sj);
    osw.flush();
    osw.close();
    http.connect();
  }

  private LineNumberReader createReaderAtMarkupLine() throws IOException {
    LineNumberReader reader = new LineNumberReader(new InputStreamReader(con.getInputStream()));
    for (int x = 0; x < 13; x++) {
      reader.readLine();
    }
    return reader;
  }

  @Before
  public void initalizeTests() throws IOException {
    int port = ThreadLocalRandom.current().nextInt(8000, 9000);
    testServer = new Server(new PersonDatabaseStub(), port);
    testServer.start();
    url = new URL("http://localhost/index:" + port);
    con = url.openConnection();
  }

  @After
  public void cleanUpTests() {
    testServer.stop();
  }

  @Test
  public void testServerRespondsToGetRequestWithCorrectData() throws IOException {
    LineNumberReader reader = createReaderAtMarkupLine();
    Assert.assertEquals("Hello Dominic, Anton, and Long", reader.readLine().split("-")[0].trim());
  }

  @Test
  public void testServerRespondsToPostRequestWithCorrectData() throws IOException {
    sendRequest("", "Test", "POST", con);
    con = url.openConnection();

    LineNumberReader reader = createReaderAtMarkupLine();

    Assert.assertEquals("Hello Dominic, Anton, Long, and Test", reader.readLine().split("-")[0].trim());
  }

  @Test
  public void testServerRefusesPostRequestIfNameIsAlreadyPresent() throws IOException {
    sendRequest("", "Dominic", "POST", con);

    LineNumberReader reader = createReaderAtMarkupLine();

    Assert.assertEquals("Error, the name you are attempting to add is invalid!", reader.readLine().trim());
  }

  @Test
  public void testServerRefusesPutRequestIfNewNameIsTaken() throws IOException {
    sendRequest("Anton", "Dominic", "PUT", con);

    LineNumberReader reader = createReaderAtMarkupLine();

    Assert.assertEquals("Error, the name you are attempting to change too is invalid!", reader.readLine().trim());
  }

  @Test
  public void testServerRefusesPutRequestIfOldNameIsNotPresent() throws IOException {
    sendRequest("Mitch", "Dominic", "PUT", con);

    LineNumberReader reader = createReaderAtMarkupLine();

    Assert.assertEquals("Error, the name you are attempting to change too is invalid!", reader.readLine().trim());
  }

  @Test
  public void testServerRefusesDeleteRequestIfNameIsDominic() throws IOException {
    sendRequest("", "Dominic", "DELETE", con);

    LineNumberReader reader = createReaderAtMarkupLine();

    Assert.assertEquals("Error, the name you are attempting to delete is invalid!", reader.readLine().trim());
  }

  @Test
  public void testServerRespondsToPutRequestWithCorrectData() throws IOException {
    sendRequest("Anton", "Max", "PUT", con);
    con = url.openConnection();

    LineNumberReader reader = createReaderAtMarkupLine();

    Assert.assertEquals("Hello Dominic, Long, and Max", reader.readLine().split("-")[0].trim());
  }

  @Test
  public void testServerRespondsToDeleteRequestWithCorrectData() throws IOException {
    sendRequest("", "Long", "DELETE", con);
    con = url.openConnection();

    LineNumberReader reader = createReaderAtMarkupLine();

    Assert.assertEquals("Hello Dominic and Anton", reader.readLine().split("-")[0].trim());
  }
}
