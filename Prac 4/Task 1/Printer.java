import java.util.concurrent.locks.Lock;

public class Printer {
    Lock l;

	public Printer(Lock l) {
		this.l = l;
	}

	public void Print(int number){
		System.out.println("[" + Thread.currentThread().getName() + "] [Request " + number
				+ "] printing request");
		try {
			l.lock();

			try {
				Thread.sleep((int) Math.floor(Math.random() * (1000 + 1 - 200 + 1) + 200));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(
					"["+ Thread.currentThread().getName() + "] [Request " + number + "] printing [random-message]");
		} finally {
			l.unlock();
		}
	}
}
