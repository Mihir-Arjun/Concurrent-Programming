 public class JobRequest {
        private final String jobNumber;
        private final int hours;

        public JobRequest(String jobNumber, int hours) {
            this.jobNumber = jobNumber;
            this.hours = hours;
        }

        public String getJobNumber() {
            return jobNumber;
        }

        public int getHours() {
            return hours;
        }
    }