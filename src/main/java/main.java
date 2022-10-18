import java.io.Console;
import java.util.concurrent.ThreadLocalRandom;

public class main {
    public static void main(String[] args) {

        Timer timer = new Timer();
        AvlTree<Integer> avl = new AvlTree<Integer>();
        BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();

        long[][] avlOperationStats = new long[100][2];
        long[][] bstOperationStats = new long[100][2];
        
        int max = 500;
        int min = 0;

        for (int i = 0; i < 100; i++){
            //Méthode de trouver un nombre random trouvé sur : 
            //https://stackoverflow.com/questions/363681/how-do-i-generate-random-integers-within-a-specific-range-in-java
            int value = ThreadLocalRandom.current().nextInt(min, max + 1);

            timer.timerInit();
            bst.counter.resetCounter();
            bst.add(value);
            bstOperationStats[i][0] = bst.counter.getCounter();
            bstOperationStats[i][1] = timer.getTimerValue();

            timer.timerInit();
            avl.counter.resetCounter();
            avl.add(value);
            avlOperationStats[i][0] = avl.counter.getCounter();
            avlOperationStats[i][1] = timer.getTimerValue();

            System.out.println("\nBinary Search Tree number of operations : " + bstOperationStats[i][0] + " and time of execution : " + bstOperationStats[i][1] + " for " + (i + 1) + "'n insertion");
            System.out.println("AVL tree number of operations : " + avlOperationStats[i][0] + " and time of execution : " + avlOperationStats[i][1] + " for " + (i + 1) + "'n insertion");
        }
    }
}
