public class MyThread extends Thread {
    SharedResources criticalSection;
	volatile Filter f;


	public MyThread(SharedResources CrSection ){
		criticalSection = CrSection;
	}

	@Override
	public void run(){
        criticalSection.access();
	}
}
