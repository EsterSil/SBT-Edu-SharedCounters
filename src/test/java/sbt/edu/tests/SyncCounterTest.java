package sbt.edu.tests;

import org.junit.jupiter.api.Test;
import sbt.edu.sharedcounter.syncronizedcounter.SyncCounter;

import java.util.concurrent.ExecutionException;


public class SyncCounterTest {
    private SyncCounter counter = new SyncCounter();
    private CommonCounterTest test = new CommonCounterTest(1000);


    @Test
    void singleThreadTest() {
        try {
            test.consistencyTest(counter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        counter.reset();
    }

    @Test
    void twoThreadsTest() {
        try {
            test.lowContentionConsistencyTest(counter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        counter.reset();
    }

    @Test
    void manyThreadTest() {
        try {
            test.highContentionConsistencyTest(counter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        counter.reset();
    }

    @Test
    void uniqPerOneTest() {
        try {
            test.uniquenessPerOneTest(counter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        counter.reset();
    }

    @Test
    void uniqPerManyTest() {
        try {
            test.uniquenessPerManyTest(counter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void uniqThroughManyTest() {
        try {
            test.uniquenessThroughManyTest(counter);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        counter.reset();
    }
}
