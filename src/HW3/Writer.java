/*
 Writer.java
 Lindsay Simpkins
 COMP 755
 10/28/14

 The Writer in the Readers-Writers problem.
 */
package HW3;

/**
 *
 * @author Lindsay
 */
public class Writer implements Runnable {

    private ReadWriteLock db;
    private int numLoops;

    public Writer(ReadWriteLock db, int numLoops) {
        this.db = db;
        this.numLoops = numLoops;
    }

    public void run() {
        int counter =0;
        while (counter < numLoops) {
            // nap for awhile
            SleepUtilities.nap();
                        
            db.acquireWriteLock();
            
            // now write to write to the database
            System.out.println("Writing to the Database");
            SleepUtilities.nap();
            db.releaseWriteLock();
            
            //Increase the counter
            counter ++;
        }
    }

}
