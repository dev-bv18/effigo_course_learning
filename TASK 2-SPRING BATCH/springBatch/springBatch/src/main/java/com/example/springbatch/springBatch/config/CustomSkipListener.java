package com.example.springbatch.springBatch.config;
import com.example.springbatch.springBatch.entity.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.listener.SkipListenerSupport;
import org.springframework.stereotype.Component;

@Component
public class CustomSkipListener extends SkipListenerSupport<Student, Student> {

    private static final Logger logger = LoggerFactory.getLogger(CustomSkipListener.class);

    @Override
    public void onSkipInRead(Throwable t) {
        logger.error("Skipped an item during reading: " + t.getMessage(), t);
    }

    @Override
    public void onSkipInProcess(Student student, Throwable t) {
        logger.error("Skipped processing student: " + student + " due to: " + t.getMessage(), t);
    }

    @Override
    public void onSkipInWrite(Student student, Throwable t) {
        logger.error("Skipped writing student: " + student + " due to: " + t.getMessage(), t);
    }
}
