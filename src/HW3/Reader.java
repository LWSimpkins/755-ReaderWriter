/*
 Reader.java
 Lindsay Simpkins
 COMP 755
 10/28/14

 The Reader in the Readers-Writers problem.
 */
package HW3;

/**
 *
 * @author Lindsay
 */
public class Reader implements Runnable {

    private ReadWriteLock db;

    public Reader(ReadWriteLock db) {
        this.db = db;
    }

    public void run() {
        while (true) {
// nap for awhile
            try {
                Thread.sleep(1000);                 //one second
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            db.acquireReadLock();
// now read from the database
            try {
                System.out.println("Reading from the Database");
                Thread.sleep(1000);                 //one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            db.releaseReadLock();
        }
    }

}
