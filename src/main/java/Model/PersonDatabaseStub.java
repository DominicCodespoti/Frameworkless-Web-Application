package Model;

import Utilities.RequestValidator;
import java.util.ArrayList;
import java.util.List;

public class PersonDatabaseStub implements IPersonDatabase {

  private List<String> people = new ArrayList<>();

  public PersonDatabaseStub() {
    updateDatabase();
  }

  @Override
  public void updateDatabase() {
    people.clear();
    people.add("Dominic");
    people.add("Anton");
    people.add("Long");
  }

  @Override
  public List<String> getPeople() {
    return people;
  }

  @Override
  public void addPerson(String personToAdd) {
    people.add(personToAdd);
  }

  @Override
  public String getQuery() {
    if (people.size() == 1) {
      return "Hello " + people.get(0) + " - the time on the server is 12 AM on Monday";
    } else if (people.size() == 2) {
      return "Hello " + people.get(0) + " and " + people.get(1)
          + " - the time on the server is 12 AM on Monday";
    } else {
      StringBuilder moreThanThreePeople = new StringBuilder();
      for (int i = 0; i < people.size() - 1; i++) {
        moreThanThreePeople.append(people.get(i));
        moreThanThreePeople.append(", ");
      }
      return "Hello " + moreThanThreePeople + "and " + people.get(people.size() - 1)
          + " - the time on the server is 12 AM on Monday";
    }
  }

  @Override
  public String deleteQuery(String personToDelete) {
    if (!RequestValidator.doesPersonExist(personToDelete, people)) {
      return "Error, person does not exist in world!";
    }
    if (RequestValidator.isPersonImportant(personToDelete)) {
      return "Error, you can't get rid of Dominic!";
    }

    String personBeingDeleted = people.stream().filter(x -> x.equals(personToDelete)).findFirst().orElse(null);

    people.remove(personBeingDeleted);

    return "Person deleted successfully!";
  }

  @Override
  public String putQuery(String personToChange, String personToChangeToo) {
    if (!RequestValidator.doesPersonExist(personToChange, people)) {
      return "Error, person you want to change does not exist in world!";
    }
    if (RequestValidator.doesPersonExist(personToChangeToo, people)) {
      return "Error, persons new name already exists!";
    }

    String personBeingDeleted = people.stream().filter(x -> x.equals(personToChange)).findFirst().orElse(null);

    people.remove(personBeingDeleted);
    addPerson(personToChangeToo);

    return "Person changed successfully!";
  }

  @Override
  public String postQuery(String personToAdd) {
    if (RequestValidator.doesPersonExist(personToAdd, people)) {
      return "Error, persons name already exists!";
    }

    addPerson(personToAdd);

    return "Person added successfully!";
  }
}
