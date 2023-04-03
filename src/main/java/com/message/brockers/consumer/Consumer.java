package com.message.brockers.consumer;

import com.message.brockers.util.ConsoleIOUtil;
import com.message.brockers.pool.exception.ConnectionPoolException;
import com.message.brockers.pool.ConnectionPool;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

public class Consumer implements Runnable, ExceptionListener {

    private ConnectionPool connectionPool;

    public Consumer() throws ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
    }

    @Override
    public void run() {
        try {
            Connection connection = connectionPool.getConnection();
            connection.setExceptionListener(this);

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            String queue = ConsoleIOUtil.getOutputFromConsole("Enter queue name:");
            Destination destination = session.createQueue(queue);

            MessageConsumer consumer = session.createConsumer(destination);

            while (true) {
                TextMessage receivedMessage = (TextMessage) consumer.receive();
                String text = receivedMessage.getText();

                if (text == null || text.isEmpty()) {
                    break;
                }

                System.out.println("Received message: " + text);
            }

            consumer.close();
            session.close();
            connectionPool.releaseConnection(connection);
        } catch (ConnectionPoolException | JMSException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onException(JMSException exception) {
        exception.printStackTrace();
    }
}
