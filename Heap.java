import java.util.ArrayList;
import java.util.Collections;

// based on a heap sort class that I made in 2015
public class Heap<T extends Comparable<T>> {
	private ArrayList<T> heap;
	private Integer numberOfMemoryAccesses;

	public Heap(){
		heap= new ArrayList<T>();
		numberOfMemoryAccesses=0;
	}
	// added when arrays are accessed in COMPUTATION ALGORITHIM- not everywhere
	// also used for read AND Writing to a variable, in the same vein as what I did
	// in another simulation program in CMPUT379

	private void addNumberOfMemoryAccesses(int numberOfAdditionalMemoryAccesses){
			numberOfMemoryAccesses = numberOfMemoryAccesses +
															 	numberOfAdditionalMemoryAccesses;
	}
	public void insertIntoHeap(T a){
		numberOfMemoryAccesses=0;
		heap.add(a);
	}

	public void printHeap(){
		System.out.println(heap);
	}

	public ArrayList<T> getHeapArrayList(){
		return heap;
	}
	public void setHeapArrayList(ArrayList<T> inputArrayList){
		numberOfMemoryAccesses=0;
		heap=inputArrayList;
	}
	// the two immediate functions were and their use were added for this project
	// adding memory accesses
	private ArrayList<T> buildheap(ArrayList<T> inputarr){
		ArrayList<T> myArray = new ArrayList<T>(inputarr.size());
		for(int i=0;i<inputarr.size();i++){
			myArray.add(null);
		}
		for(int i=0;i<inputarr.size();i++){

			addNumberOfMemoryAccesses(2);
			myArray.set(i, inputarr.get(i));
			myArray=heapify(myArray,i);
		}
		return myArray;
	}
	public Integer getMemoryAccesses(){
		return numberOfMemoryAccesses;
	}

	// adding memory accesses
	private ArrayList<T> heapify(ArrayList<T> unsorted, int node){
		addNumberOfMemoryAccesses(2);
		if(node!=0 & unsorted.get((node-1)/2).compareTo(unsorted.get(node))<0){
			addNumberOfMemoryAccesses(2);
			Collections.swap(unsorted, node,(node-1)/2);
			return heapify(unsorted,(node-1)/2);
		}
		return unsorted;
	}
	// adding memory accesses
	private ArrayList<T> siftdown(ArrayList<T> unsorted){
		int treesize=unsorted.size();
		while(treesize>1){

			addNumberOfMemoryAccesses(2);
			Collections.swap(unsorted, 0,treesize-1);
			treesize--;
			unsorted=subsort(unsorted,0,treesize);
		}
		return unsorted;
	}
	// adding memory accesses
	private ArrayList<T> subsort(ArrayList<T> unsorted,int node, int treesize){
		if((node*2)+1>treesize|(node*2)+2>treesize){
			return unsorted;
		}
		addNumberOfMemoryAccesses(1);
		T parent= unsorted.get(node);
		addNumberOfMemoryAccesses(1);
		T leftchild=unsorted.get((node*2)+1);
		addNumberOfMemoryAccesses(1);
		T rightchild=unsorted.get((node*2)+2);
		if((parent.compareTo(leftchild)<0&(node*2)+1<treesize)|
				(parent.compareTo(rightchild)<0&(node*2)+2<treesize)){
					if(leftchild.compareTo(rightchild)>=0|(node*2)+2>=treesize){
						addNumberOfMemoryAccesses(2);
						Collections.swap(unsorted, node,(node*2)+1);
						unsorted=subsort(unsorted,(node*2)+1,treesize);
					}else{
						addNumberOfMemoryAccesses(2);
						Collections.swap(unsorted, node,(node*2)+2);
						unsorted=subsort(unsorted,(node*2)+2,treesize);
					}
				}
				return unsorted;
			}
	// adding memory accesses
	public void sort(){
		numberOfMemoryAccesses=0;
		heap=buildheap(heap);
		heap=siftdown(heap);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Heap<String> myheap= new Heap<String>();
		myheap.insertIntoHeap("abcd");
		myheap.insertIntoHeap("abc");
		myheap.insertIntoHeap("a");
		myheap.insertIntoHeap("ab");
		myheap.insertIntoHeap("abcdef");
		myheap.insertIntoHeap("abcde");
		System.out.println(myheap.getMemoryAccesses());
		myheap.printHeap();
		myheap.sort();
		System.out.println(myheap.getMemoryAccesses());
		myheap.printHeap();

		Heap<Integer> myOtherHeap= new Heap<Integer>();
		myOtherHeap.insertIntoHeap(-23);
		myOtherHeap.insertIntoHeap(76);
		myOtherHeap.insertIntoHeap(74);
		myOtherHeap.insertIntoHeap(-27);
		myOtherHeap.insertIntoHeap(-27);
		myOtherHeap.insertIntoHeap(1);
		myOtherHeap.insertIntoHeap(0);
		System.out.println(myOtherHeap.getMemoryAccesses());
		myOtherHeap.printHeap();
		myOtherHeap.sort();
		System.out.println(myOtherHeap.getMemoryAccesses());
		myOtherHeap.printHeap();

		ArrayList<Integer> myIntegerArrayList=new ArrayList<Integer>();
		myIntegerArrayList.add(0);
		myIntegerArrayList.add(1);
		myIntegerArrayList.add(45);
		myIntegerArrayList.add(-22);
		myIntegerArrayList.add(0);
		myIntegerArrayList.add(36);
		myIntegerArrayList.add(-23);
		myIntegerArrayList.add(46);
		myOtherHeap.setHeapArrayList(myIntegerArrayList);
		System.out.println(myOtherHeap.getMemoryAccesses());
		myOtherHeap.printHeap();
		myOtherHeap.sort();
		System.out.println(myOtherHeap.getMemoryAccesses());
		myOtherHeap.printHeap();

		myIntegerArrayList=new ArrayList<Integer>();
		myIntegerArrayList.add(0);
		myIntegerArrayList.add(1);
		myIntegerArrayList.add(45);
		myIntegerArrayList.add(-22);
		myIntegerArrayList.add(0);
		myIntegerArrayList.add(36);
		myIntegerArrayList.add(-23);
		myIntegerArrayList.add(46);
		myOtherHeap.setHeapArrayList(myIntegerArrayList);
		System.out.println(myOtherHeap.getMemoryAccesses());
		myOtherHeap.printHeap();
		myOtherHeap.sort();
		System.out.println(myOtherHeap.getMemoryAccesses());
		myOtherHeap.printHeap();
		// Heap<Integer> heap=new Heap(myIntegerArrayList);
		// heap.printHeap();
		// heap.heapSort();
		// heap.printHeap();


	}
}
