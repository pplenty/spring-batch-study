package com.jason.springbatchstudy.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by yusik on 2020/02/03.
 */
@Slf4j
@Service
public class ResourceService implements InitializingBean {

    private final Object pollSync = new Object();

    private List<Integer> integers = IntStream.range(0, 10000)
            .boxed()
            .collect(Collectors.toList());

    private LinkedList<Integer> numbers;

    public Integer pollNumber() {
        Integer temp;
        synchronized (pollSync) {
            temp = numbers.poll();
        }
        return temp;
    }

    @Override
    public void afterPropertiesSet() {
        numbers = new LinkedList<>(integers);
        log.info("number size: {}", numbers.size());
        System.out.println(numbers.size());
    }
}
