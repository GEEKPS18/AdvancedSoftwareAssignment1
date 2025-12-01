package edu.najah.cap.advance.assignments.assignment1.executor;

import java.util.HashMap;
import java.util.Map;

/**
 * JobStrategyFactory maps job types to their corresponding strategies.
 * Removes type-checking logic from JobExecutor.
 * This fulfills part of Requirement 3: Strategy Pattern.
 */
public class JobStrategyFactory {
    private final Map<String, JobStrategy> strategies = new HashMap<>();

    public JobStrategyFactory() {
        // Register strategies
        strategies.put("EMAIL", new EmailJobStrategy());
        strategies.put("DATA", new DataProcessingStrategy());
        strategies.put("REPORT", new ReportGenerationStrategy());
    }

    /**
     * Gets the strategy for a given job type.
     * @param jobType the job type (e.g., "EMAIL", "DATA", "REPORT")
     * @return the corresponding JobStrategy, or null if not found
     */
    public JobStrategy getStrategy(String jobType) {
        return strategies.get(jobType);
    }
}
