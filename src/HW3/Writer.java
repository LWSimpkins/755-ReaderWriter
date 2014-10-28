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

    public Writer(ReadWriteLock db) {
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
            db.acquireWriteLock();
// now write to write to the database
            try {
                System.out.println("Writing to the Database");
                Thread.sleep(1000);                 //one second
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            db.releaseWriteLock();
        }
    }

}
