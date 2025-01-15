import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

@SuppressWarnings({ "rawtypes", "unchecked" })

public class MCSQueue implements Lock {
    AtomicReference tail;

    public MCSQueue() {
        tail = new AtomicReference(null);
    }

    @Override
    public void lock() {
        MCSNode pred = (MCSNode) tail.getAndSet(((Node) Thread.currentThread()).node);
        ((Node) Thread.currentThread()).node.prev = pred;
        if (pred != null) {
            ((Node) Thread.currentThread()).node.locked = true;
            pred.next = ((Node) Thread.currentThread()).node;
            while (((Node) Thread.currentThread()).node.locked) {
            }
        }
    }

    @Override
    public void unlock() {
        String out = "";
        out += "QUEUE: ";
        MCSNode temp = ((Node) Thread.currentThread()).node;
        out += "{" + temp.name + "}";
        temp = temp.next;
        while (temp != null) {
            out += "->{" + temp.name + "}";
            temp = temp.next;
        }
        System.out.println(out);

        if (((Node) Thread.currentThread()).node.next == null) {
            if (tail.compareAndSet(((Node) Thread.currentThread()).node, null)) {
                return;
            }
            while (((Node) Thread.currentThread()).node.next == null) {
            }
        }
        ((Node) Thread.currentThread()).node.next.locked = false;
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }

}
