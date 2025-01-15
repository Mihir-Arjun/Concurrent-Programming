public class Node extends Thread {
	private volatile Printer p;
	public volatile MCSNode node = null;

	Node(Printer _p)
	{
		this.p = _p ;
	}

	@Override
	public void run()
	{
		for (int i = 1; i <= 5; i++) {
			node = new MCSNode(i);
			p.Print(i);
		}
	}
}
