


public class DataSorter{
  public static void main(String[] args) {
    System.out.println(args[0]);
    System.out.println(args[1]);
    System.out.println(args[2]);
    System.out.println(args[3]);
    System.out.println(args[4]);
    String inputFile = args[0];
    String outputFile = args[1];
    Double primaryFailureProbability = Double.parseDouble(args[2]);
    Double secondaryFailureProbability = Double.parseDouble(args[3]);
    Integer timeLimit=Integer.parseInt(args[4]);
    ExecutiveRunnable executiveRunnable = new ExecutiveRunnable(inputFile,outputFile,
                                                       primaryFailureProbability,
                                                       secondaryFailureProbability,
                                                       timeLimit);
    Thread executiveThread=new Thread(executiveRunnable);
    executiveThread.start();
    try{
      executiveThread.join();
      System.out.println("thread finished");
    }catch(InterruptedException e){
      e.printStackTrace();
      System.out.println("Thread was interupted");
    }
  }
}
