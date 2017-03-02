import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import java.util.Arrays;
public class DataGenerator{
  public static void main(String[] args) {

    System.out.println(args[0]);
    System.out.println(args[1]);
    System.out.println(Integer.MIN_VALUE);
    System.out.println(Integer.MAX_VALUE);
    //Handle input right
    String fileName= args[0]+".txt";
    int numberListLength=Integer.parseInt(args[1]);


    //Get ArrayList of random numbers function
    // http://stackoverflow.com/questions/363681/generating-random-integers-in-a-specific-range
    ArrayList<Integer> unSortedRandomNumbers= new ArrayList<Integer>(numberListLength);
    for(int i=0;i<numberListLength;i++){
      int randomNum = ThreadLocalRandom.current().nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
      unSortedRandomNumbers.add(randomNum);
    }
    System.out.println(Arrays.toString(unSortedRandomNumbers.toArray()));

    //Print to file function

    try(BufferedWriter writer = new BufferedWriter( new FileWriter( fileName))){
      writer.write(args[1]);
      writer.newLine();
    }catch(IOException e){
      e.printStackTrace();
    };

    // do stuff
    // writer.close();
  }
}
