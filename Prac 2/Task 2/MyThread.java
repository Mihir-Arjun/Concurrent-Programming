public class MyThread extends Thread {
    SharedResources criticalSection;

	public MyThread(SharedResources CrSection ){
		criticalSection = CrSection;
	}

	@Override
	public void run(){
        criticalSection.access();
	}
}
