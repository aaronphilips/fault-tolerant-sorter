import java.util.ArrayList;

public class PrimaryRunnable implements Runnable{
  private Double failureProbablity;
  private String inputFileName;
  private ArrayList<Integer> result;
  private Boolean failure;
  public PrimaryRunnable(String inputFileName,Double failureProbablity){
    this.failureProbablity=failureProbablity;
    this.inputFileName=inputFileName;
  }
  public void run(){
    try{
      // REMOVE THIS this is harsh delay timer
      try{Thread.sleep(5000);}catch(InterruptedException e){System.out.println(e);}
      //move this to load into variants
      ArrayList<String> strList=FileIO.loadFileToList(inputFileName);
      ArrayList<Integer> unsortedIntegers=new ArrayList<Integer>();
      for(String s : strList) unsortedIntegers.add(Integer.parseInt(s));
      Heap<Integer> heap= new Heap<Integer>();
      heap.setHeapArrayList(unsortedIntegers);
      heap.sort();
      // heap.printHeap();
      result=heap.getHeapArrayList();
      // System.out.println(result);
      // check HAZARD and set failure
      // heap.getMemoryAccesses()
      failure=false;
    }catch (ThreadDeath threadDeath){
      System.out.println("PrimaryThread dying: something went wrong or watchdog timeout");
      failure=true;
      throw new ThreadDeath();
    }

  }
  public ArrayList<Integer> getResult(){
    if(!failure) return result;
    return null;
  }

}
