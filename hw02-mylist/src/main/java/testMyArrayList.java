import java.util.Collections;
import java.util.Arrays;
import java.util.List;

public class testMyArrayList extends DIYarrayList {
    public static void main(String[] args) {
 
        DIYarrayList<Integer> list = new DIYarrayList<Integer>();
        System.out.println("Initial size: " + list.size());
        Integer[] a = new Integer[20];
        for (int i=0; i < a.length; i++) a[i] = 20-i;
        Collections.addAll(list, a);
        System.out.print ("Initial list: ");
        for (Integer le : list) System.out.print (" " + le);
        System.out.println("\nSize after add: " + list.size());
        List<Integer> al = Arrays.asList(a);
        System.out.print ("Extra list: ");
        for (Integer ale : al) System.out.print (" " + ale);
        Collections.copy(list,al);
        System.out.print ("\nAfter copying: ");
        for (int k=0; k < list.size(); k++) System.out.print (" " + list.get(k));
        System.out.println("\nSize after copy: " + list.size());
        Collections.sort(list, null);
        System.out.print ("Sorted list: ");
        for (int m=0; m < list.size(); m++) System.out.print (" " + list.get(m));
    }
}