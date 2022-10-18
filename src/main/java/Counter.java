public class Counter {
    private int operationCounter = 0;

    public int getCounter(){
        return operationCounter;
    }

    public void incrementCounter(){
        operationCounter++;
    }

    public void resetCounter(){
        operationCounter = 0;
    }
}
