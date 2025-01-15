import java.util.concurrent.locks.Lock;

public class Printer {
	Lock l;

	public Printer(Lock l) {
		this.l = l;
	}

	public void Print(int num) {
		System.out.println("[" + Thread.currentThread().getName() + "] [Request " + num
				+ "] printing request");
		try {
			while (!l.tryLock((int) Math.floor(Math.random() * (5000 + 1 - 2000 + 1) + 2000),
					java.util.concurrent.TimeUnit.MILLISECONDS)) {
			}
			Thread.sleep((int) Math.floor(Math.random() * (1000 + 1 - 200 + 1) + 200));

			System.out.println(
					 "[" + Thread.currentThread().getName() + "] [Request " + num
							+ "] printing [random-message]");
		} catch (Exception e) {
		} finally {
			l.unlock();
		}
	}
}
