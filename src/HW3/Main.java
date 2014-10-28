/*
 Main.java
 Lindsay Simpkins
 COMP 755
 10/28/14

 The driver file for the Readers-Writers problem.
 */
package HW3;

/**
 *
 * @author Lindsay
 */
public class Main {

    public static final int NUM_READERS = 5;
    public static final int NUM_WRITERS = 2;

    /**
     * @param args
     */
    public static void main(String[] args) {

        //Create new ReadWriteLock
        ReadWriteLock db = new Database();

        //Create and instantiate array of NUM_READERS Reader threads
        Thread[] readers = new Thread[NUM_READERS];

        for (int i = 0; i < NUM_READERS; i++) {
            readers[i] = new Thread(new Reader(db));
            readers[i].start();
        }

        //Create and instantiate array of NUM_WRITERS Writer threads
        Thread[] writers = new Thread[NUM_WRITERS];

        for (int i = 0; i < NUM_WRITERS; i++) {
            writers[i] = new Thread(new Writer(db));
            writers[i].start();
        }

        try {
            //Join the threads.
            for (int i = 0; i < NUM_READERS; i++) {
                readers[i].join();
            }
            for (int i = 0; i < NUM_WRITERS; i++) {
                writers[i].join();
            }

        } catch (InterruptedException ie) {
            System.err.println("Could not join threads");
        }
    }
}
