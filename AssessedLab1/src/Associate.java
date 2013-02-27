public class Associate<T1, T2> {
    private T1 key;
    private T2 val;

    public Associate(T1 key, T2 val) {
        this.key = key;
        this.val = val;
    }

    public T2 getValue() {
        return val;
    }

    public T1 getKey() {
        return key;
    }

    public void setVal(T2 val) {
        this.val = val;
    }
}