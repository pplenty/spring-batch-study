package com.jason.springbatchstudy.batch.config;

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

import java.util.Arrays;
import java.util.List;

/**
 * Created by yusik on 2020/01/27.
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
public class SampleJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private int indexOfReader = 0;

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
                .<Integer, String>chunk(3)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public ItemReader<Integer> reader() {
        List<Integer> integers = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        return () -> {
            if (++indexOfReader >= integers.size()) {
                return null;
            }
            return integers.get(indexOfReader);
        };
    }

    @Bean
    public ItemProcessor<Integer, String> processor() {
        return String::valueOf;
    }

    @Bean
    public ItemWriter<String> writer() {
        return items -> log.info("items: {}", items);
    }
}
