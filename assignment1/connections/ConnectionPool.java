import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Simple Connection Pool - manages reusable database connections.
 * Supports up to 10 connections, blocks if none available.
 */
public class ConnectionPool {
    private final BlockingQueue<Connection> pool;
    private final int MAX_CONNECTIONS = 10;
    private int connectionCounter = 0;

    public ConnectionPool() {
        pool = new LinkedBlockingQueue<>(MAX_CONNECTIONS);
    }

    /**
     * Get a connection from the pool. Blocks if none available.
     */
    public Connection acquire() throws InterruptedException {
        Connection conn = pool.poll();
        if (conn == null && connectionCounter < MAX_CONNECTIONS) {
            synchronized (this) {
                if (connectionCounter < MAX_CONNECTIONS) {
                    conn = new Connection("Conn-" + (++connectionCounter));
                }
            }
        }
        if (conn == null) {
            conn = pool.take();
        }
        return conn;
    }

    /**
     * Return connection to pool for reuse.
     */
    public void release(Connection connection) {
        if (connection != null) {
            try {
                pool.put(connection);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public int getAvailableConnections() {
        return pool.size();
    }
}
