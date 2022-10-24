public class AvlTree<T extends Comparable<T>> extends BinarySearchTree<T>{
    @Override
    protected BinaryNode<T> add(T value, BinaryNode<T> curNode) {
        return balance(super.add(value, curNode));
    }

    @Override
    protected BinaryNode<T> remove(T value, BinaryNode<T> curNode) {
        return balance( super.remove(value, curNode));
    }

    private BinaryNode<T> balance( BinaryNode<T> curNode ){
        counter.incrementCounter();
        if( curNode == null )
            return curNode;
        if( height(curNode.left) - height(curNode.right) > 1 )
        {
            if( height(curNode.left.left) >= height(curNode.left.right) )
                curNode = rotateWithLeftChild( curNode );
            else
                curNode = doubleWithLeftChild( curNode );
        }
        else if( height(curNode.right) - height(curNode.left) > 1 )
        {
            if( height(curNode.right.right) >= height(curNode.right.left) )
                curNode = rotateWithRightChild( curNode );
            else
                curNode = doubleWithRightChild( curNode );
        }
        curNode.height = Math.max( height(curNode.left), height(curNode.right) ) + 1;
        return curNode;
    }

    // Gauche - Gauche
    private BinaryNode<T> rotateWithLeftChild( BinaryNode<T> k2 ){
        counter.incrementCounter();
        BinaryNode<T> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        // Mettre à jour les hauteurs à la rotation
        k2.height = Math.max( height(k2.left), height(k2.right) ) + 1;
        k1.height = Math.max( height(k1.left), k2.height ) + 1;
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
        k1.height = Math.max( height(k1.left), height(k1.right) ) + 1;
        k2.height = Math.max( height(k2.right), k1.height ) + 1;
        return k2;
    }

    // Droite - Gauche
    private BinaryNode<T> doubleWithRightChild( BinaryNode<T> k1 ){
        counter.incrementCounter();
        k1.right = rotateWithLeftChild( k1.right );
        return rotateWithRightChild( k1 );
    }

    private int height( BinaryNode<T> t )
    {
        return t == null ? -1 : t.height;
    }       
}
