/*
 Main.java
 Lindsay Simpkins
 COMP 755
 10/28/14

 The driver file for the Readers-Writers problem.
 Four arguments are expected from the command line, in the following order:
 NUM_READERS: the number of Reader threads
 NUM_WRITERS: the number of Writer threads
 LOOP_READERS: the number of times each Reader thread should loop
 LOOP_WRITERS: the number of times each Writer thread should loop

 The Reader and Writer threads are created here, and passed the same ReadWriteLock
 */
package HW3;

/**
 *
 * @author Lindsay
 */
public class Main {

    public static int NUM_READERS;
    public static int NUM_WRITERS;
    public static int LOOP_READERS;
    public static int LOOP_WRITERS;

    /**
     * @param args
     */
    public static void main(String[] args) {
        //check if a number was input via command line
        if (args.length >= 4) {            
            //Verify the command line arguments are numbers
            try {
                //input values from the command line
                NUM_READERS = Integer.parseInt(args[0]);
                NUM_WRITERS = Integer.parseInt(args[1]);
                LOOP_READERS = Integer.parseInt(args[2]);                
                LOOP_WRITERS = Integer.parseInt(args[3]);
            } catch (NumberFormatException e) {
                System.err.println("A command line argument was not an integer");
            }

            //Verify all the command line arguments are >=0
            if (NUM_READERS < 0 || LOOP_READERS < 0 || NUM_WRITERS < 0 || LOOP_WRITERS <0) {
                System.err.println("All command line arguments must be 0 or greater");
            } else {
                //Create new ReadWriteLock
                ReadWriteLock db = new Database();

                //Create and instantiate array of NUM_READERS Reader threads
                Thread[] readers = new Thread[NUM_READERS];

                for (int i = 0; i < NUM_READERS; i++) {
                    readers[i] = new Thread(new Reader(db, LOOP_READERS));
                    readers[i].start();
                }

                //Create and instantiate array of NUM_WRITERS Writer threads
                Thread[] writers = new Thread[NUM_WRITERS];

                for (int i = 0; i < NUM_WRITERS; i++) {
                    writers[i] = new Thread(new Writer(db, LOOP_WRITERS));
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
                
                System.out.println("\nAll Readers and Writers are finished");
            }

        }else {
            System.err.println("Missing command line arguments");
        }
    }
}