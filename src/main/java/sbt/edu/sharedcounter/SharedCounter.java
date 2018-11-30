package sbt.edu.sharedcounter;


/**
 * interface provides methods for asynchronous counters
 */
public interface SharedCounter {

    /**
     *
     * @return current value of the counter
     */
    int get();

    int getAndIncrement() throws Exception;

    void reset();
}
