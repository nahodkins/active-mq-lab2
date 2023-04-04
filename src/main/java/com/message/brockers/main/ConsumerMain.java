package com.message.brockers.main;

import com.message.brockers.consumer.Consumer;
import com.message.brockers.pool.exception.ConnectionPoolException;

public class ConsumerMain {

    public static void main(String[] args) throws ConnectionPoolException, InterruptedException {
        Consumer firstConsumer = new Consumer();
        Consumer secondConsumer = new Consumer();
        Thread firstThread = new Thread(firstConsumer);
        firstThread.setName("firstThread");
        Thread secondThread = new Thread(secondConsumer);
        secondThread.setName("secondThread");

        firstThread.start();
        secondThread.start();

        secondThread.join();
        firstThread.join();
    }
}
