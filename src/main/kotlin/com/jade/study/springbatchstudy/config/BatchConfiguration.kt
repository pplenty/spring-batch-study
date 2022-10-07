package com.jade.study.springbatchstudy.config

import com.jade.study.springbatchstudy.service.ResourceService
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource

@Configuration
@EnableBatchProcessing
class BatchConfiguration @Autowired constructor(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
    private val resourceService: ResourceService,
) {

    @Value("\${file.input}")
    private val fileInput: String? = null

    @Bean
    fun sampleJob(): Job = jobBuilderFactory["sampleJob"]
        .start(step1())
        .next(mainStep())
        .build()

    @Bean
    fun step1(): Step {
        println("fileInput: $fileInput")
        return stepBuilderFactory["step1"]
            .tasklet { contribution, chunkContext ->
                println("## tasklet 1")
                RepeatStatus.FINISHED
            }
            .build()
//            .chunk<Coffee, Coffee>(10)
//            .reader(reader())
//            .processor(processor())
//            .writer(writer)
//            .build()
    }

    @Bean
    fun mainStep(): Step {
        return stepBuilderFactory["mainStep"]
            .chunk<Int, String>(7)
            .reader(reader())
            .processor(processor())
            .writer(writer())
//            .taskExecutor(SimpleAsyncTaskExecutor())
            .build()
    }

    @Bean
    fun reader(): ItemReader<Int> = ItemReader { resourceService.pollNumber() }

    @Bean
    fun processor(): ItemProcessor<Int, String> = ItemProcessor { it.toString() }

    @Bean
    fun writer(): ItemWriter<String> {
        return ItemWriter<String> { items ->
            println("item size: ${items.size}")
            println("items: $items")
            println("Thread: ${Thread.currentThread()}")
        }
    }
}