public class BinarySearchTree<T extends Comparable<T>> extends BinaryTree<T> implements Tree<T>{
   
    public Counter counter = new Counter();

    @Override
    public void add(T data) {
        this.root = add(data, root);
    }

    protected BinaryNode<T> add(T value, BinaryNode<T> curNode) {
        counter.incrementCounter();
        // Insérer si position courante est une feuille
        if( curNode == null )
            return new BinaryNode<T>( value );
        // Sinon trouver position
        int compareResult = value.compareTo( curNode.value );
        if(compareResult < 0 )
            curNode.left = add( value, curNode.left );
        else if( compareResult > 0 )
            curNode.right = add( value, curNode.right );
        else
            ; // Rejeter en cas de doublon
        return curNode;

    }

    @Override
    public boolean contains(T value) {
        return contains(value, root);
    }

    private boolean contains(T value, BinaryNode<T> curNode) {
        counter.incrementCounter();
        if( curNode == null )
            return false;
        else if ( curNode.value == value)
            return true;
        
        int compareResult = value.compareTo( curNode.value );
        if( compareResult < 0 )
            return contains(value, curNode.left);
        return contains( value, curNode.right );
    }

    @Override
    public void remove(T value) {
        this.root = remove(value, root);
    }

    protected BinaryNode<T> remove(T value, BinaryNode<T> curNode) {
        // Si x absent, resortir
        if( curNode == null )
            return curNode;
        // Rechercher position de x
        int compareResult = value.compareTo( curNode.value );
        if( compareResult < 0 )
            curNode.left = remove( value, curNode.left );
        else if( compareResult > 0 )
            curNode.right = remove( value, curNode.right );
        // Une fois x trouvé
        else if( curNode.left != null && curNode.right != null ) // Cas deux enfants
        {
            curNode.value = findMin( curNode.right ).value;
            curNode.right = remove( curNode.value, curNode.right );
        }
        else // Cas trivial 
            curNode = ( curNode.left != null ) ? curNode.left : curNode.right;
        return curNode;
    }

    protected BinaryNode<T> findMin(BinaryNode<T> curNode){
        if( curNode == null )
            return null;
        else if( curNode.left == null )
            return curNode;
        return findMin( curNode.left );
    }
}
