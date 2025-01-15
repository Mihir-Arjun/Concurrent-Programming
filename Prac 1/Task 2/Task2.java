public class Task2 extends Thread {
    private static Counter counter;
    private PetersonLock lock;
    private int index;
    int range;

    public Task2(Counter counter, int range, PetersonLock lock, int index) {
        this.counter = counter;
        this.lock = lock;
        this.index = index;
        this.range=range;
    }

    public void run() {
        while (counter.getValue() < range) {
            int n = counter.getAndIncrement(index);
            if (isPrime(n)) {
                System.out.println(Thread.currentThread().getName() + " [" + "Counter Value" + "-" + n + "]: " + n);
            }
        }
    }

    public static boolean isPrime(int num) {
        if (num <= 1) {
            return false;
        }
        for (int i = 2; i * i <= num; i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
}
