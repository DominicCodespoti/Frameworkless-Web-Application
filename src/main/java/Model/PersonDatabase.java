package Model;

import Utilities.RequestValidator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PersonDatabase implements IPersonDatabase{

  private List<String> people = new ArrayList<>();

  public PersonDatabase() throws FileNotFoundException {
    updateDatabase();
  }

  @Override
  public void updateDatabase() throws FileNotFoundException {
    people.clear();
    Scanner currentPerson = new Scanner(new File("persons.txt"));
    while (currentPerson.hasNext()) {
      people.add(currentPerson.next());
    }
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
    ZonedDateTime melbourneTime = ZonedDateTime.now(ZoneId.of("Australia/Sydney"));
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd MM yyyy");
    String melbourneDateFormatted = melbourneTime.format(dateFormat);
    DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mm a");
    String melbourneTimeFormatted = melbourneTime.format(timeFormat); //todo make new classs for time

    if (getPeople().size() == 1) {
      return "Hello " + getPeople().get(0) + " - the time on the server is " + melbourneTimeFormatted + " on "
          + melbourneDateFormatted;
    } else if (getPeople().size() == 2) {
      return "Hello " + getPeople().get(0) + " and " + getPeople().get(1) + " - the time on the server is "
          + melbourneTimeFormatted + " on " + melbourneDateFormatted;
    } else {
      StringBuilder moreThanThreePeople = new StringBuilder();
      for (int i = 0; i < getPeople().size() - 1; i++) {
        moreThanThreePeople.append(getPeople().get(i));
        moreThanThreePeople.append(", ");
      }
      return "Hello " + moreThanThreePeople + "and " + getPeople().get(getPeople().size() - 1)
          + " - the time on the server is " + melbourneTimeFormatted + " on " + melbourneDateFormatted;
    }
  }

  @Override
  public String deleteQuery(String personToDelete) throws IOException {
    if (!RequestValidator.doesPersonExist(personToDelete, getPeople())) {
      return "Error, person does not exist in world!";
    }
    if (RequestValidator.isPersonImportant(personToDelete)) {
      return "Error, you can't get rid of Dominic!";
    }

    String personBeingDeleted = getPeople().stream().filter(x -> x.equals(personToDelete)).findFirst().orElse(null);

    getPeople().remove(personBeingDeleted);
    saveDatabase();

    return "Person deleted successfully!";
  }

  @Override
  public String putQuery(String personToChange, String personToChangeToo) throws IOException {
    if (!RequestValidator.doesPersonExist(personToChange, getPeople())) {
      return "Error, person you want to change does not exist in world!";
    }
    if (RequestValidator.doesPersonExist(personToChangeToo, getPeople())) {
      return "Error, persons new name already exists!";
    }

    String personBeingDeleted = getPeople().stream().filter(x -> x.equals(personToChange)).findFirst().orElse(null);

    getPeople().remove(personBeingDeleted);
    addPerson(personToChangeToo);
    saveDatabase();

    return "Person changed successfully!";
  }

  @Override
  public String postQuery(String personToAdd) throws IOException {
    if (RequestValidator.doesPersonExist(personToAdd, getPeople())) {
      return "Error, persons name already exists!";
    }

    addPerson(personToAdd);
    saveDatabase();

    return "Person added successfully!";
  }

  public void saveDatabase() throws IOException {
    FileWriter writer = new FileWriter("persons.txt");
    for (String currentPerson : getPeople()) {
      writer.write(currentPerson + System.lineSeparator());
    }
    writer.close();
    updateDatabase();
  }
}
