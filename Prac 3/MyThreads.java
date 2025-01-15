import java.util.Random;

public class MyThreads extends Thread {
    AtomicMRMW<Integer> register;
    boolean writer ;

	public MyThreads(AtomicMRMW<Integer> register_ , boolean writer ){
        this.register =register_ ;
        this.writer = writer ;
	}

	@Override
	public void run()
	{
		if (writer) {
            Random random = new Random();
            int value = random.nextInt(100); // Generate a random value
            register.write(value);
            System.out.println("(writer) [" + getName() + "] : " +"["+ value+"]");
        } else {
            Integer value = register.read();
            System.out.println("(reader) [" + getName() + "] : " +"["+ value+"]");
        }   
	}
}
