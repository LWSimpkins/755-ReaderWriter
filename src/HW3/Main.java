/*
 Main.java
 Lindsay Simpkins
 COMP 755
 10/28/14

 The driver file for the Readers-Writers problem.
Four arguments are expected from the command line, in the following order:
NUM_READERS: the number of Reader threads
LOOP_READERS: the number of times each Reader thread should loop
NUM_WRITERS: the number of Writer threads
LOOP_Writers: the number of times each Writer thread should loop

The Reader and Writer threads are created here, and passed the same ReadWriteLock
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
