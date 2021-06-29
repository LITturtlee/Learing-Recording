import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("hello world");
        int[] a = new int[] {1,2,3,4,5};
        // printArray(a);
        int[] b = {1,2,4,5};
        printArray(b);
        // Collection c = new ArrayList();
        // Iterator it = c.iterator();
        // c.add(1);
        // c.add(2);
        // it.next();
    }
    public static void printArray(int[] a){
        for(int i = 0;i<a.length;i++){
            System.out.println(a[i]);
        }
    }
}
