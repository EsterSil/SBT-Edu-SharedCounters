package sbt.edu.tests;

import org.junit.jupiter.api.Test;
import sbt.edu.sharedcounter.concurrentcounter.ConCounter;

import java.util.concurrent.ExecutionException;

class ConCounterTest {

    private ConCounter conCounter = new ConCounter();
    private CommonCounterTest test = new CommonCounterTest(10000);

    @Test
    void singleThreadTest() {
        try {
            test.consistencyTest(conCounter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        conCounter.reset();
    }

    @Test
    void twoThreadsTest() {
        try {
            test.lowContentionConsistencyTest(conCounter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        conCounter.reset();
    }

    @Test
    void manyThreadTest() {
        try {
            test.highContentionConsistencyTest(conCounter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        conCounter.reset();
    }

    @Test
    void uniqPerOneTest() {
        try {
            test.uniquenessPerOneTest(conCounter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        conCounter.reset();
    }

    @Test
    void uniqPerManyTest() {
        try {
            test.uniquenessPerManyTest(conCounter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        conCounter.reset();
    }

    @Test
    void uniqThroughManyTest() {
        try {
            test.uniquenessThroughManyTest(conCounter);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        conCounter.reset();
    }
}
