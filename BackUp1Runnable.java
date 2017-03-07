import java.util.ArrayList;

public class BackUp1Runnable implements ResultRunnable<String>{
  private Double failureProbablity;
  private String inputFileName;
  private String outputFileName;
  private Boolean failure;
  public BackUp1Runnable(String inputFileName, String outputFileName, Double failureProbablity){
    this.failureProbablity=failureProbablity;
    this.inputFileName=inputFileName;
    this.outputFileName=outputFileName;
    this.failure=false;
  }

  public void run(){
    try{
      // try{Thread.sleep(5000);}catch(InterruptedException e){System.out.println(e);}
      InsertionSort insertionSortObject= new InsertionSort();
      int result = insertionSortObject.insertionSortMethod(inputFileName,outputFileName, failureProbablity);
      failure = (result==-1)? true:false;
    }
    catch(ThreadDeath threadDeath){
      System.out.println("backup Thread dying: something went wrong or watchdog timeout");
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
