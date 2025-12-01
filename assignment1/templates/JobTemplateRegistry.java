package edu.najah.cap.advance.assignments.assignment1.templates;

import java.util.HashMap;
import java.util.Map;

/**
 * JobTemplateRegistry stores reusable prototypes for job templates.
 * Allows efficient cloning instead of rebuilding from scratch.
 * This fulfills part of Requirement 2: Prototype Pattern.
 */
public class JobTemplateRegistry {
    private final Map<String, JobPrototype> registry = new HashMap<>();

    /**
     * Adds a prototype to the registry.
     * @param key the key to store the prototype under
     * @param prototype the prototype to store
     */
    public void addPrototype(String key, JobPrototype prototype) {
        registry.put(key, prototype);
    }

    /**
     * Retrieves and clones a prototype from the registry.
     * @param key the key of the prototype
     * @return a cloned JobPrototype, or null if not found
     */
    public JobPrototype getPrototype(String key) {
        JobPrototype prototype = registry.get(key);
        return prototype != null ? prototype.clone() : null;
    }

    /**
     * Checks if a prototype exists in the registry.
     * @param key the key to check
     * @return true if exists, false otherwise
     */
    public boolean hasPrototype(String key) {
        return registry.containsKey(key);
    }

    /**
     * Initializes the registry with default prototypes.
     * Simulates the heavy load once, then clones.
     */
    public void initializeDefaults() {
        // Simulate heavy load for Email template
        String emailBody = simulateHeavyLoad("EmailTemplate:DefaultEmail");
        addPrototype("EMAIL_DEFAULT", new EmailJobTemplate("Default Email", "format=HTML;priority=normal", emailBody));

        // Simulate heavy load for Data template
        String dataBody = simulateHeavyLoad("DataTemplate:DefaultData");
        addPrototype("DATA_DEFAULT", new DataProcessingJobTemplate("Default Data Processing", "source=db;transform=clean", dataBody));

        // Simulate heavy load for Report template
        String reportBody = simulateHeavyLoad("ReportTemplate:DefaultReport");
        addPrototype("REPORT_DEFAULT", new ReportJobTemplate("Default Report", "format=PDF;brand=TaskMaster", reportBody));
    }

    private String simulateHeavyLoad(String msg) {
        System.out.println("Simulating heavy template creation for: " + msg + " (only once for registry)");
        try { Thread.sleep(3000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        return "Large template for " + msg;
    }
}
