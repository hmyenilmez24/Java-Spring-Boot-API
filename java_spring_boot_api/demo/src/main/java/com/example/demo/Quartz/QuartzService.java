//package com.example.demo.Quartz;
//
//import org.quartz.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.quartz.SchedulerFactoryBean;
//import org.springframework.stereotype.Service;
//import java.util.List;
//
//@Service
//class QuartzService {
//
//    @Autowired
//    private SchedulerFactoryBean schedulerFactoryBean;
//
//    void init(List<MyTrigger> triggers) throws SchedulerException {
//        Scheduler scheduler = schedulerFactoryBean.getScheduler();
//        scheduler.addJob(job(), true);
//
//        for (MyTrigger myTrigger: triggers) {
//            scheduler.scheduleJob(trigger(myTrigger));
//        }
//    }
//
//    private JobDetail job() {
//        return JobBuilder.newJob(SampleJob.class)
//                .withIdentity("QuartzJob", "QuartzJob")
//                .storeDurably()
//                .build();
//    }
//
//    private Trigger trigger(MyTrigger trigger) {
//        return TriggerBuilder.newTrigger()
//                .withIdentity(trigger.name)
//                .withSchedule(
//                        CronScheduleBuilder.cronSchedule(trigger.schedule))
//                .forJob("QuartzJob", "QuartzJob")
//                .build();
//    }
//}
//
//class MyTrigger {
//    String name;
//    String schedule;
//}
