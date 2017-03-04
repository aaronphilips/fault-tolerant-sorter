import java.util.ArrayList;
import java.util.Collections;

// a heap sort class that I made in 2015
public class Heap<T extends Comparable<T>> {
	private ArrayList<T> heap;

	public Heap(){
		heap= new ArrayList<T>();
	}

	public void insertIntoHeap(T a){
		heap.add(a);
	}

	public void printHeap(){
		System.out.println(heap);
	}

	public ArrayList<T> getHeapArrayList(){
		return heap;
	}
	public void setHeapArrayList(ArrayList<T> inputArrayList){
		heap=inputArrayList;
	}
	private ArrayList<T> buildheap(ArrayList<T> inputarr){
		ArrayList<T> myArray = new ArrayList<T>(inputarr.size());
		for(int i=0;i<inputarr.size();i++){	myArray.add(null);	}
		for(int i=0;i<inputarr.size();i++){
			myArray.set(i, inputarr.get(i));
			myArray=heapify(myArray,i);
			}
		return myArray;
	}

	private ArrayList<T> heapify(ArrayList<T> unsorted, int node){
		if(node!=0 & unsorted.get((node-1)/2).compareTo(unsorted.get(node))<0){
			Collections.swap(unsorted, node,(node-1)/2);
			return heapify(unsorted,(node-1)/2);
		}
		return unsorted;
	}

	private ArrayList<T> siftdown(ArrayList<T> unsorted){
		int treesize=unsorted.size();
		while(treesize>1){
			int node=0;
			Collections.swap(unsorted, node,treesize-1);
			treesize--;
			unsorted=subsort(unsorted,node,treesize);
			}
	return unsorted;
	}

	private ArrayList<T> subsort(ArrayList<T> unsorted,int node, int treesize){
		if((node*2)+1>treesize|(node*2)+2>treesize){return unsorted;}
		T parent= unsorted.get(node);
		T leftchild=unsorted.get((node*2)+1);
		T rightchild=unsorted.get((node*2)+2);
		if((parent.compareTo(leftchild)<0&(node*2)+1<treesize)|
				(parent.compareTo(rightchild)<0&(node*2)+2<treesize)){
			if(leftchild.compareTo(rightchild)>=0|(node*2)+2>=treesize){
				Collections.swap(unsorted, node,(node*2)+1);
				unsorted=subsort(unsorted,(node*2)+1,treesize);
			}
			else{
				Collections.swap(unsorted, node,(node*2)+2);
				unsorted=subsort(unsorted,(node*2)+2,treesize);
			}
		}
		return unsorted;
	}

	public void sort(){
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
		myheap.printHeap();
		myheap.sort();
		myheap.printHeap();

		Heap<Integer> myOtherHeap= new Heap<Integer>();
		myOtherHeap.insertIntoHeap(-23);
		myOtherHeap.insertIntoHeap(76);
		myOtherHeap.insertIntoHeap(74);
		myOtherHeap.insertIntoHeap(-27);
		myOtherHeap.insertIntoHeap(-27);
		myOtherHeap.insertIntoHeap(1);
		myOtherHeap.insertIntoHeap(0);
		myOtherHeap.printHeap();
		myOtherHeap.sort();
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
		myOtherHeap.printHeap();
		myOtherHeap.sort();
		myOtherHeap.printHeap();
		// Heap<Integer> heap=new Heap(myIntegerArrayList);
		// heap.printHeap();
		// heap.heapSort();
		// heap.printHeap();


	}
}
