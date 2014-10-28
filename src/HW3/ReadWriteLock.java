/*
ReadWriteLock.java
Lindsay Simpkins
COMP 755
10/28/14

The given interface (figure 6.17) from Operating System Concepts with Java 8th Ed.
 */

package HW3;

/**
 *
 * @author Lindsay
 */
public interface ReadWriteLock {
    public void acquireReadLock();
    public void acquireWriteLock();
    public void releaseReadLock();
    public void releaseWriteLock();
}
