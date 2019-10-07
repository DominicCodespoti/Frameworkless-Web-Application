package Model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;

public interface IPersonDatabase {

  void updateDatabase() throws FileNotFoundException;

  Set<String> getAll();

  void remove(String personToDelete) throws IOException;

  void change(String personToChange, String personToChangeToo) throws IOException;

  void add(String personToAdd) throws IOException;
}
