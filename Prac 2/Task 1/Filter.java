import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Filter implements Lock {
	private volatile int[] level;
	private volatile int[] victim;
	private int n;

	public Filter(int n) {
		this.n = n;
        level = new int[n];
        victim = new int[n];
        for (int i = 0; i < n; i++) {
            level[i] = 0;
        }
	}

	public void lock() {
		int me = (int) Thread.currentThread().getId() % n;
        for (int i = 1; i < n; i++) {
            level[me] = i;
            victim[i] = me;
            boolean wait = true;
            while (wait) {
                wait = false;
                for (int k = 0; k < n; k++) {
                    if (k != me && level[k] >= i && victim[i] == me) {
                        wait = true;
                        break;
                    }
                }
            }
        }
	}

	public void unlock() {
		int me = (int) Thread.currentThread().getId() % n;
        level[me] = 0;
	}

	public void lockInterruptibly() throws InterruptedException {
		throw new UnsupportedOperationException();
	}

	public boolean tryLock() {
		throw new UnsupportedOperationException();
	}

	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		throw new UnsupportedOperationException();
	}

	public Condition newCondition() {
		throw new UnsupportedOperationException();
	}
}
