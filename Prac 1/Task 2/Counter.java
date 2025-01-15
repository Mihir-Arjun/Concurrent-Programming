public class Counter {
    private int value;
    private PetersonLock lock;

    public Counter(int c, PetersonLock lock) {
        value = c;
        this.lock = lock;
    }

    public int getValue() {
        return value;
    }

    public int getAndIncrement(int i) {
        int temp;
        lock.lock(i);
        try {
            temp = value;
            value = value + 1;
        } finally {
            lock.unlock(i);
        }
        return temp;
    }
}
