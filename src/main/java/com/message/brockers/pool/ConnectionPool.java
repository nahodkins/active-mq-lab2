package com.message.brockers.pool;

import com.message.brockers.pool.exception.ConnectionPoolException;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {

    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final int POOL_SIZE = 20;

    private static ConnectionPool instance;
    private final ActiveMQConnectionFactory connectionFactory;
    private final List<Connection> connections;
    private final List<Connection> busyConnections;

    public static ConnectionPool getInstance() throws ConnectionPoolException {
        if (instance == null) {
            try {
                instance = new ConnectionPool();
            } catch (JMSException e) {
                throw new ConnectionPoolException("Failed to init connection pool", e);
            }
        }
        return instance;
    }

    public Connection getConnection() throws ConnectionPoolException {
        if (connections.isEmpty()) {
            throw new ConnectionPoolException("Connection pool is empty");
        } else {
            Connection connection = connections.remove(0);
            busyConnections.add(connection);
            return connection;
        }
    }

    public void releaseConnection(Connection connection) throws ConnectionPoolException {
        if (busyConnections.contains(connection)) {
            busyConnections.remove(connection);
            connections.add(connection);
        } else {
            throw new ConnectionPoolException("Attempt to release  foreign connection");
        }
    }

    private ConnectionPool() throws JMSException {
        connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        connections = new ArrayList<>();
        busyConnections = new ArrayList<>();
        initConnectionPool();
    }

    private void initConnectionPool() throws JMSException {
        for (int i = 0; i < POOL_SIZE; i++) {
            Connection connection = connectionFactory.createConnection();
            connection.start();
            connections.add(connection);
        }
    }
}
