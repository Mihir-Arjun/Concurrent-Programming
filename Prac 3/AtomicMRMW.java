public class AtomicMRMW<T> implements Register<T> {
    private StampedValue<AtomicMRSW<T>>[] a_table; // array of atomic MRSW registers
    private final int capacity;

    @SuppressWarnings("unchecked")
    public AtomicMRMW(int capacity, T init) {
        this.capacity = capacity;
        a_table = new StampedValue[capacity];
        StampedValue<AtomicMRSW<T>> value = new StampedValue<>(new AtomicMRSW<>(init, capacity));
        for (int j = 0; j < a_table.length; j++) {
            a_table[j] = value;
        }
    }

    public void write(T value) {
        int me = getThreadID();
        StampedValue<AtomicMRSW<T>> max = new StampedValue<>(null);
        
        synchronized (a_table) {
            for (int i = 0; i < a_table.length; i++) {
                max = max.max(max, a_table[i]);
            }
            a_table[me] = new StampedValue<>(new AtomicMRSW<>(value, capacity));
        }
    }

    public T read() {
        StampedValue<AtomicMRSW<T>> max = new StampedValue<>(null);
        
        synchronized (a_table) {
            for (int i = 0; i < a_table.length; i++) {
                max = max.max(max, a_table[i]);
            }
        }
        
        return max.value.read();
    }

    private int getThreadID() {
        return (int) Thread.currentThread().getId() % capacity;
    }
}