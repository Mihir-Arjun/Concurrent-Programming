public class MCSNode {
    boolean locked = false;
    MCSNode next = null;
    MCSNode prev = null;
    String name;
    String status = "WAITING";

    MCSNode(int i) {
        name = Thread.currentThread().getName() + ":Request Number " + i;
    }
    MCSNode() {
        name = "AVAILABLE";
    }
}