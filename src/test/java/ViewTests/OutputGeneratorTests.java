package ViewTests;

import Model.PersonDatabase;
import Model.LocalPersonDatabase;
import ModelTests.PersonDatabaseStub;
import View.OutputGenerator;
import View.Server;
import View.WebPageOutputGenerator;
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

public class OutputGeneratorTests {
  private Server testServer;
  private URL url;
  private URLConnection con;

  private void sendRequest(String paramOne, String paramTwo, String requestMethod, URLConnection con)
      throws IOException {
    HttpURLConnection http = (HttpURLConnection) con;
    http.setRequestMethod(requestMethod);
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
    PersonDatabase personDatabase = new PersonDatabaseStub();
    OutputGenerator outputGenerator = new WebPageOutputGenerator();
    testServer = new Server(personDatabase, outputGenerator, port, "localhost");
    testServer.start();
    url = new URL("http://localhost:" + port + "/index");
    con = url.openConnection();
  }

  @After
  public void cleanUpTests() {
    testServer.stop();
  }

  @Test
  public void testOutputGeneratesCorrectGreetingForFourPeople() throws IOException {
    sendRequest("", "Potato", "POST", con);
    con = url.openConnection();

    LineNumberReader reader = createReaderAtMarkupLine();

    Assert.assertEquals("Hello Dominic, Anton, Long, and Potato", reader.readLine().split("-")[0].trim());
  }

  @Test
  public void testOutputGeneratesCorrectGreetingForThreePeople() throws IOException {
    LineNumberReader reader = createReaderAtMarkupLine();

    Assert.assertEquals("Hello Dominic, Anton, and Long", reader.readLine().split("-")[0].trim());
  }

  @Test
  public void testOutputGeneratesCorrectGreetingForTwoPeople() throws IOException {
    sendRequest("", "Long", "DELETE", con);
    con = url.openConnection();

    LineNumberReader reader = createReaderAtMarkupLine();

    Assert.assertEquals("Hello Dominic and Anton", reader.readLine().split("-")[0].trim());
  }

  @Test
  public void testOutputGeneratesCorrectGreetingForOnePerson() throws IOException {
    sendRequest("", "Long", "DELETE", con);
    con = url.openConnection();
    sendRequest("", "Anton", "DELETE", con);
    con = url.openConnection();

    LineNumberReader reader = createReaderAtMarkupLine();

    Assert.assertEquals("Hello Dominic", reader.readLine().split("-")[0].trim());
  }
}
