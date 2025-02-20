package com.example.springbatch.springBatch.config;

import com.example.springbatch.springBatch.entity.Student;
import com.example.springbatch.springBatch.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@AllArgsConstructor
public class SpringBatchConfig {

    private static final Logger logger = LoggerFactory.getLogger(SpringBatchConfig.class); // Logger instance

    private final StudentRepository studentRepository;

    @Bean
    public JsonItemReader<Student> reader() {
        logger.info("Initializing JSON reader");

        JacksonJsonObjectReader<Student> jsonObjectReader = new JacksonJsonObjectReader<>(Student.class);
        JsonItemReader<Student> jsonItemReader = new JsonItemReader<>();
        jsonItemReader.setJsonObjectReader(jsonObjectReader);
        jsonItemReader.setResource(new ClassPathResource("data.json"));
        jsonItemReader.setName("jsonReader");

        logger.info("JSON reader initialized successfully with resource 'data.json'");
        return jsonItemReader;
    }

    @Bean
    public StudentProcessor processor(){
        logger.info("Initializing processor");
        return new StudentProcessor();
    }

    @Bean
    public RepositoryItemWriter<Student> writer(){
        logger.info("Initializing writer");

        RepositoryItemWriter<Student> writer = new RepositoryItemWriter<>();
        writer.setRepository(studentRepository);
        writer.setMethodName("save");

        logger.info("Writer initialized successfully with repository: " + studentRepository.getClass().getName());
        return writer;
    }

    @Bean
    public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager, CustomSkipListener skipListener) {
        logger.info("Creating step for job execution with skip handling");

        return new StepBuilder("jsonStep", jobRepository)
                .<Student, Student>chunk(2, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .faultTolerant()
                .skip(Exception.class)  // Skip any exception
                .skipLimit(0)  // Allow up to 1000 skips
                .listener(skipListener)
                .build();
    }


    @Bean
    public Job job(JobRepository jobRepository, Step step) {
        logger.info("Creating job for student import");

        Job job = new JobBuilder("importStudents", jobRepository)
                .start(step)
                .build();

        logger.info("Job 'importStudents' created successfully");
        return job;
    }
}
