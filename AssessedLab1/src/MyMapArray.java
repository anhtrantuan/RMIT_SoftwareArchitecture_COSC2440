import java.util.ArrayList;

public class MyMapArray<T1, T2> implements MyMapInterface<T1, T2> {
    private ArrayList<Associate<T1, T2>> elements;

    public MyMapArray() {
        elements = new ArrayList<Associate<T1, T2>>();
    }

    @Override
    public void put(T1 key, T2 val) {
        // Find the associate (if existed) with given key and modify it.
        Associate<T1, T2> associate = null;
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getKey().equals(key)) {
                associate = elements.get(i);
                associate.setVal(val);
                i = elements.size();
            }
        }

        // If key not existed, add new key to list.
        if (associate == null) {
            associate = new Associate<T1, T2>(key, val);
            elements.add(associate);
        }
    }

    @Override
    public T2 get(T1 k) {
        // Find the associate (if existed) with given key and return its value.
        for (Associate<T1, T2> element : elements) {
            if (element.getKey() == k) {
                return element.getValue();
            }
        }

        // If key not existed, return null;
        return null;
    }

    @Override
    public void print() {
        for (Associate<T1, T2> element : elements) {
            System.out.println(element.getKey() + " : " + element.getValue());
        }
    }
}
