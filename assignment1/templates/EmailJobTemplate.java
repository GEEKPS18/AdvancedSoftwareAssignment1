package edu.najah.cap.advance.assignments.assignment1.templates;

import edu.najah.cap.advance.assignments.assignment1.job.Job;

/**
 * Concrete prototype for Email job templates.
 * Implements JobPrototype for cloning.
 */
public class EmailJobTemplate implements JobPrototype {
    private String name;
    private String config;
    private String templateBody;

    public EmailJobTemplate(String name, String config, String templateBody) {
        this.name = name;
        this.config = config;
        this.templateBody = templateBody;
    }

    @Override
    public JobPrototype clone() {
        // Shallow clone for immutable fields
        return new EmailJobTemplate(this.name, this.config, this.templateBody);
    }

    @Override
    public Job createJobInstance() {
        String id = templateBody + " _ EMAIL-" + System.currentTimeMillis();
        return new Job(id, "EMAIL", name, config);
    }

    // Getters for registry
    public String getName() { return name; }
    public String getConfig() { return config; }
    public String getTemplateBody() { return templateBody; }
}
