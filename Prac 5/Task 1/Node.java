import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@SuppressWarnings({ "rawtypes" })
public class Node<T> {
    public T item;
    public int key;
    public Node next;
    public int person;
    String name;
    public long startTime, time;
    String tName;
    public Lock lock = new ReentrantLock();

    public static final String RESET = "\033[0m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";

    public Node(T item, int person, long time) {
        this.item = item;
        this.key = item.hashCode();
        this.next = null;
        this.person = person;
        this.startTime = System.currentTimeMillis();
        this.time = time;
        this.tName = Thread.currentThread().getName();
        name = YELLOW + Thread.currentThread().getName() + RESET + " (P-" + BLUE
                + person + RESET + ")";
    }

    public String getName() {
        name = YELLOW + Thread.currentThread().getName() +":"+ RESET + " ("+ BLUE +"P-" +
                + person + RESET +", "+ (time - (System.currentTimeMillis() - startTime)) + "ms)";
        return name;
    }
}