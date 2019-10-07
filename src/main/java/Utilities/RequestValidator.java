package Utilities;

import java.util.Set;

public class RequestValidator {

  private static boolean doesPersonExist(String nameToCheck, Set<String> peopleToCheckAgainst) {
    return peopleToCheckAgainst.stream().anyMatch(x -> x.equals(nameToCheck));
  }

  public static boolean isPersonImportant(String nameToCheck) {
    return (!nameToCheck.equals("Dominic"));
  }

  public static boolean isValid(String nameToCheck, Set<String> peopleToCheckAgainst) {
    return !doesPersonExist(nameToCheck, peopleToCheckAgainst) && isPersonImportant(nameToCheck);
  }
}
