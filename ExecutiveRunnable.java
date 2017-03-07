import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;

// http://stackoverflow.com/questions/877096/how-can-i-pass-a-parameter-to-a-java-thread
public class ExecutiveRunnable implements Runnable{
  private String inputFileName ;
  private String outputFileName ;
  private Double primaryFailureProbability ;
  private Double secondaryFailureProbability ;
  private Integer timeLimit;

  public ExecutiveRunnable(String inputFileName,String outputFileName,
                         Double primaryFailureProbability,
                         Double secondaryFailureProbability,
                         Integer timeLimit){
    this.inputFileName = inputFileName;
    this.outputFileName = outputFileName;
    this.primaryFailureProbability = primaryFailureProbability;
    this.secondaryFailureProbability = secondaryFailureProbability;
    this.timeLimit=timeLimit;
  }

  public void run(){

    ResultRunnable<String> primaryRunnable = new PrimaryRunnable(inputFileName,outputFileName,primaryFailureProbability);

    Thread primaryThread=new Thread(primaryRunnable);
    Timer primaryTimer = new Timer();
		Watchdog primaryWatchdog = new Watchdog(primaryThread);
		primaryTimer.schedule(primaryWatchdog, timeLimit);
    primaryThread.start();
    try{
      primaryThread.join();
      primaryTimer.cancel();
    }catch(InterruptedException e){
      e.printStackTrace();
      System.out.println("primaryThread was interupted");
      return;
    }

    String resultFile=primaryRunnable.getResult();

    Adjudicator adjudicator= new Adjudicator();
    try{
      if(adjudicator.sortingAcceptanceTest(resultFile)){
        System.out.println("Primary Result passed");
        return;
      }else{
        System.out.println("PrimaryFailedException");
        throw new PrimaryFailedException();
      }
    }catch (PrimaryFailedException e) {
        FileIO.deleteFile(outputFileName);
        e.printStackTrace();
    }

    ArrayList<ResultRunnable> backUpVariants=new ArrayList<ResultRunnable>();
    BackUp1Runnable backUp1Runnable = new BackUp1Runnable(inputFileName,outputFileName,secondaryFailureProbability);
    backUpVariants.add(backUp1Runnable);
    Iterator<ResultRunnable> backUpVariantsIterator=backUpVariants.iterator();

    while(backUpVariantsIterator.hasNext()){
      ResultRunnable<String> backUpRunnable = backUpVariantsIterator.next();
      Thread backUpThread=new Thread(backUpRunnable);
      Timer backUpTimer = new Timer();
  		Watchdog backUpWatchdog = new Watchdog(backUpThread);
  		backUpTimer.schedule(backUpWatchdog, timeLimit);
      backUpThread.start();
      try{
        backUpThread.join();
        backUpTimer.cancel();
      }catch(InterruptedException e){
        e.printStackTrace();
        System.out.println("backUpThread was interupted");
        continue;
      }
      resultFile=backUpRunnable.getResult();
      if(adjudicator.sortingAcceptanceTest(resultFile)){
        System.out.println("Result passed");
        return;
      }
    }

    FileIO.deleteFile(outputFileName);
    throw new FailureException("All Backups failed");
  }

  private class Adjudicator {
    protected Boolean sortingAcceptanceTest(String resultFile){

      if(resultFile==null) return false;

      ArrayList<String> stringList=FileIO.loadFileToList(resultFile);
      ArrayList<Integer> sortedIntegers=new ArrayList<Integer>();
      for(String s : stringList) sortedIntegers.add(Integer.valueOf(s));
      Iterator<Integer> sortedIntegersIterator= sortedIntegers.iterator();
      Integer lastInteger=Integer.MIN_VALUE;
      Integer currentInteger;

      while(sortedIntegersIterator.hasNext()){
        currentInteger=sortedIntegersIterator.next();
        if(currentInteger<=lastInteger) {
          // System.out.println("current:"+currentInteger.toString()+" lastInteger:"+lastInteger.toString());
          return false;
        }
        lastInteger=currentInteger;
      }
      return true;
    }
  }
}
