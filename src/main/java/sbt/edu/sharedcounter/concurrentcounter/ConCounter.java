package sbt.edu.sharedcounter.concurrentcounter;

import sbt.edu.sharedcounter.SharedCounter;

import java.util.concurrent.atomic.AtomicInteger;

public class ConCounter implements SharedCounter {

    private AtomicInteger counter;

    public ConCounter() {
        counter = new AtomicInteger(0);
    }

    public int get() {
        return counter.get();
    }

    public int getAndIncrement() {
        return counter.getAndIncrement();
    }

    @Override
    public void reset() {
        counter = new AtomicInteger(0);
    }
}
