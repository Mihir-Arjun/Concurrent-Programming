import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Bakery implements Lock
{
	int n;
    private volatile Boolean[] flag;
	private volatile Integer[] label;

	public void lockInterruptibly() throws InterruptedException
	{
		throw new UnsupportedOperationException();
	}

	public boolean tryLock()
	{
		throw new UnsupportedOperationException();
	}

	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException
	{
		throw new UnsupportedOperationException();
	}

	public Condition newCondition()
	{
		throw new UnsupportedOperationException();
	}

	public Bakery(int n) {
        this.n = n;
        flag = new Boolean[n];
        label = new Integer[n];
        for (int i = 0; i < n; i++) {
            flag[i] = false;
            label[i] = 0;
        }
    }

	@Override
	public void lock() {
		int me = (int) Thread.currentThread().getId() % n;
        flag[me] = true;
        label[me] = findMaxLabel() + 1;

        for (int i = 0; i < n; i++) {
            while (i != me && (flag[i] && (label[i] < label[me] || (label[i] == label[me] && i < me)))) {}
        }
	}

	@Override
	public void unlock() {
        int me = (int) Thread.currentThread().getId() % n;
        flag[me] = false;
    }

	public int findMaxLabel() {
        int maxLabel = label[0];
        for (int i = 1; i < n; i++) {
            if (label[i] > maxLabel) {
                maxLabel = label[i];
            }
        }
        return maxLabel;
    }
}
