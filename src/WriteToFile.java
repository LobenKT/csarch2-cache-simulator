import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import java.util.ArrayList;

public class WriteToFile {
  void generate(ArrayList<String> info) {
    try {
      FileWriter myWriter = new FileWriter("testlog.txt");
      for(int i = 0; i < info.size(); i++){
        myWriter.write(info.get(i));
        myWriter.write("\n");
      }
      myWriter.close();
      System.out.println("Successfully wrote to the file.");
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
}