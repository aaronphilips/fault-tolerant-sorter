import java.util.ArrayList;

public class PrimaryRunnable implements Runnable{
  private Double failureProbablity;
  private String inputFileName;
  private String outputFileName;
  // private String result;
  private Boolean failure;
  public PrimaryRunnable(String inputFileName, String outputFileName, Double failureProbablity){
    this.failureProbablity=failureProbablity;
    this.inputFileName=inputFileName;
    this.outputFileName=outputFileName;
  }
  public void run(){
    try{
      // REMOVE THIS this is harsh delay timer
      // try{Thread.sleep(5000);}catch(InterruptedException e){System.out.println(e);}

      ArrayList<String> stringList=FileIO.loadFileToList(inputFileName);
      ArrayList<Integer> unsortedIntegers=new ArrayList<Integer>();
      for(String s : stringList) unsortedIntegers.add(Integer.parseInt(s));
      Heap<Integer> heap= new Heap<Integer>();
      heap.setHeapArrayList(unsortedIntegers);
      heap.sort();
      // heap.printHeap();

      // So upon reading the assignment seems like the output is the result itself
      //    -Why else say " with a failure, the output file should be deleted
      //     before termination"
      //    -This is also supported by the fact the input File is the checkpoint
      //     Makes sense to use output as end point or return result
      // So when getResult is called, its essentially returning the result(file)
      //    by its name
      FileIO.saveListToFile(heap.getHeapArrayList(),outputFileName);

      // System.out.println(result);
      // check HAZARD and set failure
      Double HAZARD =heap.getMemoryAccesses()*failureProbablity;
      Random random = new Random();
      Double randomNumber=random.nextDouble();
      failure=(0.5<randomNumber)&&(randomNumber<(0.5+HAZARD));
    }catch (ThreadDeath threadDeath){
      System.out.println("PrimaryThread dying: something went wrong or watchdog timeout");
      failure=true;
      throw new ThreadDeath();
    }

  }
  public String getResult(){
    // either return the result (a file) by its name, or null as an error return
    //    as per this line of the assignment
    if(!failure) return outputFileName;
    return null;
  }

}
