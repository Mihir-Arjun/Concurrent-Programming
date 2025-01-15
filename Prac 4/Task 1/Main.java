public class Main {
    public static void main(String args[]){
        Printer p = new Printer(new MCSQueue());
        int size = 5;
        Node[] node = new Node[size];

        for (int i = 0; i < size; i++)
            node[i] = new Node(p);

        for (Node n : node)
            n.start();
    }
}
