package com.message.brockers.producer;

import com.message.brockers.util.ConsoleIOUtil;
import com.message.brockers.pool.exception.ConnectionPoolException;
import com.message.brockers.pool.ConnectionPool;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

public class Producer implements Runnable {

    private final ConnectionPool connectionPool;

    public Producer() throws ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
    }

    @Override
    public void run() {
        try {
            Connection connection = connectionPool.getConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            String queue = ConsoleIOUtil.getOutputFromConsole("Enter queue name:");
            Destination destination = session.createQueue(queue);

            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            String message;
            while (!(message = ConsoleIOUtil.getOutputFromConsole("Enter message:")).isEmpty()) {
                producer.send(session.createTextMessage(message));
            }

            producer.send(session.createTextMessage());

            producer.close();
            session.close();
            connectionPool.releaseConnection(connection);
        } catch (ConnectionPoolException | JMSException e) {
            e.printStackTrace();
        }
    }
}
