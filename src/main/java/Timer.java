public class Timer {
    long start = 0;

    public void timerInit(){
        start = 0;
        start = System.nanoTime();
    }

    public long getTimerValue(){
        return System.nanoTime() - start;
    }
}
