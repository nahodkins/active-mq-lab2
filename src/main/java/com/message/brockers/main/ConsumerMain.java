package com.message.brockers.main;

import com.message.brockers.consumer.Consumer;
import com.message.brockers.pool.exception.ConnectionPoolException;

public class ConsumerMain {

    public static void main(String[] args) throws ConnectionPoolException, InterruptedException {
        Consumer consumer = new Consumer();
        Thread thread = new Thread(consumer);

        thread.start();
        thread.join();
    }
}
