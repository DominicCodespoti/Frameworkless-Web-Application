package ModelTests;

import Model.PersonDatabase;
import java.util.LinkedHashSet;
import java.util.Set;

public class PersonDatabaseStub implements PersonDatabase {

  private Set<String> people = new LinkedHashSet<>();

  public PersonDatabaseStub() {
    people.add("Dominic");
    people.add("Anton");
    people.add("Long");
  }

  @Override
  public void getFromDatabase() {

  }

  @Override
  public Set<String> getAll() {
    return people;
  }

  @Override
  public void add(String personToAdd) {
    people.add(personToAdd);
  }

  @Override
  public void remove(String personToRemove) {
    people.remove(personToRemove);
  }

  @Override
  public void change(String personToChange, String personToChangeToo) {
    people.remove(personToChange);
    people.add(personToChangeToo);
  }
}
