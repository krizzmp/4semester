/**
 * Created by Nillers-pc on 16-03-2016.
 */
public class PQHeap implements PQ {

    private int size;
    private Element[] heap;
    private int pos;

    public PQHeap(int maxElms){
        size = maxElms+221;
        heap = new Element[size];
        pos = 1;
    }

    @Override
    public Element extractMin() {
        Element max;
        max = heap[1];
        heap[1] = heap[pos-1];
        pos = pos - 1;
        minHeapify(1);
        return max;
    }
/*
The mothod "insert" copyes the current count amount of element in the array, as this will be the position
on the new element in the index.
Then it increment the count by one to make it ready for the next element.
The element is then inserted to then last used index in the array.
Then there is a loop that check if the new element is on the right spot and start to swap on to the right index.
 */
    @Override
    public void insert(Element e) {

        int elpos = pos;
        pos = pos + 1;
        heap[elpos] = e;
        while(elpos > 1 && heap[parent(elpos)].key > heap[elpos].key){
            exchange(elpos, parent(elpos));
            elpos = parent(elpos);
        }

    }
    public void minHeapify(int i){
        int l = leftChild(i);
        int r = rightChild(i);
        int largest;
        if(l <= pos && heap[l].key < heap[i].key) {
            largest = l;
        }else{
            largest = i;
        }
        if(r <= pos && heap[r].key < heap[largest].key){
            largest = r;
        }
        if(largest != i){
            exchange(i, largest);
            minHeapify(largest);
        }


    }

    public void exchange(int i, int l){
        Element tmp = heap[i];
        heap[i] = heap[l];
        heap[l] = tmp;
    }

    public int parent(int i){
        return i/2;
    }

    public int leftChild(int i){
        return 2*i;
    }

    public int rightChild(int i){
        return 2*i+1;
    }



}
