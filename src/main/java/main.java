import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;


public class main {

    final static int MAX = 500000;
    final static int MIN = 0;
    final static Timer TIMER = new Timer();
    public static void main(String[] args) throws IOException {

        ArrayList<Integer> insertedValues = new ArrayList<>();

        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        AvlTree<Integer> avl = new AvlTree<>();
        BinarySearchTree<Integer> bstWorst = new BinarySearchTree<>();
        AvlTree<Integer> avlWorst = new AvlTree<>();

        ArrayList<String> csvBstInsertion = new ArrayList<>();
        ArrayList<String> csvBstSearch = new ArrayList<>();
        ArrayList<String> csvBstInsertionWorst = new ArrayList<>();
        writeCsvHeaders(csvBstInsertion);
        writeCsvHeaders(csvBstSearch);
        writeCsvHeaders(csvBstInsertionWorst);

        ArrayList<String> csvAvlInsertion = new ArrayList<>();
        ArrayList<String> csvAvlSearch = new ArrayList<>();
        ArrayList<String> csvAvlInsertionWorst = new ArrayList<>();
        writeCsvHeaders(csvAvlInsertion);
        writeCsvHeaders(csvAvlSearch);
        writeCsvHeaders(csvAvlInsertionWorst);

        // Insertion
        for (Integer i = 0; i < 10000; i++){
            // Méthode de trouver un nombre random trouvé sur :
            // https://stackoverflow.com/questions/363681/how-do-i-generate-random-integers-within-a-specific-range-in-java
            Integer value = ThreadLocalRandom.current().nextInt(MIN, MAX + 1);
            insertedValues.add(value);
            computeInsertionStats(value, bst, csvBstInsertion, i);
            computeInsertionStats(value, avl, csvAvlInsertion, i);
            computeInsertionStats(i, bstWorst, csvBstInsertionWorst, i);
            computeInsertionStats(i, avlWorst, csvAvlInsertionWorst, i);
        }

        bst.counter.resetCounter();
        avl.counter.resetCounter();

        // Search
        Integer index = 1;
        for (Integer value : insertedValues) {
            computeSearchStats(value, bst, csvBstSearch, index);
            computeSearchStats(value, avl, csvAvlSearch, index);
            index++;
        }

        // Write to file for plotting graphs
        writeToFile(new File("bstInsertion.csv"), csvBstInsertion);
        writeToFile(new File("avlInsertion.csv"), csvAvlInsertion);
        writeToFile(new File("bstSearch.csv"), csvBstSearch);
        writeToFile(new File("avlSearch.csv"), csvAvlSearch);

        writeToFile(new File("bstInsertionWorst.csv"), csvBstInsertionWorst);
        writeToFile(new File("avlInsertionWorst.csv"), csvAvlInsertionWorst);
    }

    private static void writeToFile(File file, ArrayList<String> csv) throws IOException{
        file.createNewFile();
        if (file.exists()){
            try(FileWriter filewriter = new FileWriter(file)){
                for (int i = 0; i < csv.size(); i+=3){
                    filewriter.write(csv.get(i) + ", " + csv.get(i+1) + ", " + csv.get(i+2) + ",\n");
                }
                filewriter.flush();
            }
        }
    }

    private static void computeInsertionStats(Integer insertedValue, BinarySearchTree<Integer> tree, ArrayList<String> csv, Integer index){
        int nodeCounter = index + 1;
        TIMER.timerInit();
        tree.add(insertedValue);
        int counter = tree.counter.getCounter();
        long time = TIMER.getTimerValue();
        
        csv.add(Integer.toString(nodeCounter));
        csv.add(Integer.toString(counter));
        csv.add(Integer.valueOf((int) time).toString()); // La conversion de long en int est valide puisque les temps d'exécutions sont rapides et les valeurs petites
    }

    private static void computeSearchStats(Integer value, BinarySearchTree<Integer> tree, ArrayList<String> csv, Integer index){        
        TIMER.timerInit();
        tree.contains(value);
        int counter = tree.counter.getCounter();
        long time = TIMER.getTimerValue();
        
        csv.add(index.toString());
        csv.add(Integer.toString(counter));
        csv.add(Integer.valueOf((int) time).toString()); // La conversion de long en int est valide puisque les temps d'exécutions sont rapides et les valeurs petites
    }

    private static void writeCsvHeaders(ArrayList<String> csv){
        csv.add("Number Of Node");
        csv.add("Number of operation");
        csv.add("Execution Time");
    }
}
