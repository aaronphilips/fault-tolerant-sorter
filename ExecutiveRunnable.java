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

    PrimaryRunnable primaryRunnable = new PrimaryRunnable(inputFileName,outputFileName,primaryFailureProbability);

    Thread primaryThread=new Thread(primaryRunnable);
    Timer primaryTimer = new Timer();
		Watchdog primaryWatchdog = new Watchdog(primaryThread);
		primaryTimer.schedule(primaryWatchdog, timeLimit);
    primaryThread.start();
    try{
      primaryThread.join();
      primaryTimer.cancel();
      System.out.println("primaryThread finished");
    }catch(InterruptedException e){
      e.printStackTrace();
      System.out.println("Thread was interupted");
      return;
    }

    String resultFile=primaryRunnable.getResult();

    Adjudicator adjudicator= new Adjudicator();
    try{
      if(adjudicator.sortingAcceptanceTest(resultFile)){
        System.out.println("Result passed");
        // return sortedIntegers;
        // FileIO.saveListToFile(sortedIntegers,outputFile);
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
    BackUp1Runnable backUp1Runnable = new BackUp1Runnable(inputFileName,outputFileName,primaryFailureProbability);
    backUpVariants.add(backUp1Runnable);
    Iterator<ResultRunnable> backUpVariantsIterator=backUpVariants.iterator();

    while(backUpVariantsIterator.hasNext()){
      ResultRunnable backUpRunnable = backUpVariantsIterator.next();
      Thread backUpThread=new Thread(backUpRunnable);
      Timer backUpTimer = new Timer();
  		Watchdog backUpWatchdog = new Watchdog(backUpThread);
  		backUpTimer.schedule(backUpWatchdog, timeLimit);
      backUpThread.start();
      try{
        backUpThread.join();
        backUpTimer.cancel();
        System.out.println("backUpThread finished");
      }catch(InterruptedException e){
        e.printStackTrace();
        System.out.println("Thread was interupted");
        return;
      }
      resultFile=backUpRunnable.getResult();
      System.out.println("back up happened resultFile:"+resultFile);
      if(adjudicator.sortingAcceptanceTest(resultFile)){
        System.out.println("Result passed");
        return;
      }
    }

    FileIO.deleteFile(outputFileName);
    throw new FailureException();

    // catch local exception by basically doing this

    // setup backups
    // add to list of runnables



  }

  private class Adjudicator {
    protected Boolean sortingAcceptanceTest(String resultFile){
      // System.out.println("dsajklhdsfd");
      // System.out.println(sortedIntegers==null);
      if(resultFile==null) return false;
      System.out.println("got here");
      ArrayList<String> stringList=FileIO.loadFileToList(resultFile);
      ArrayList<Integer> sortedIntegers=new ArrayList<Integer>();
      for(String s : stringList) sortedIntegers.add(Integer.parseInt(s));
      Iterator<Integer> sortedIntegersIterator= sortedIntegers.iterator();
      Integer lastInteger=Integer.MIN_VALUE;
      Integer currentInteger;

      while(sortedIntegersIterator.hasNext()){
        currentInteger=sortedIntegersIterator.next();
        // System.out.print("Last:"+lastInteger.toString()+"  Current:"+currentInteger.toString()+"   ");
        // System.out.println(currentInteger<lastInteger);

        if(currentInteger<lastInteger) return false;

        lastInteger=currentInteger;
      }
      return true;
    }
  }
}
