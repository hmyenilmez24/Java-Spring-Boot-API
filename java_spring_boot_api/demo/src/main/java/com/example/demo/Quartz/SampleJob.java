//package com.example.demo.Quartz;
//
//import com.example.demo.Repository.ItemRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.quartz.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//
//
//@Slf4j
//@Component
//public class SampleJob implements Job {
//
//    @Autowired
//    private final ItemRepository itemRepository;
//
//    public SampleJob(ItemRepository itemRepository) {
//        this.itemRepository = itemRepository;
//    }
//
//    @Override
//    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        itemRepository.findById(100L);
//        log.info(String.valueOf(itemRepository.findById(100L)));
//    }
//
//    @Bean
//    public JobDetail jobDetail(){
//        return JobBuilder.newJob().ofType(SampleJob.class)
//                .storeDurably()
//                .withIdentity("Quartz_Job_Detail")
//                .withDescription("Estoy invocando el job de ejempo")
//                .build();
//    }
//
//    @Bean
//    public  Trigger trigger(JobDetail jobDetail){
//        return TriggerBuilder.newTrigger().forJob(jobDetail)
//                .withIdentity("Quartz_Trigger")
//                .withDescription("Trigger de ejemplo")
//                .withSchedule(SimpleScheduleBuilder.simpleSchedule().repeatForever().withIntervalInMilliseconds(5000))
//                .build();
//    }
//
//}
