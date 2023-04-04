package com.message.brockers.main;

import com.message.brockers.consumer.Consumer;
import com.message.brockers.pool.exception.ConnectionPoolException;
import com.message.brockers.producer.Producer;

import java.util.ArrayList;
import java.util.List;

public class ConsumerProducerMain {

    public static void main(String[] args) throws ConnectionPoolException {
        int numOfThreads = Integer.parseInt(System.getenv("NUM_OF_THREADS"));
        switch (System.getenv("OPERATION")) {
            case "consumer" -> {
                List<Thread> consumerThreads = new ArrayList<>();
                for (int i = 0; i < numOfThreads; i++) {
                    consumerThreads.add(new Thread(new Consumer()));
                }
                consumerThreads.forEach(Thread::start);
            }
            case "producer" -> {
                List<Thread> producerThreads = new ArrayList<>();
                for (int i = 0; i < numOfThreads; i++) {
                    producerThreads.add(new Thread(new Producer()));
                }
                producerThreads.forEach(Thread::start);
            }
            default -> throw new IllegalArgumentException("Wrong arguments passed");
        }
    }
}
