package Model;

public class Response {
  private String serverOutput;
  private int serverStatusCode;

  public int getServerStatusCode() {
    return serverStatusCode;
  }

  public String getServerOutput() {
    return serverOutput;
  }

  public void setServerOutput(String serverOutput) {
    this.serverOutput = serverOutput;
  }

  public void setServerStatusCode(int serverStatusCode) {
    this.serverStatusCode = serverStatusCode;
  }
}
