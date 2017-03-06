import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import java.util.Arrays;


public class DataGenerator{
  public static void main(String[] args) {

    System.out.println(args[0]);
    System.out.println(args[1]);

    //Handle input right
    String fileName= args[0];
    int integerListLength=Integer.parseInt(args[1]);

    //Get ArrayList of random Integers function
    // http://stackoverflow.com/questions/363681/generating-random-integers-in-a-specific-range
    ArrayList<Integer> unsortedRandomIntegers= new ArrayList<Integer>(integerListLength);
    for(int i=0;i<integerListLength;i++){
      int randomNum = ThreadLocalRandom.current().nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
      unsortedRandomIntegers.add(randomNum);
    }

    FileIO.saveListToFile(unsortedRandomIntegers,fileName);

    ArrayList<String> strList =FileIO.loadFileToList(fileName);
    unsortedRandomIntegers=new ArrayList<Integer>(integerListLength);
    for(String s : strList) unsortedRandomIntegers.add(Integer.parseInt(s));
    // System.out.println(unsortedRandomIntegers);

  }
}
