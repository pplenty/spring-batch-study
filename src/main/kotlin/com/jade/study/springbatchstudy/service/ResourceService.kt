package com.jade.study.springbatchstudy.service

import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors
import java.util.stream.IntStream

@Service
class ResourceService : InitializingBean {

    private val integers: List<Int> = IntStream.range(0, 100)
        .boxed()
        .collect(Collectors.toList())

    private lateinit var numbers: LinkedList<Int>

    fun pollNumber(): Int? {
        return numbers.poll()
    }

    override fun afterPropertiesSet() {
        numbers = LinkedList(integers)
        println("number size: ${numbers.size}")
    }
}