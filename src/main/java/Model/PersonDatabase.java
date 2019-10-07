package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

public class PersonDatabase implements IPersonDatabase {

  private Set<String> people = new LinkedHashSet<>();

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
  public Set<String> getAll() {
    return people;
  }

  @Override
  public void add(String personToAdd) throws IOException {
    people.add(personToAdd);
    saveDatabase();
  }

  @Override
  public void remove(String personToRemove) throws IOException {
    people.remove(personToRemove);
    saveDatabase();
  }

  @Override
  public void change(String personToChange, String personToChangeToo) throws IOException {
    people.remove(personToChange);
    people.add(personToChangeToo);
    saveDatabase();
  }

  private void saveDatabase() throws IOException {
    FileWriter writer = new FileWriter("persons.txt");
    for (String currentPerson : getAll()) {
      writer.write(currentPerson + System.lineSeparator());
    }
    writer.close();
    updateDatabase();
  }
}
