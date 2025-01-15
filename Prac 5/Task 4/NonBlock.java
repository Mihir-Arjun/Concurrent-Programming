import java.util.concurrent.atomic.AtomicReference;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class NonBlock<T> {
    private Node<T> head;

    public static final String RESET = "\033[0m";
    public static final String BLACK = "\033[0;30m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";
    public static final String WHITE = "\033[0;37m";

    public NonBlock() {
        head = new Node(Integer.MIN_VALUE, -1, 0);
        head.next = new AtomicReference<>(new Node(Integer.MAX_VALUE, -1, 0));
    }

    public boolean add(T item, int person, long time) {
        int key = item.hashCode();
        while (true) {
            Node pred = head;
            Node curr = head.next.get();
            while (curr.key < key) {
                pred = curr;
                curr = (Node) curr.next.get();
            }
            Node node = new Node(item, person, time);
            node.next = new AtomicReference<>(curr);
            if (pred.next.compareAndSet(curr, node)) {
                System.out.println(GREEN + "ADD "+ RESET+ node.getName());
                return true;
            }
        }
    }

    public boolean remove(T item) {
        int key = item.hashCode();
        printList();
        while (true) {
            Node pred = head;
            Node curr = (Node) pred.next.get();
            while (curr.key < key) {
                pred = curr;
                curr = (Node) curr.next.get();
            }
            Node succ = (Node) curr.next.get();
            if (!curr.marked) {
                curr.marked = true;
                if (pred.next.compareAndSet(curr, succ)) {
                    return true;
                }
            }
        }
    }

    private void printList() {
        Node curr = head.next.get();
        String out = RED + "List: " + RESET;
        while (curr != null) {
            if (curr.person != -1)
                out += "[" + YELLOW + curr.tName + RESET + " (" + BLUE + "P-" +
                        +curr.person + RESET + ", " + ((curr.time - (System.currentTimeMillis() - curr.startTime) > 0)
                                ? (curr.time - (System.currentTimeMillis() - curr.startTime))
                                : 0)
                        + "ms)"
                        + "]";
            curr = (Node)curr.next.get();
        }
        System.out.println(out);
    }
    

}