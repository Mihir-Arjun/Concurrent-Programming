import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

@SuppressWarnings({ "rawtypes", "unchecked" })

public class Timeout implements Lock {
    static MCSNode AVAILABLE = new MCSNode();
    AtomicReference tail;

    public Timeout() {
        tail = new AtomicReference(null);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        ((Node) Thread.currentThread()).node.prev = null;
        MCSNode myPred = (MCSNode) tail.getAndSet(((Node) Thread.currentThread()).node);
        if (myPred == null || myPred.prev == AVAILABLE) {
            ((Node) Thread.currentThread()).node.status = "WAITING";
            return true;
        }
        myPred.next = ((Node) Thread.currentThread()).node;

        long startTime = System.nanoTime();
        while (System.nanoTime() - startTime < unit.toNanos(time)) {
            System.out.print("");
            MCSNode predPred = myPred.prev;
            if (predPred == AVAILABLE) {
                ((Node) Thread.currentThread()).node.status = "WAITING";
                return true;
            } else if (predPred != null)
                myPred = predPred;
        }
        if (!tail.compareAndSet(((Node) Thread.currentThread()).node, myPred))
            ((Node) Thread.currentThread()).node.prev = myPred;
        System.out.println(((Node) Thread.currentThread()).node.name + " timed out");
        ((Node) Thread.currentThread()).node.status = "TIMED OUT";
        return false;
    }

    @Override
    public void unlock() {
        String out = "";
        out += "QUEUE: ";
        MCSNode temp = ((Node) Thread.currentThread()).node;
        out += "{" + temp.name + "}";
        temp = temp.next;
        while (temp != null && temp.status != "TIMED OUT") {
            out += "->{" + temp.name + "}";
            temp = temp.next;
        }

        MCSNode nexts = null;
        if (temp != null && temp.status == "TIMED OUT") {
            nexts = temp.next;
        }

        while (nexts != null && nexts.status != "TIMED OUT") {
            out += "->{" + nexts.name + "}";
            nexts = nexts.next;
        }
        System.out.println(out);

        if (!tail.compareAndSet(((Node) Thread.currentThread()).node, null))
            ((Node) Thread.currentThread()).node.prev = AVAILABLE;
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
    }

    @Override
    public void lock() {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }

}
