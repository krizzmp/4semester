import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Nillers-pc on 16-03-2016.
 */
public class Heapsort {

    public static void main(String[] args) {
        int n, counter;
        Scanner sc = new Scanner(System.in);
        counter = 0;
        ArrayList<Integer> al = new ArrayList<>();
        while (sc.hasNextInt()) {
            n = sc.nextInt();
            al.add(n);
            counter++;
        }
        PQ he = new PQHeap(counter);
        for(int i: al){
            he.insert(new Element(i, null));
        }
        Element el;
        while (counter >= 1) {
            el = he.extractMin();
            System.out.println(el.key);
            counter--;
        }
    }
}
