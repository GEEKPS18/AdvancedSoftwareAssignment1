package edu.najah.cap.advance.assignments.assignment1.templates;

import edu.najah.cap.advance.assignments.assignment1.job.Job;

/**
 * Concrete prototype for Report job templates.
 * Implements JobPrototype for cloning.
 */
public class ReportJobTemplate implements JobPrototype {
    private String name;
    private String config;
    private String templateBody;

    public ReportJobTemplate(String name, String config, String templateBody) {
        this.name = name;
        this.config = config;
        this.templateBody = templateBody;
    }

    @Override
    public JobPrototype clone() {
        // Shallow clone for immutable fields
        return new ReportJobTemplate(this.name, this.config, this.templateBody);
    }

    @Override
    public Job createJobInstance() {
        String id = templateBody + " _ REPORT-" + System.currentTimeMillis();
        return new Job(id, "REPORT", name, config);
    }

    // Getters for registry
    public String getName() { return name; }
    public String getConfig() { return config; }
    public String getTemplateBody() { return templateBody; }
}
