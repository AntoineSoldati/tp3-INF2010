import java.io.Console;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;


public class main {
    public static void main(String[] args) throws IOException {

        final int MAX = 500;
        final int MIN = 0;

        Timer timer = new Timer();
        AvlTree<Integer> avl = new AvlTree<Integer>();
        BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();

        long[][] avlOperationStats = new long[100][2];
        long[][] bstOperationStats = new long[100][2];
        
        //CSV file for graph 
        ArrayList<String> csvBst = new ArrayList<>();
        csvBst.add("Number Of Node");
        csvBst.add("Number of operation");
        csvBst.add("Execution Time");

        ArrayList<String> csvAvl = new ArrayList<>();
        csvAvl.add("Number Of Node");
        csvAvl.add("Number of operation");
        csvAvl.add("Execution Time");

        for (Integer i = 0; i < 100; i++){
            Integer nodeCounter = i + 1;
            //Méthode de trouver un nombre random trouvé sur : 
            //https://stackoverflow.com/questions/363681/how-do-i-generate-random-integers-within-a-specific-range-in-java
            int value = ThreadLocalRandom.current().nextInt(MIN, MAX + 1);

            timer.timerInit();
            bst.counter.resetCounter();
            bst.add(value);
            Integer counter = bst.counter.getCounter();
            Long time = timer.getTimerValue();
            bstOperationStats[i][0] = counter;
            bstOperationStats[i][1] = time;
            
            csvBst.add(nodeCounter.toString());
            csvBst.add(counter.toString());
            csvBst.add(Integer.valueOf(time.intValue()).toString()); //La conversion de long en int est valide puisque 
                  
            //les temps d'exécutions sont rapides et les valeurs petites
            timer.timerInit();
            avl.counter.resetCounter();
            avl.add(value);
            counter = avl.counter.getCounter();
            time = timer.getTimerValue();
            avlOperationStats[i][0] = counter;
            avlOperationStats[i][1] = time;

            csvAvl.add(nodeCounter.toString());
            csvAvl.add(counter.toString());
            csvAvl.add(Integer.valueOf(time.intValue()).toString()); //La conversion de long en int est valide puisque 
                                                                    //les temps d'exécutions sont rapides et les valeurs petites

            //System.out.println("\nBinary Search Tree number of operations : " + bstOperationStats[i][0] + " and time of execution : " + bstOperationStats[i][1] + " for " + (i + 1) + "'n insertion");
            //System.out.println("AVL tree number of operations : " + avlOperationStats[i][0] + " and time of execution : " + avlOperationStats[i][1] + " for " + (i + 1) + "'n insertion");
        }

        File fileBst = new File("bstInsertion.txt");
        fileBst.createNewFile();

        File fileAvl = new File("avlInsertion.txt");
        fileAvl.createNewFile();

        if (fileBst.exists() && fileAvl.exists()){ //Faire une fonction ou une classe
            try(FileWriter filewriter = new FileWriter(fileBst);){
                for (int i = 0; i < csvBst.size(); i++){
                    filewriter.write(csvBst.get(i));
                    filewriter.write(";");
                }
                filewriter.flush();
                filewriter.close();
            }

            try(FileWriter filewriter = new FileWriter(fileAvl);){
                for (int i = 0; i < csvAvl.size(); i++){
                    filewriter.write(csvAvl.get(i));
                    filewriter.write(";");
                }
                filewriter.flush();
                filewriter.close();
            }
        }
    }
}
