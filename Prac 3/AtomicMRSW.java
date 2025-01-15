public class AtomicMRSW<T> implements Register<T> {
    private StampedValue<AtomicSRSW<T>>[][] a_table; // each entry is SRSW atomic
    private ThreadLocal<Long> lastStamp;
    private final int readers;

    public AtomicMRSW(T init, int readers) {
        this.readers = readers;
        lastStamp = ThreadLocal.withInitial(() -> 0L);
        a_table = new StampedValue[readers][readers];
        StampedValue<AtomicSRSW<T>> value = new StampedValue<>(new AtomicSRSW<>(init));
        
        for (int i = 0; i < readers; i++) {
            for (int j = 0; j < readers; j++) {
                a_table[i][j] = value;
            }
        }
    }

    public T read() {
        int me = getThreadID();
        StampedValue<AtomicSRSW<T>> value = a_table[me][me];

        synchronized (a_table) {
            for (int i = 0; i < a_table.length; i++) {
                value = value.max(value, a_table[i][me]);
            }

            for (int i = 0; i < a_table.length; i++) {
                if (i == me) continue;
                a_table[me][i] = value;
            }
        }

        return value.value.read();
    }

    public void write(T v) {
        long stamp = lastStamp.get() + 1;
        lastStamp.set(stamp);
        StampedValue<AtomicSRSW<T>> value = new StampedValue<>(new AtomicSRSW<>(v));

        synchronized (a_table) {
            for (int i = 0; i < a_table.length; i++) {
                a_table[i][i] = value;
            }
        }
    }
    
    private int getThreadID() {
        return (int) Thread.currentThread().getId() % readers;
    }
}
