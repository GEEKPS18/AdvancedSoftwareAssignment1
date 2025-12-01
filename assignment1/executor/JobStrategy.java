package edu.najah.cap.advance.assignments.assignment1.executor;

import edu.najah.cap.advance.assignments.assignment1.connections.Connection;
import edu.najah.cap.advance.assignments.assignment1.job.Job;

/**
 * واجهة JobStrategy لنمط الاستراتيجية.
 * تحدد طريقة تنفيذ الوظائف.
 * هذا يلبي جزء من المتطلب 3: نمط الاستراتيجية.
 */
public interface JobStrategy {
    /**
     * ينفذ الوظيفة باستخدام الاتصال المقدم.
     * @param job الوظيفة المراد تنفيذها
     * @param connection الاتصال المستخدم
     */
    void execute(Job job, Connection connection);
}
