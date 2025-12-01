import edu.najah.cap.advance.assignments.assignment1.connections.Connection;
import edu.najah.cap.advance.assignments.assignment1.job.Job;

/**
 * Strategy for executing Data Processing jobs.
 * Implements JobStrategy.
 */
public class DataProcessingStrategy implements JobStrategy {
    @Override
    public void execute(Job job, Connection connection) {
        System.out.println("[DataProcessingStrategy] Reading & transforming data using config: " + job.getConfig());
        connection.executeQuery("SELECT * FROM source_table WHERE job_id = '" + job.getId() + "'");
        connection.executeQuery("INSERT INTO processed_results (job_id) VALUES ('" + job.getId() + "')");
    }
}
