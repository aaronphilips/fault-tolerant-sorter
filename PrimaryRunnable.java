import java.util.ArrayList;

public class PrimaryRunnable implements Runnable{
  private Double failureProbablity;
  private String inputFileName;
  private ArrayList<Integer> result;
  public PrimaryRunnable(String inputFileName,Double failureProbablity){
    this.failureProbablity=failureProbablity;
    this.inputFileName=inputFileName;
  }
  public void run(){
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

  }
  public ArrayList<Integer> getResult(){
    return result;
  }

}
