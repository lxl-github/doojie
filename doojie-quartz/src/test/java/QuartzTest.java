import com.doojie.domain.quartz.ScheduleJob;
import com.doojie.quartz.scheduler.ScheduleJobService;
import com.doojie.quartz.scheduler.support.ScheduleJobServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>类描述</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lixiaoliang
 * @version 1.0
 * @since 1.0
 */
public class QuartzTest {

    private static ScheduleJobService scheduleJobService = new ScheduleJobServiceImpl();

    public static void main(String[] args) {
        try {
            ScheduleJob scheduleJob = new ScheduleJob();
            String job_name = "动态任务调度";
            System.out.println("【系统启动】开始(每1秒输出一次)...");
            scheduleJob.setJobName(job_name);
            scheduleJob.setJobStatus(scheduleJob.STATUS_RUNNING);
            scheduleJob.setJobGroup("job");
            scheduleJob.setCronExpression("0/1 * * * * ?");
            scheduleJob.setBeanClass("com.doojie.quartz.job.AutoSchedulingWorkJob");
            scheduleJobService.addJob(scheduleJob);

            Thread.sleep(5000);


            System.out.println("【修改时间】开始(每2秒输出一次)...");
            ScheduleJob scheduleJob2 = new ScheduleJob();
            String job_name2 = "动态任务调度";
            scheduleJob2.setJobName(job_name2);
            scheduleJob2.setJobGroup("job");
            scheduleJob2.setCronExpression("10/2 * * * * ?");
            scheduleJobService.updateJobCron(scheduleJob2);


            Thread.sleep(6000);
            System.out.println("【移除定时】开始...");
            ScheduleJob scheduleJob3 = new ScheduleJob();
            String job_name3 = "动态任务调度";
            scheduleJob3.setJobName(job_name3);
            scheduleJob3.setJobGroup("job");
            scheduleJobService.deleteJob(scheduleJob3);
            System.out.println("【移除定时】成功");

            System.out.println("【再次添加定时任务】开始(每10秒输出一次)...");
            ScheduleJob scheduleJob4 = new ScheduleJob();
            String job_name4 = "动态任务调度";
            scheduleJob4.setJobName(job_name4);
            scheduleJob4.setJobGroup("job");
            scheduleJob.setJobStatus(scheduleJob.STATUS_RUNNING);
            scheduleJob4.setBeanClass("com.doojie.quartz.job.AutoSchedulingWorkJob");
            scheduleJob4.setCronExpression("*/10 * * * * ?");
            scheduleJobService.addJob(scheduleJob4);
            Thread.sleep(60000);

            ScheduleJob scheduleJob5 = new ScheduleJob();
            String job_name5 = "动态任务调度";
            scheduleJob5.setJobName(job_name5);
            scheduleJob5.setJobGroup("job");
            scheduleJobService.deleteJob(scheduleJob5);
            System.out.println("【移除定时】成功");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
