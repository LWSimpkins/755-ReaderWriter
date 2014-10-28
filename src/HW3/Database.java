/*
 Database.java
 Lindsay Simpkins
 COMP 755
 10/28/14

 Implementation of the ReadWriteLock interface.
 A solution to the Readers-Writers problem, using Java condition variables.
 */
package HW3;

import java.util.concurrent.Semaphore;

/**
 *
 * @author Lindsay
 */
public class Database implements ReadWriteLock {

    private int readerCount;
    private Semaphore mutex;
    private Semaphore db;

    public Database() {
        readerCount = 0;
        mutex = new Semaphore(1);
        db = new Semaphore(1);
    }

    public void acquireReadLock() {
        mutex.acquire();
        /**
         * The first reader indicates that the database is being read.
         */
        ++readerCount;
        if (readerCount == 1) {
            db.acquire();
        }
        mutex.release();
    }

    public void releaseReadLock() {
        mutex.acquire();
        /**
         * The last reader indicates that the database is no longer being read.
         */
        --readerCount;
        if (readerCount == 0) {
            db.release();
        }
        mutex.release();
    }

    public void acquireWriteLock() {
        db.acquire();
    }

    public void releaseWriteLock() {
        db.release();
    }

}
