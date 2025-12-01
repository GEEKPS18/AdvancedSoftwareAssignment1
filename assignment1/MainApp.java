import java.util.Arrays;

// main app to show how the job system works
public class MainApp {
    public static void main(String[] args) {
        System.out.println("job system demo");

        // setup the new improved components
        ConnectionPool pool = new ConnectionPool(); // manages connections
        JobTemplateRegistry registry = new JobTemplateRegistry(); // stores templates
        registry.initializeDefaults(); // load templates once

        TemplateManager templateManager = new TemplateManager(registry); // uses registry
        JobExecutor realExecutor = new JobExecutor(); // does the work
        JobExecutorProxy executor = new JobExecutorProxy(realExecutor, pool); // controls access

        User alice = new User("alice", Arrays.asList("EMAIL", "REPORT", "DATA")); // user with permissions

        // demo: create and run different jobs
        System.out.println("\ncreating report job");
        JobPrototype reportTemplate = templateManager.buildReportJobTemplate("monthly report", "format=pdf");
        Job reportJob = reportTemplate.createJobInstance();
        reportJob.setRequestedBy(alice);

        System.out.println("running report job");
        executor.executeJob(reportJob);

        System.out.println("\ncreating email job");
        JobPrototype emailTemplate = templateManager.buildEmailJobTemplate("monthly email", "format=html");
        Job emailJob = emailTemplate.createJobInstance();
        emailJob.setRequestedBy(alice);

        System.out.println("running email job");
        executor.executeJob(emailJob);

        System.out.println("\ncreating data job");
        JobPrototype dataTemplate = templateManager.buildDataProcessingTemplate("clean data", "source=db");
        Job dataJob = dataTemplate.createJobInstance();
        dataJob.setRequestedBy(alice);

        System.out.println("running data job");
        executor.executeJob(dataJob);
    }
}
