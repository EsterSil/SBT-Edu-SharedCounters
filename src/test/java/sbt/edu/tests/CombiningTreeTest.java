package sbt.edu.tests;

import org.junit.jupiter.api.Test;
import sbt.edu.sharedcounter.combiningtree.CombiningTreeCounter;

import java.util.concurrent.ExecutionException;

public class CombiningTreeTest {

    private CommonCounterTest test = new CommonCounterTest(1000);
    private CombiningTreeCounter counter = new CombiningTreeCounter(10);

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
        counter.reset();
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
