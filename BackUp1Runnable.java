import java.util.ArrayList;

public class BackUp1Runnable implements ResultRunnable{
  private Double failureProbablity;
  private String inputFileName;
  private String outputFileName;
  // private String result;
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
      // System.loadLibrary("_insertionsort");
      InsertionSort insertionSortObject= new InsertionSort();
      // double failureProbablity=0.000001;
      int result = insertionSortObject.insertionSortMethod(inputFileName,outputFileName, failureProbablity);
      System.out.println("RESULT"+result);
      failure = (result==-1)? true:false;
      System.out.print("failure var 1:");
      System.out.println(failure);
    }
    catch(ThreadDeath threadDeath){
      System.out.println("bakcup Thread dying: something went wrong or watchdog timeout");
      failure=true;
      throw new ThreadDeath();
    }
  }
  public String getResult(){
    // either return the result (a file) by its name, or null as an error return
    //    as per this line of the assignment
    System.out.print("failure var 1:");
    System.out.println(failure);
    if(!failure) return outputFileName;
    return null;
  }
}
