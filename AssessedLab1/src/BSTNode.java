public class BSTNode<T1, T2> {
    private Associate<T1, T2> associate;
    private BSTNode<T1, T2> left, right;

    public BSTNode(T1 key, T2 value) {
        this.associate = new Associate<T1, T2>(key, value);
        this.left = null;
        this.right = null;
    }

    public T1 getKey() {
        return associate.getKey();
    }

    public T2 getValue() {
        return associate.getValue();
    }

    public BSTNode<T1, T2> getLeft() {
        return left;
    }

    public BSTNode<T1, T2> getRight() {
        return right;
    }

    // Add a descendant node to this.
    @SuppressWarnings("unchecked")
    public void add(BSTNode<T1, T2> descendant) {
        int comparison = ((Comparable<T1>) descendant.getKey()).compareTo(associate.getKey());

        // If new key is less than this node's key, add to the left.
        if (comparison < 0) {
            // If left node not exists, set the new node as left node.
            if (left == null) {
                left = descendant;
            } else {
                // Else, let left node handle the new node.
                left.add(descendant);
            }
        } else if (comparison > 0) {
            // Else if new key is more than this node's key, add to the right.

            // If right node not exists, set the new node as right node.
            if (right == null) {
                right = descendant;
            } else {
                // Else, let the right node handle the new node.
                right.add(descendant);
            }
        } else {
            // Else, replace this node with new value.
            associate.setVal(descendant.getValue());
        }
    }

    // Print this node and all its descendants in low-to-high order.
    public void print() {
        if (left != null) {
            left.print();
        }
        System.out.println(associate.getKey() + " : " + associate.getValue());
        if (right != null) {
            right.print();
        }
    }
}
