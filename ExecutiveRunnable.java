import java.util.ArrayList;
import java.util.Iterator;
// http://stackoverflow.com/questions/877096/how-can-i-pass-a-parameter-to-a-java-thread
public class ExecutiveRunnable implements Runnable{
  private String inputFile ;
  private String outputFile ;
  private Double primaryFailureProbability ;
  private Double secondaryFailureProbability ;
  private Integer timeLimit;

  public ExecutiveRunnable(String inputFile,String outputFile,
                         Double primaryFailureProbability,
                         Double secondaryFailureProbability,
                         Integer timeLimit){
    this.inputFile = inputFile;
    this.outputFile = outputFile;
    this.primaryFailureProbability = primaryFailureProbability;
    this.secondaryFailureProbability = secondaryFailureProbability;
    this.timeLimit=timeLimit;
  }

  public void run(){
    // System.out.println("I started");
    // System.out.println(inputFile);
    // System.out.println(outputFile);
    // System.out.println(primaryFailureProbability);
    // System.out.println(secondaryFailureProbability);
    // System.out.println(timeLimit);

    PrimaryRunnable primaryRunnable = new PrimaryRunnable(inputFile,primaryFailureProbability);
    Thread primaryThread=new Thread(primaryRunnable);
    primaryThread.start();
    try{
      primaryThread.join();
      System.out.println("thread finished");
    }catch(InterruptedException e){
      e.printStackTrace();
      System.out.println("Thread was interupted");
      return;
    }

    ArrayList<Integer> sortedIntegers=primaryRunnable.getResult();

    Adjudicator adjudicator= new Adjudicator();
    adjudicator.sortingAcceptanceTest(sortedIntegers);
  }

  private class Adjudicator {
    protected Boolean sortingAcceptanceTest(ArrayList<Integer> sortedIntegers){

      // ArrayList<String> strList=FileIO.loadFileToList(fileName);
      // ArrayList<Integer> sortedIntegers=new ArrayList<Integer>();
      //
      // for(String s : strList) sortedIntegers.add(Integer.parseInt(s));

      Iterator<Integer> sortedIntegersIterator= sortedIntegers.iterator();
      Integer lastInteger=Integer.MIN_VALUE;
      Integer currentInteger;

      while(sortedIntegersIterator.hasNext()){
        currentInteger=sortedIntegersIterator.next();
        System.out.print("Last:"+lastInteger.toString()+"  Current:"+currentInteger.toString()+"   ");
        System.out.println(currentInteger<lastInteger);

        if(currentInteger<lastInteger) return false;

        lastInteger=currentInteger;
      }
      return true;
    }
  }

}
