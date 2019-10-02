package Utilities;

import java.util.List;

public class RequestValidator {

  public static boolean doesPersonExist(String nameToCheck, List<String> peopleToCheckAgainst) {
    return (peopleToCheckAgainst.stream().anyMatch(x -> x.equals(nameToCheck)));
  }

  public static boolean isPersonImportant(String nameToCheck) {
    return (nameToCheck.equals("Dominic"));
  }
}
