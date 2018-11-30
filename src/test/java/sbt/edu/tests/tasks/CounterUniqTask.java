package sbt.edu.tests.tasks;

import sbt.edu.sharedcounter.SharedCounter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class CounterUniqTask implements Callable<List<Integer>> {

    public List<Integer> getValues() {
        return values;
    }

    private List<Integer> values = new ArrayList<>();
    private int bound;
    private SharedCounter counter;

    public CounterUniqTask(int bound, SharedCounter counter) {
        this.bound = bound;
        this.counter = counter;
    }

    public void run() {


    }

    @Override
    public List<Integer> call() throws Exception {
        System.out.println(Thread.currentThread().getName() + " started");
        for (int i = 0; i < bound; i++) {
            try {
                values.add(counter.getAndIncrement());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return values;
    }
}
