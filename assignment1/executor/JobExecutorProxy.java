package edu.najah.cap.advance.assignments.assignment1.executor;

import edu.najah.cap.advance.assignments.assignment1.connections.Connection;
import edu.najah.cap.advance.assignments.assignment1.connections.ConnectionPool;
import edu.najah.cap.advance.assignments.assignment1.job.Job;
import edu.najah.cap.advance.assignments.assignment1.model.User;

import java.util.List;

/**
 * Proxy for controlled job execution.
 * Implements JobExecutorInterface.
 * Adds security (permissions), logging, timing, and connection management.
 */
public class JobExecutorProxy implements JobExecutorInterface {
    private final JobExecutor realExecutor;
    private final ConnectionPool connectionPool;

    public JobExecutorProxy(JobExecutor realExecutor, ConnectionPool connectionPool) {
        this.realExecutor = realExecutor;
        this.connectionPool = connectionPool;
    }

    @Override
    public void executeJob(Job job) {
        long startTime = System.currentTimeMillis();

        // Validate permissions
        if (!hasPermission(job)) {
            System.out.println("[Proxy] Permission denied for job: " + job.getName());
            return;
        }

        // Acquire connection from pool
        Connection connection = connectionPool.acquire();
        if (connection == null) {
            System.out.println("[Proxy] No available connections for job: " + job.getName());
            return;
        }

        try {
            // Log start
            System.out.printf("[Proxy] Starting job %s (%s) requested by %s%n",
                    job.getName(), job.getType(),
                    job.getRequestedBy() == null ? "unknown" : job.getRequestedBy().getName());

            // Delegate to real executor
            realExecutor.executeJob(job, connection);

            // Log completion with timing
            long duration = System.currentTimeMillis() - startTime;
            System.out.printf("[Proxy] Finished job %s in %d ms%n", job.getName(), duration);

        } catch (Exception e) {
            System.out.println("[Proxy] Error executing job: " + e.getMessage());
        } finally {
            // Release connection back to pool
            connectionPool.release(connection);
        }
    }

    private boolean hasPermission(Job job) {
        User user = job.getRequestedBy();
        if (user == null) return false;

        List<String> permissions = user.getPermissions();
        return permissions.contains(job.getType());
    }
}
