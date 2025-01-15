import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class LockFreeStack<T> {
    AtomicReference<Node<T>> top = new AtomicReference<>(null);
    static final int MIN_DELAY = 100;
    static final int MAX_DELAY = 1000; 
    Backoff backoff = new Backoff(MIN_DELAY, MAX_DELAY);

    protected boolean tryPush(Node<T> node) {
        Node<T> oldTop = top.get();
        node.next = oldTop;
        return (top.compareAndSet(oldTop, node));
    }

    public void push(T value) {
        Node<T> node = new Node<>(value);
        while (true) {
            if (tryPush(node)) {
                return;
            } else {
                backoff.backoff();
            }
        }
    }

    protected Node<T> tryPop() throws EmptyException {
        Node<T> oldTop = top.get();
        if (oldTop == null) {
            throw new EmptyException();
        }
        Node<T> newTop = oldTop.next;
        if (top.compareAndSet(oldTop, newTop)) {
            return oldTop;
        } else {
            return null;
        }
    }

    public T pop() throws EmptyException {
        while (true) {
            Node<T> returnNode = tryPop();
            if (returnNode != null) {
                return returnNode.value;
            } else {
                backoff.backoff();
            }
        }
    }

    private static class Node<T> {
        public T value;
        public Node<T> next;

        public Node(T value) {
            this.value = value;
            this.next = null;
        }
    }

    private static class Backoff {
        final int minDelay, maxDelay;
        int limit;
        final Random random;

        public Backoff(int min, int max) {
            minDelay = min;
            maxDelay = max;
            limit = minDelay;
            random = new Random();
        }

        public void backoff() {
            int delay = random.nextInt(limit);
            limit = Math.min(maxDelay, 2 * limit);
            try { Thread.sleep(delay); } catch (InterruptedException e) {}
        }
    }

    public static class EmptyException extends Exception {}
}
