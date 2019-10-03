package Model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface IPersonDatabase {

  void updateDatabase() throws FileNotFoundException;

  List<String> getPeople();

  void addPerson(String personToAdd);

  String getQuery();

  String deleteQuery(String personToDelete) throws IOException;

  String putQuery(String personToChange, String personToChangeToo) throws IOException;

  String postQuery(String personToAdd) throws IOException;
}
