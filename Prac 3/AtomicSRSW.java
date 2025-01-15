public class AtomicSRSW<T> implements Register<T> {
    StampedValue<T> r_value; // regular SRSW timestamp-value pair
    ThreadLocal<Long> lastStamp;
    ThreadLocal<StampedValue<T>> lastRead;

    public AtomicSRSW(T init) {
        r_value = new StampedValue<>(init);
        lastStamp = ThreadLocal.withInitial(() -> 0L);
        lastRead = ThreadLocal.withInitial(() -> r_value);
    }

    public T read() {
        StampedValue<T> value = r_value;
        StampedValue<T> last = lastRead.get();
        // Call the max method on the instance of StampedValue
        StampedValue<T> result = value.max(value, last);
        
        lastRead.set(result);
        return result.value;
    }

    public void write(T v) {
        long stamp = lastStamp.get() + 1;
        r_value = new StampedValue<>(stamp, v);
        lastStamp.set(stamp);
    }
}
