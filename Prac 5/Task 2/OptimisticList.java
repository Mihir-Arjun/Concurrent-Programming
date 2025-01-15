@SuppressWarnings({ "unchecked", "rawtypes" })
public class OptimisticList<T> {
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

    public OptimisticList() {
        head = new Node(Integer.MIN_VALUE, -1, 0);
        head.next = new Node(Integer.MAX_VALUE, -1, 0);

    }

    public boolean add(T item, int person, long time) {
        Node pred = head, curr = pred.next;
        int key = item.hashCode();
        try {
            pred = head;
            pred.lock.lock();
            curr = pred.next;
            curr.lock.lock();
            while (curr.key < key) {
                pred.lock.unlock();
                pred = curr;
                curr = curr.next;
                curr.lock.lock();
            }
            if (key == curr.key)
                return false;
            else {
                Node node = new Node(item, person, time);
                node.next = curr;
                pred.next = node;
                System.out.println(GREEN + "ADD "+ RESET+ node.getName());
                return true;
            }
        } finally {
            pred.lock.unlock();
            curr.lock.unlock();
        }
    }

    public boolean remove(T item) {
        Node pred = head, curr = head.next;
        int key = item.hashCode();
        printList();
        try {
            pred = head;
            pred.lock.lock();
            curr = pred.next;
            curr.lock.lock();
            while (curr.key <= key) {
                if (item == curr.item) {
                    pred.next = curr.next;
                    return true;
                }
                pred.lock.unlock();
                pred = curr;
                curr = curr.next;
                curr.lock.lock();
            }
            return false;

        } finally {
            curr.lock.unlock();
            pred.lock.unlock();
        }
    }

    private void printList() {
        Node curr = head.next;
        String out = RED + "List: " + RESET;
        if (curr.person != -1)
            out += "[" + YELLOW + curr.tName + RESET + " (" + BLUE + "P-" +
                    +curr.person + RESET + ", " + ((curr.time - (System.currentTimeMillis() - curr.startTime) > 0)
                            ? (curr.time - (System.currentTimeMillis() - curr.startTime))
                            : 0)
                    + "ms)"
                    + "]";
        while (curr.next != null) {
            curr = curr.next;
            if (curr.person != -1)
                out += " -> [" + YELLOW + curr.tName + RESET + " (" + BLUE + "P-" +
                        +curr.person + RESET + ", "
                        + ((curr.time - (System.currentTimeMillis() - curr.startTime) > 0)
                                ? (curr.time - (System.currentTimeMillis() - curr.startTime))
                                : 0)
                        + "ms)" + "]";
        }
        System.out.println(out);
    }

}