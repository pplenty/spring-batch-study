package com.jason.springbatchstudy.batch.config;

import com.jason.springbatchstudy.batch.ResourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

/**
 * Created by yusik on 2020/01/27.
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
public class SampleJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ResourceService resourceService;

    @Bean
    public Job sampleJob() {
        return jobBuilderFactory.get("sampleJob")
                .start(step1())
                .next(mainStep())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet((contribution, chunkContext) -> {
                    log.info("## tasklet 1");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step mainStep() {
        return stepBuilderFactory.get("mainStep")
                .<Integer, String>chunk(1000)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }

    @Bean
    public ItemReader<Integer> reader() {
        return resourceService::pollNumber;
    }

    @Bean
    public ItemProcessor<Integer, String> processor() {
        return String::valueOf;
    }

    @Bean
    public ItemWriter<String> writer() {
        return items -> {
            log.info("item size: {}", items.size());
            log.info("items: {}", items);
            log.info("Thread: {}", Thread.currentThread().toString());

        };
    }
}
