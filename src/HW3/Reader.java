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
            SleepUtilities.nap();
                        
            db.acquireReadLock();
            
            // now read from the database
            System.out.println("Reading from the Database");
            SleepUtilities.nap();
            db.releaseReadLock();
        }
    }

}
