package ViewTests;

import View.Server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ServerTests {

  private Server testServer = new Server();

  private void sendRequest(String paramOne, String paramTwo, String requestMethod) throws IOException {
    URL url = new URL("http://localhost:8080/");
    URLConnection con = url.openConnection();
    HttpURLConnection http = (HttpURLConnection) con;
    http.setRequestMethod(requestMethod); // PUT is another valid option
    http.setDoOutput(true);

    String sj = paramOne + "=" + paramTwo;
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
  public void initializeServer()  {
    testServer.start();
  }

  @Test
  public void testServerInitializesOnPortCorrectly() throws IOException {
    URL url = new URL("http://localhost:8080");
    URLConnection con = url.openConnection();

    BufferedReader reader =
        new BufferedReader(new InputStreamReader(con.getInputStream()));

    Assert.assertEquals("Welcome!", reader.readLine());
  }
}
