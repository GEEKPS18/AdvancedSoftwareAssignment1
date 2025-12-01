 # وصف مخطط الفئات

## المكونات الرئيسية

### إدارة الاتصالات
- `ConnectionPool`: يدير الاتصالات القابلة لإعادة الاستخدام (حتى 10)، آمن للخيوط مع BlockingQueue.
  - الطرق: `acquire()`، `release()`، `getAvailableConnections()`
- `Connection`: فئة اتصال بسيطة مع `executeQuery()`.

### إدارة القوالب (نمط النموذج الأولي)
- `JobPrototype` (واجهة): تحدد `clone()` و `createJobInstance()`.
- `EmailJobTemplate`، `DataProcessingJobTemplate`، `ReportJobTemplate`: نماذج ملموسة تنفذ `JobPrototype`.
- `JobTemplateRegistry`: يخزن النماذج، يوفر `getPrototype()` للاستنساخ.
- `TemplateManager`: يستخدم السجل لتوفير القوالب المستنسخة.

### تنفيذ الوظائف (الاستراتيجية + الوكيل)
- `JobStrategy` (واجهة): تحدد `execute(Job, Connection)`.
- `EmailJobStrategy`، `DataProcessingStrategy`، `ReportGenerationStrategy`: استراتيجيات ملموسة.
- `JobStrategyFactory`: يربط أنواع الوظائف بالاستراتيجيات.
- `JobExecutorInterface` (واجهة): تحدد `executeJob(Job)`.
- `JobExecutor`: المنفذ الحقيقي باستخدام الاستراتيجيات، ينفذ `JobExecutorInterface`.
- `JobExecutorProxy`: وكيل ينفذ `JobExecutorInterface`، يضيف ضوابط (الأذونات، التسجيل، التوقيت، إدارة الاتصالات).

### النموذج
- `Job`: يمثل وظيفة مع id، type، name، config، requestedBy.
- `User`: يمثل مستخدم مع name و permissions.

### التطبيق الرئيسي
- `MainApp`: نقطة الدخول، ينشئ المكونات ويشغل العرض التوضيحي.

## العلاقات الرئيسية
- `JobExecutorProxy` -> `JobExecutor` (التفويض)
- `JobExecutorProxy` -> `ConnectionPool` (يكتسب/يطلق)
- `JobExecutor` -> `JobStrategyFactory` -> `JobStrategy` (يستخدم الاستراتيجيات)
- `TemplateManager` -> `JobTemplateRegistry` -> `JobPrototype` (يستنسخ النماذج)
- `MainApp` -> جميع المكونات (التكوين)

## تطبيق النماذج
- **تجميع الاتصالات**: `ConnectionPool` لإدارة الموارد.
- **النموذج الأولي**: تسلسل `JobPrototype` مع `JobTemplateRegistry`.
- **الاستراتيجية**: تسلسل `JobStrategy` مع `JobStrategyFactory`.
- **الوكيل**: `JobExecutorProxy` يغلف `JobExecutor`.
