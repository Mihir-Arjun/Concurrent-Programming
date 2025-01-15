public class PetersonLock{
    private boolean[] flag = new boolean[2];
    private int victim;

    public void lock(int i) {
        int j = 1 - i;
        flag[i] = true; 
        victim = i; 
        while (flag[j] && victim == i) {
        }
        ; // wait
    }

    public void unlock(int i) {
        flag[i] = false; 
    }
}