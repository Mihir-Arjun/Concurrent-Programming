import java.util.Random;

public class SystemAdministrator implements Runnable {
    private final String name;
    private final LockFreeQueue<JobRequest> centralDatabase;

    public SystemAdministrator(String name, LockFreeQueue<JobRequest> centralDatabase) {
        this.name = name;
        this.centralDatabase = centralDatabase;
    }

    @Override
    public void run() {
        Random random = new Random();
        int randomAcceptanceHours = random.nextInt(24) + 1;
        while (true) {
            try {
                JobRequest jobRequest = centralDatabase.deq();

                // Check for "end of stream" job request
                if ("End".equals(jobRequest.getJobNumber()) && jobRequest.getHours() == -1) {
                    break;
                }

                // Process the job request
                int hours = jobRequest.getHours();
                String approvalStatus = hours <= randomAcceptanceHours ? "Approved" : "Disapproved";
                System.out.println("(OUT) " + name + " " + jobRequest.getJobNumber() + " " + hours + " " + approvalStatus);
                
            Thread.sleep((int) Math.floor(Math.random() * (1000 + 1 - 200 + 1) + 200));
            } catch (LockFreeQueue.EmptyException e) {
                // Central database is empty, continue checking.
            } catch (InterruptedException e) {
                // Thread was interrupted while sleeping, handle interruption.
            }
        }
    }
}
