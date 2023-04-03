package com.message.brockers.main;

import com.message.brockers.pool.exception.ConnectionPoolException;
import com.message.brockers.producer.Producer;

public class ProducerMain {

    public static void main(String[] args) throws ConnectionPoolException, InterruptedException {
        Producer producer = new Producer();
        Thread thread = new Thread(producer);

        thread.start();
        thread.join();
    }
}
