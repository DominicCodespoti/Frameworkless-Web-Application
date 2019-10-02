package Model;

import java.io.FileNotFoundException;
import java.util.List;

public interface IPersonDatabase {

  void updateDatabase() throws FileNotFoundException;

  List<String> getPeople();

  void addPerson(String personToAdd);

  String getQuery();

  String deleteQuery(String personToDelete);

  String putQuery(String personToChange, String personToChangeToo);

  String postQuery(String personToAdd);
}
