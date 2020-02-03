package com.jason.springbatchstudy;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.LinkedList;

@SpringBootTest
class SpringBatchStudyApplicationTests {

    @Test
    void contextLoads() {

        LinkedList<Integer> nums = new LinkedList<>(Arrays.asList(1,2));

        System.out.println(nums.poll());
        System.out.println(nums.poll());
        System.out.println(nums.poll());
        System.out.println(nums.poll());
    }

}
