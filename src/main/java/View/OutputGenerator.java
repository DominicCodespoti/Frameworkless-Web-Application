package View;

import java.util.Set;

public interface OutputGenerator {

  String getOutputFormatter(String names, String[] formattedDateTime);

  String getOutputGenerator(Set<String> people);

  String putOutput();

  String postOutput();

  String deleteOutput();

  String putErrorOutput();

  String postErrorOutput();

  String deleteErrorOutput();
}
