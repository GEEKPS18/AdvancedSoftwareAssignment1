import edu.najah.cap.advance.assignments.assignment1.job.Job;

/**
 * Simple prototype interface for cloning job templates.
 */
public interface JobPrototype {
    JobPrototype clone();
    Job createJobInstance();
}
