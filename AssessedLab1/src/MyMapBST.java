public class MyMapBST<T1, T2> implements MyMapInterface<T1, T2> {

    private BSTNode<T1, T2> root;

    public MyMapBST() {
        root = null;
    }

    @Override
    public void put(T1 key, T2 val) {
        // Create new node.
        BSTNode<T1, T2> node = new BSTNode<T1, T2>(key, val);

        // If root not existed, set new node as root.
        if (root == null) {
            root = node;
        } else {
            // Else, add new node as descendant of root.
            root.add(node);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public T2 get(T1 k) {
        BSTNode<T1, T2> current = root;

        // Find from root down the node with matching key.
        while (current != null) {
            int comparison = ((Comparable) current.getKey()).compareTo(k);

            // If keys match, return current node's value.
            if (comparison == 0) {
                return current.getValue();
            } else if (comparison < 0) {
                // Else if current key is less than given key, move to right node.
                current = current.getRight();
            } else {
                // Else, move to left node.
                current = current.getLeft();
            }
        }

        // If no matching key found, return null.
        return null;
    }

    @Override
    public void print() {
        if (root != null) {
            root.print();
        }
    }
}
