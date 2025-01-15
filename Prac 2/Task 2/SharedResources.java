import java.util.concurrent.locks.Lock;

public class SharedResources {
    Lock l;
    private final Bakery lock = new Bakery(THREAD_COUNT);
    private static final int THREAD_COUNT = 5;

	public void access(){
        String threadName = Thread.currentThread().getName();
        for (int j = 0; j < 2; j++) { // Number of times to re-enter the critical section
            lock.lock();
            try {
                // Critical section
                int maxLabel = lock.findMaxLabel();
                System.out.println(threadName + ": flag[" + j + "] = true , label[" + j + "] = " + (maxLabel + 1));
                Thread.sleep((long) (200 + Math.random() * 801)); // Sleep for 200 to 1000 milliseconds
                System.out.println(threadName + ": ------------------------------- DONE");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
	}
}