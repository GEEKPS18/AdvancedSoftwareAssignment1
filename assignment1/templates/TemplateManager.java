package edu.najah.cap.advance.assignments.assignment1.templates;

/*
 * Refactored TemplateManager: uses JobTemplateRegistry for efficient cloning instead of building from scratch.
 * This fulfills Requirement 2: Prototype Pattern.
 */
public class TemplateManager {
    private final JobTemplateRegistry registry;

    public TemplateManager(JobTemplateRegistry registry) {
        this.registry = registry;
        // Initialize registry with defaults if not already done
        if (!registry.hasPrototype("EMAIL_DEFAULT")) {
            registry.initializeDefaults();
        }
    }

    public JobPrototype buildEmailJobTemplate(String templateName, String config) {
        // Clone from registry instead of building from scratch
        JobPrototype prototype = registry.getPrototype("EMAIL_DEFAULT");
        if (prototype instanceof EmailJobTemplate) {
            EmailJobTemplate cloned = (EmailJobTemplate) prototype;
            // Customize if needed, but for simplicity, return cloned
            System.out.println("Cloned Email template: " + templateName);
            return cloned;
        }
        // Fallback to old way if not found (should not happen)
        return createFallbackEmailTemplate(templateName, config);
    }

    public JobPrototype buildDataProcessingTemplate(String templateName, String config) {
        JobPrototype prototype = registry.getPrototype("DATA_DEFAULT");
        if (prototype instanceof DataProcessingJobTemplate) {
            DataProcessingJobTemplate cloned = (DataProcessingJobTemplate) prototype;
            System.out.println("Cloned DataProcessing template: " + templateName);
            return cloned;
        }
        return createFallbackDataTemplate(templateName, config);
    }

    public JobPrototype buildReportJobTemplate(String templateName, String config) {
        JobPrototype prototype = registry.getPrototype("REPORT_DEFAULT");
        if (prototype instanceof ReportJobTemplate) {
            ReportJobTemplate cloned = (ReportJobTemplate) prototype;
            System.out.println("Cloned Report template: " + templateName);
            return cloned;
        }
        return createFallbackReportTemplate(templateName, config);
    }

    // Fallback methods for if registry not initialized (should not be used)
    private JobPrototype createFallbackEmailTemplate(String templateName, String config) {
        String templateBody = simulateHeavyLoad("EmailTemplate:" + templateName);
        return new EmailJobTemplate(templateName, config, templateBody);
    }

    private JobPrototype createFallbackDataTemplate(String templateName, String config) {
        String templateBody = simulateHeavyLoad("DataTemplate:" + templateName);
        return new DataProcessingJobTemplate(templateName, config, templateBody);
    }

    private JobPrototype createFallbackReportTemplate(String templateName, String config) {
        String templateBody = simulateHeavyLoad("ReportTemplate:" + templateName);
        return new ReportJobTemplate(templateName, config, templateBody);
    }

    private String simulateHeavyLoad(String msg) {
        System.out.println("Simulating heavy template creation for: " + msg + " (fallback)");
        try { Thread.sleep(3000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        return "Large template";
    }
}
