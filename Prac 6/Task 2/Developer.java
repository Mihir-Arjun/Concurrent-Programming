import java.util.Random;

public class Developer implements Runnable {
    private final String name;
    private final LockFreeStack<JobRequest> centralDatabase;

    public Developer(String name, LockFreeStack<JobRequest> centralDatabase) {
        this.name = name;
        this.centralDatabase = centralDatabase;
    }

    @Override
    public void run() {
        Random random = new Random();
        for (int i = 1; i <= 3; i++) {
            int hours = random.nextInt(24) + 1;
            JobRequest jobRequest = new JobRequest(name + "-Job" + i, hours);
            centralDatabase.push(jobRequest);
            System.out.println("(IN) " + name + " " + jobRequest.getJobNumber() + " " + jobRequest.getHours());
        }
        try {
            Thread.sleep(1000); // Sleep for 1 second (adjust as needed)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}