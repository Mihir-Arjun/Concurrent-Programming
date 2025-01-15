public class Main {
    public static void main(String[] args) {
        int n = 1; // Starting range
        int m = 100; // Ending range

        // Initialize the array with integers from n to m.
        int size = m - n + 1;
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = n + i;
        }

        int numThreads = 5;
        Thread[] threads = new Task1[numThreads];
        int range = size / numThreads; //partitioning

        for (int i = 0; i < numThreads; i++) {
            int start = i * range;
            int end;
            if (i == numThreads - 1) {
                end = size;
            } else {
                end = (i + 1) * range;
            }
            threads[i] = new Task1(array, start, end);
        }

        for (Thread t : threads) { // Start running threads
            t.start();
        }

        for (Thread t : threads) { // Wait for all running threads to finish
            try {
                t.join();
            } catch (InterruptedException e) {
            }
        }

    }
}
