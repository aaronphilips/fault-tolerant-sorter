


public class DataSorter{
  public static void main(String[] args) {
  
    String inputFileName = args[0];
    String outputFileName = args[1];
    Double primaryFailureProbability = Double.parseDouble(args[2]);
    Double secondaryFailureProbability = Double.parseDouble(args[3]);
    Integer timeLimit=Integer.parseInt(args[4]);

    ExecutiveRunnable executiveRunnable = new ExecutiveRunnable(inputFileName,
                                                       outputFileName,
                                                       primaryFailureProbability,
                                                       secondaryFailureProbability,
                                                       timeLimit);
    Thread executiveThread=new Thread(executiveRunnable);
    executiveThread.start();
    try{
      executiveThread.join();
    }catch(InterruptedException e){
      e.printStackTrace();
      System.out.println("executiveThread was interupted");
    }
  }
}
