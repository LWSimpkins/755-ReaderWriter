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
    private int numLoops;

    public Reader(ReadWriteLock db, int numLoops) {
        this.db = db;
        this.numLoops = numLoops;
    }

    public void run() {
        int counter =0;
        while (counter < numLoops) {
            // nap for awhile
            SleepUtilities.nap();
                        
            db.acquireReadLock();
            
            // now read from the database
            System.out.println("Reading from the Database");
            SleepUtilities.nap();
            db.releaseReadLock();
            
            //Increase the counter
            counter ++;
        }
    }

}
