public class Driver{
  public static void main(String[] args){
    System.loadLibrary("_insertionsort");
    InsertionSort insertionSortObject= new InsertionSort();
    double failureProbablity=0.000001;
    int result = insertionSortObject.insertionSortMethod("hey","Output2.txt", failureProbablity);
    System.out.println(result);
  }
}
