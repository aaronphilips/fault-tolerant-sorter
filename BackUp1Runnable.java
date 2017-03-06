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

    }catch(ThreadDeath threadDeath){
    }
    public String getResult(){
      // either return the result (a file) by its name, or null as an error return
      //    as per this line of the assignment
      if(!failure) return outputFileName;
      return null;
    }
}
