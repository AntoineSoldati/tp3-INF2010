import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;


public class main {

    final static int MAX = 500;
    final static int MIN = 0;
    final static Timer TIMER = new Timer();
    public static void main(String[] args) throws IOException {

        ArrayList<Integer> insertedValues = new ArrayList<Integer>();

        AvlTree<Integer> avl = new AvlTree<Integer>();
        BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
         
        ArrayList<String> csvBstInsertion = new ArrayList<>();
        ArrayList<String> csvBstSearch = new ArrayList<>();
        ArrayList<String> csvBstInsertionWorst = new ArrayList<>();
        writeCsvHeaders(csvBstSearch);
        writeCsvHeaders(csvBstInsertion);
        writeCsvHeaders(csvBstInsertionWorst);

        ArrayList<String> csvAvlInsertion = new ArrayList<>();
        ArrayList<String> csvAvlSearch = new ArrayList<>();
        ArrayList<String> csvAvlInsertionWorst = new ArrayList<>();
        writeCsvHeaders(csvAvlSearch);
        writeCsvHeaders(csvAvlInsertion);
        writeCsvHeaders(csvAvlInsertionWorst);

        //Insertion
        for (Integer i = 0; i < 100; i++){
            //Méthode de trouver un nombre random trouvé sur : 
            //https://stackoverflow.com/questions/363681/how-do-i-generate-random-integers-within-a-specific-range-in-java
            Integer value = ThreadLocalRandom.current().nextInt(MIN, MAX + 1);
            insertedValues.add(value);
            computeInsertionStats(value, bst, csvBstInsertion, i);
            computeInsertionStats(value, avl, csvAvlInsertion, i);
            computeInsertionStats(i, bst, csvBstInsertionWorst, i);
        }
        
        //Search
        Integer index = 1;
        for (Integer value : insertedValues) {
            computeSearchStats(value, bst, csvBstSearch, index);
            computeSearchStats(value, avl, csvAvlSearch, index++);
        }

        //Write to file for plotting graphs
        File fileBstInsertion = new File("bstInsertion.txt");
        fileBstInsertion.createNewFile();

        File fileAvlInsertion = new File("avlInsertion.txt");
        fileAvlInsertion.createNewFile();

        File fileBstSearch = new File("bstSearch.txt");
        fileBstSearch.createNewFile();

        File fileAvlSearch = new File("avlSearch.txt");
        fileAvlSearch.createNewFile();

        File fileBstInsertionWorst = new File("bstInsertionWorst.txt");
        fileBstInsertionWorst.createNewFile();

        writeToFile(fileBstInsertion, csvBstInsertion);
        writeToFile(fileAvlInsertion, csvAvlInsertion);
        writeToFile(fileBstSearch, csvBstSearch);
        writeToFile(fileAvlSearch, csvAvlSearch);
        writeToFile(fileBstInsertionWorst, csvBstInsertionWorst);
    }

    private static void writeToFile(File file, ArrayList<String> csv) throws IOException{
        if (file.exists()){
            try(FileWriter filewriter = new FileWriter(file);){
                for (int i = 0; i < csv.size(); i++){
                    filewriter.write(csv.get(i));
                    filewriter.write(";");
                }
                filewriter.flush();
                filewriter.close();
            }
        }
    }

    private static void computeInsertionStats(Integer insertedValue, BinarySearchTree<Integer> tree, ArrayList<String> csv, Integer index){
        Integer nodeCounter = index + 1;
        
        TIMER.timerInit();
        tree.counter.resetCounter();
        tree.add(insertedValue);
        Integer counter = tree.counter.getCounter();
        Long time = TIMER.getTimerValue();
        
        csv.add(nodeCounter.toString());
        csv.add(counter.toString());
        csv.add(Integer.valueOf(time.intValue()).toString()); //La conversion de long en int est valide puisque les temps d'exécutions sont rapides et les valeurs petites
    }

    private static void computeSearchStats(Integer value, BinarySearchTree<Integer> tree, ArrayList<String> csv, Integer index){        
        TIMER.timerInit();
        tree.counter.resetCounter();
        tree.contains(value);
        Integer counter = tree.counter.getCounter();
        Long time = TIMER.getTimerValue();
        
        csv.add(index.toString());
        csv.add(counter.toString());
        csv.add(Integer.valueOf(time.intValue()).toString()); //La conversion de long en int est valide puisque les temps d'exécutions sont rapides et les valeurs petites
    }

    private static ArrayList<String> writeCsvHeaders(ArrayList<String> csv){
        csv.add("Number Of Node");
        csv.add("Number of operation");
        csv.add("Execution Time");

        return csv;
    }
}
