public class AvlTree<T extends Comparable<T>> extends BinarySearchTree<T>{
    @Override
    public void add(T value) {
        this.root = add(value, this.root);
    }

    protected BinaryNode<T> add(T value, BinaryNode<T> curNode) {
        counter.incrementCounter();
        if( curNode == null )
            return new BinaryNode<>( value );
        int compareResult = value.compareTo( curNode.value );
        if( compareResult < 0 )
            curNode.left = add( value, curNode.left );
        else if( compareResult > 0 )
            curNode.right = add( value, curNode.right );
        else
            ; // Pas de doublons
        return balance( curNode );
    }

    @Override
    public void remove(T value) {
        this.root = remove(value, this.root);
    }

    protected BinaryNode<T> remove(T value, BinaryNode<T> curNode) {
        if( curNode == null )
            return curNode; // Item not found; do nothing
        int compareResult = value.compareTo( curNode.value );
        if( compareResult < 0 )
            curNode.left = remove( value, curNode.left );
        else if( compareResult > 0 )
            curNode.right = remove( value, curNode.right );
        else if( curNode.left != null && curNode.right != null ) // Two children
        {
            curNode.value = findMin( curNode.right ).value;
            curNode.right = remove( curNode.value, curNode.right );
        }
        else
            curNode = ( curNode.left != null ) ? curNode.left : curNode.right;
        return balance( curNode );
    }

    private BinaryNode<T> balance( BinaryNode<T> curNode ){
        counter.incrementCounter();
        if( curNode == null )
            return curNode;
        if( curNode.left.height - curNode.right.height > 1 )
        {
            if( curNode.left.left.height >= curNode.left.right.height )
                curNode = rotateWithLeftChild( curNode );
            else
                curNode = doubleWithLeftChild( curNode );
        }
        else if( curNode.right.height - curNode.left.height > 1 )
        {
            if( curNode.right.right.height >= curNode.right.left.height )
                curNode = rotateWithRightChild( curNode );
            else
                curNode = doubleWithRightChild( curNode );
        }
        curNode.height = Math.max( curNode.left.height, curNode.right.height ) + 1;
        return curNode;
    }

    // Gauche - Gauche
    private BinaryNode<T> rotateWithLeftChild( BinaryNode<T> k2 ){
        counter.incrementCounter();
        BinaryNode<T> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        // Mettre à jour les hauteurs à la rotation
        k2.height = Math.max( k2.left.height, k2.right.height ) + 1;
        k1.height = Math.max( k1.left.height, k2.height ) + 1;
        return k1;
    }

    // Gauche - Droite
    private BinaryNode<T> doubleWithLeftChild( BinaryNode<T> k3 ){
        counter.incrementCounter();
        k3.left = rotateWithRightChild( k3.left );
        return rotateWithLeftChild( k3 );
    }

    // Droite - Droite
    private BinaryNode<T> rotateWithRightChild( BinaryNode<T> k1 ){
        counter.incrementCounter();
        BinaryNode<T> k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        // Mettre à jour les hauteurs à la rotation
        k1.height = Math.max( k1.left.height, k1.right.height ) + 1;
        k2.height = Math.max( k2.right.height, k1.height ) + 1;
        return k2;
    }

    // Droite - Gauche
    private BinaryNode<T> doubleWithRightChild( BinaryNode<T> k1 ){
        counter.incrementCounter();
        k1.right = rotateWithLeftChild( k1.right );
        return rotateWithRightChild( k1 );
    }
}
