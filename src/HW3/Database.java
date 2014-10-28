/*
 Database.java
 Lindsay Simpkins
 COMP 755
 10/28/14

 Implementation of the ReadWriteLock interface.
 A solution to the Readers-Writers problem, using Java condition variables.
 */
package HW3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Lindsay
 */
public class Database implements ReadWriteLock {

    private int readerCount;
    private boolean isWriting;
    private Lock lock;
    private Condition condVar;

    public Database() {
        readerCount = 0;
        isWriting = false;
        lock = new ReentrantLock();
        condVar = lock.newCondition();
    }

    public void acquireReadLock() {
        lock.lock();

        try {
            //If writer is writing, wait until signaled
            if (isWriting) {
                condVar.await();
            }
            //Thread was signaled and Rriter not writing
            //Reader can read, update readerCount
            ++readerCount;
        } catch (InterruptedException ie) {
        } finally {
            lock.unlock();
        }
    }

    public void releaseReadLock() {
        lock.lock();
        //Reader done, update readerCount
        --readerCount;

        //Signal all waiting threads
        condVar.signalAll();
        lock.unlock();
    }

    public void acquireWriteLock() {
        lock.lock();

        try {
            //If readers are reasing, wait until signaled
            if (readerCount != 0) {
                condVar.await();
            }
            //Thread was signaled and no Readers reading 
            //Writer can write, update isWriting
            isWriting = true;
        } catch (InterruptedException ie) {
        } finally {
            lock.unlock();
        }
    }

    public void releaseWriteLock() {
        lock.lock();

        //Writer is done, update isWriting
        isWriting = false;

        //Signal all waiting threads
        condVar.signalAll();
        lock.unlock();
    }

}
