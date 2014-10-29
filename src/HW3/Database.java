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
    private Condition condReaders;
    private Condition condWriters;

    public Database() {
        readerCount = 0;
        isWriting = false;
        lock = new ReentrantLock();
        condReaders = lock.newCondition();
        condWriters = lock.newCondition();
    }

    public void acquireReadLock() {
        lock.lock();

        try {
            //If writer is writing, wait until signaled
            if (isWriting) {
                condReaders.await();
            }
            //Thread was signaled and no Writers are writing
            //This thread can read, update readerCount
            ++readerCount;
            //if this is the first Reader, signal all other Readers, so they can read too
            if(readerCount==1){
                condReaders.signalAll();
            }
        } catch (InterruptedException ie) {
        } finally {
            lock.unlock();
        }
    }

    public void releaseReadLock() {
        lock.lock();
        try {
            //Reader done, update readerCount
            --readerCount;
            
            //If there are no Readers reading, signal all Writers
            if(readerCount==0){
                condWriters.signalAll();
            }
            
            //Signal all waiting threads
            //condReaders.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void acquireWriteLock() {
        lock.lock();

        try {
            //If readers are reading, or another Writer is writing
            //Wait until signaled
            if (readerCount > 0 || isWriting) {
                condWriters.await();
            }
            //Thread was signaled and no Readers reading or Writiers writing
            //This thread can write, update isWriting
            isWriting = true;
        } catch (InterruptedException ie) {
        } finally {
            lock.unlock();
        }
    }

    public void releaseWriteLock() {
        lock.lock();
        try {
            //Writer is done, update isWriting
            isWriting = false;
            
            //Signal all waiting Reader and Writer threads
            condReaders.signalAll();
            condWriters.signalAll();
        } finally {
            lock.unlock();
        }
    }

}
