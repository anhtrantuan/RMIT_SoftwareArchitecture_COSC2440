public interface MyMapInterface<T1, T2> {
    // Add or replace (if key exists) an association
    void put(T1 key, T2 val);

    // Return value given the key.
    T2 get(T1 k);

    // Print the details of all associations in the map.
    void print();
}
