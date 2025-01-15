import java.util.concurrent.locks.Lock;

public class SharedResources {
    private final Filter lock = new Filter(THREAD_COUNT);

    private static final int THREAD_COUNT = 5;

    Lock l;

	public void access(){
		String threadName = Thread.currentThread().getName();
        int me = (int) Thread.currentThread().getId() % THREAD_COUNT;

        for (int i = 0; i < 2; i++) {
            lock.lock();
            try {
                // Critical section
                System.out.println(threadName + ": level[" + me + "] = " + i + " , victim[" + i + "] = " + me);
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