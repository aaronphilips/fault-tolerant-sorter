import java.util.ArrayList;

public class PrimaryRunnable implements ResultRunnable<String>{
  private Double failureProbablity;
  private String inputFileName;
  private String outputFileName;
  // private String result;
  private Boolean failure;
  public PrimaryRunnable(String inputFileName, String outputFileName, Double failureProbablity){
    this.failureProbablity=failureProbablity;
    this.inputFileName=inputFileName;
    this.outputFileName=outputFileName;
    this.failure=false;
  }
  public void run(){
    try{
      // REMOVE THIS this is harsh delay timer
      // try{Thread.sleep(5000);}catch(InterruptedException e){System.out.println(e);}

      Heap<Integer> heap= new Heap<Integer>();

      failure=!(heap.sortingRoutine(inputFileName,outputFileName,failureProbablity));

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
