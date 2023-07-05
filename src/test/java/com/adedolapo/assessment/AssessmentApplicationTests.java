package com.adedolapo.assessment;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        TransactionControllerTests.class
})

public class AssessmentApplicationTests {

    @Test
    void contextLoads() {
    }

}
