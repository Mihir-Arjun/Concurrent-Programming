public class Main {
    public static void main(String[] args) {
        PetersonLock lock = new PetersonLock();

        int numThreads = 2;
        Thread[] threads = new Thread[numThreads];

        Counter counter = new Counter(0, lock); // Initialize the counter with 0 and the lock
        int range = 100;

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Task2(counter, range, lock, i); // Pass the counter, array, lock and index to the Task2                                        // instance
            threads[i].setName("Thread " + (i));
        }

        for (Thread t : threads) { // Start running threads
            t.start();
        }

        for (Thread t : threads) { // Wait for all running threads to finish
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
