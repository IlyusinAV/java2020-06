import java.util.*;

public class DIYarrayList<T> implements List<T> {
    private int size = 0;
    private int capacity = 0;
    private final int CAPACITY = 16;
    private Object[] array;

    public DIYarrayList() {
        capacity = CAPACITY;
        array = new Object[capacity];
    }

    public DIYarrayList(int capacity) {
        this.capacity = capacity;
        array = new Object[capacity];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public Object[] toArray() {
        Object[] newArray = new Object[size];
        System.arraycopy(array, 0, newArray, 0, size);
        return newArray;
    }

    @Override
    public boolean add(Object obj) {
        if (size >= capacity) {
            increaseCapacity();
        }
        array[size] = obj;
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if ((size == 0)) {
            return false;
        }
        int i;
        for (i = 0; i < size; i++) {
            if (array[i] == null && o == null) {
                break;
            }
            if ((array[i] != null) && (array[i].equals(o))) {
                break;
            }
        }
        if (i < size) {
            shiftToLeft(i);
            return true;
        }
        return false;
    }

    @Override
    public boolean addAll(Collection c) {
        if (c == null) {
            return false;
        }
        if (c.isEmpty()) {
            return false;
        }
        for (Object item : c) {
            add(item);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection c) {
        if (c == null) {
            return false;
        }
        if (c.isEmpty() || (index < 0)) {
            return false;
        }
        if (index > size) {
            index = size;
        }
        for (Object item : c) {
            add(index++, item);
        }
        return true;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }
        size = 0;
    }

    @Override
    public T get(int index) {
        if ((index < size) && (index >= 0)) {
            return (T) array[index];
        }
        return null;
    }

    @Override
    public Object set(int index, Object element) {
        if ((index < size) && (index >= 0)) {
            Object o = array[index];
            array[index] = element;
            return o;
        }
        return null;
    }

    @Override
    public void add(int index, Object element) throws IndexOutOfBoundsException {
        if (index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (size + 1 >= capacity) {
            increaseCapacity();
        }
        if (index > size) {
            index = size;
        }
        for (int i = size; i >= index; i--) {
            array[i + 1] = array[i];
        }
        array[index] = element;
        size++;
    }

    @Override
    public T remove(int index) {
        Object o = null;
        if ((index < size) && (index >= 0)) {
            o = get(index);
            shiftToLeft(index);
        }
        return (T) o;
    }

    @Override
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (array[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (o.equals(array[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int lastIndex = -1;
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (array[i] == null) {
                    lastIndex = i;
                }
            }
            return lastIndex;
        } else {
            for (int i = 0; i < size; i++) {
                if (o.equals(array[i])) {
                    lastIndex = i;
                }
            }
        }
        return lastIndex;
    }

    @Override
    public Iterator iterator() throws UnsupportedOperationException {
        Iterator it = new Iterator() {
            private int currentIndex = 0;
            
            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }
                
            @Override
            public Object next() {
                return array[currentIndex++];
            }

        };
        return it;
    }

    @Override
    public ListIterator listIterator() {
        ListIterator lit = new ListIterator() {
            private int currentIndex = 0;
            
            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }
                
            @Override
            public boolean hasPrevious() {
                return currentIndex > 0;
            }
    
            @Override
            public Object next() {
                return array[currentIndex++];
            }

            @Override
            public Object previous() {
                return array[currentIndex--];
            }

            @Override
            public int nextIndex() {
                return (currentIndex+1);
            }

            @Override
            public int previousIndex() {
                return (currentIndex-1);
            }

            @Override
            public void add(Object element) throws UnsupportedOperationException {
                throw new UnsupportedOperationException();
            }

            @Override
            public void remove() throws UnsupportedOperationException {
                throw new UnsupportedOperationException();
            }

            @Override
            public void set(Object element) {
                array[currentIndex] = element;                    
            }
        };
        return lit;
    }

    @Override
    public ListIterator listIterator(int index) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List subList(int fromIndex, int toIndex) {
        if (fromIndex > toIndex) {
            int temp = fromIndex;
            fromIndex = toIndex;
            toIndex = temp;
        }
        if ((fromIndex < 0) || (toIndex > size)) {
            return null;
        }
        List myArrayList = new DIYarrayList(toIndex - fromIndex);
        for (int i = fromIndex; i < toIndex; i++) {
            myArrayList.add(array[i]);
        }
        return myArrayList;
    }

    @Override
    public boolean retainAll(Collection c) {
        if (c == null) {
            return true;
        }
        if (c.size() == 0) {
            clear();
            return true;
        }
        int i = 0;
        boolean modyfied = false;
        while (i < size) {
            if (c.contains(array[i])) {
                i++;
            } else {
                shiftToLeft(i);
                modyfied = true;
            }
        }
        return modyfied;
    }

    @Override
    public boolean removeAll(Collection c) {
        if (c == null) {
            return false;
        }
        if ((c.size() == 0) || (size == 0)) {
            return false;
        }
        boolean modyfied = false;
        int i = 0;
        while (i < size) {
            if (c.contains(array[i])) {
                shiftToLeft(i);
                modyfied = true;
            } else {
                i++;
            }
        }
        return modyfied;
    }

    @Override
    public boolean containsAll(Collection c) {
        if (c == null) {
            return false;
        }
        if (c.size() == 0) {
            return true;
        }
        for (Object e : c) {
            if (contains(e)) {
                ;
            } else {
                return false;
            }
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a.length < size)
            // Make a new array of a's runtime type, but my contents:
            return (T[]) Arrays.copyOf(array, size, a.getClass());
        System.arraycopy(array, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
    }

    private void increaseCapacity() {
        capacity = capacity * 2;
        Object[] newArray = new Object[capacity];
        for (int i = 0; i < size; i++) {
            newArray[i] = array[i];
            array[i] = null;
        }
        array = newArray;
    }

    private void shiftToLeft(int start) {
        size--;
        if (size <= 0) {
            return;
        }
        if (size != start) {
            System.arraycopy(array, start + 1, array, start, size - start);
        }
        array[size] = null;
    }
    private void trimToSizeArray() {
        capacity = size + 1;
        Object[] newArray = new Object[capacity];
        System.arraycopy(array, 0, newArray, 0, size);
        array = newArray;
    }

}