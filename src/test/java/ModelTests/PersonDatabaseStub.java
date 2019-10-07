package ModelTests;

import Model.IPersonDatabase;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class PersonDatabaseStub implements IPersonDatabase {

  private Set<String> people = new LinkedHashSet<>();

  public PersonDatabaseStub() {
    people.add("Dominic");
    people.add("Anton");
    people.add("Long");
  }

  @Override
  public void updateDatabase() {

  }

  @Override
  public Set<String> getAll() {
    return people;
  }

  @Override
  public void add(String personToAdd) throws IOException {
    people.add(personToAdd);
  }

  @Override
  public void remove(String personToRemove) throws IOException {
    people.remove(personToRemove);
  }

  @Override
  public void change(String personToChange, String personToChangeToo) throws IOException {
    people.remove(personToChange);
    people.add(personToChangeToo);
  }
}
