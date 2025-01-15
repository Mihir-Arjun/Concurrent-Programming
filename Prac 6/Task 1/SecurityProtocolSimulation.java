public class SecurityProtocolSimulation {
    public static void main(String[] args) {
        LockFreeQueue<JobRequest> centralDatabase = new LockFreeQueue<>();

        for (int i = 1; i <= 2; i++) {
            Thread developerThread = new Thread(new Developer("Developer-" + i, centralDatabase));
            developerThread.start();
        }

        for (int i = 1; i <= 4; i++) {
            Thread sysAdminThread = new Thread(new SystemAdministrator("SysAdmin-" + i, centralDatabase));
            sysAdminThread.start();
        }
    }
}
