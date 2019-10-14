package ViewTests;

import Model.PersonDatabase;
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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserHandlerTests {

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
    url = new URL("http://localhost:" + port + "/users");
    con = url.openConnection();
  }

  @After
  public void cleanUpTests() {
    testServer.stop();
  }

  @Test
  public void userContextReturnsAllNamesCurrentlyInServer() throws IOException {
    sendRequest("", "", "GET", con);
    con = url.openConnection();

    String[] namesExpected = {"Dominic", "Anton", "Long"};

    LineNumberReader reader = createReaderAtMarkupLine();
    List<String> namesFound = new ArrayList<>();
    namesFound.add(reader.readLine().trim());
    namesFound.add(reader.readLine().trim());
    namesFound.add(reader.readLine().trim());

    Assert.assertArrayEquals(namesExpected, namesFound.toArray());
  }
}
