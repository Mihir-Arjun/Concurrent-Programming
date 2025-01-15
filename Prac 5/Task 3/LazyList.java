@SuppressWarnings({ "unchecked", "rawtypes" })
public class LazyList<T> {
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

    public LazyList() {
        head = new Node(Integer.MIN_VALUE, -1, 0);
        head.next = new Node(Integer.MAX_VALUE, -1, 0);

    }

    public boolean add(T item, int person, long time) {
        int key = item.hashCode();
        while (true) {
            Node pred = head;
            Node curr = head.next;
            while (curr.key < key) {
                pred = curr;
                curr = curr.next;
            }
            pred.lock.lock();
            try {
                curr.lock.lock();
                try {
                    if (validate(pred, curr)) {
                        if (curr.key == key) {
                            return false;
                        } else {
                            Node node = new Node(item, person, time);
                            node.next = curr;
                            pred.next = node;
                            System.out.println(GREEN + "ADD "+ RESET+ node.getName());
                            return true;
                        }
                    }
                } finally {
                    curr.lock.unlock();
                }
            } finally {
                pred.lock.unlock();
            }
        }
    }

    public boolean remove(T item) {
        int key = item.hashCode();
        printList();
        while (true) {
            Node pred = head;
            Node curr = head.next;
            while (curr.key < key) {
                pred = curr;
                curr = curr.next;
            }
            pred.lock.lock();
            try {
                curr.lock.lock();
                try {
                    if (validate(pred, curr)) {
                        if (curr.key != key) {
                            return false;
                        } else {
                            curr.marked = true;
                            pred.next = curr.next;
                            return true;
                        }
                    }
                } finally {
                    curr.lock.unlock();
                }
            } finally {
                pred.lock.unlock();
            }
        }
    }

    private boolean validate(Node pred, Node curr) {
        return !pred.marked && !curr.marked && pred.next == curr;
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