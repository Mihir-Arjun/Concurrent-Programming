public class Task1 extends Thread {
    private int[] array;
    private int upperBound;
    private int lowerBound;

    public Task1(int[] array, int lowerBound, int upperBound) {
        this.array=array;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public void run() {
        for (int i = lowerBound; i < upperBound; i++) {
            if (isPrime(array[i])) {
                System.out.println(this.getName() + " [" + lowerBound + "-" + upperBound + "]: " + array[i]);
            }
        }
    }

    public static boolean isPrime(int num) {
        if (num <= 1) {
            return false;
        }
        for (int i = 2; i*i <= num; i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
}