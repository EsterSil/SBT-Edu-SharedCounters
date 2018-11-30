package sbt.edu.tests;

import org.junit.jupiter.api.Assertions;
import sbt.edu.sharedcounter.SharedCounter;
import sbt.edu.tests.tasks.CounterConsistencyTask;
import sbt.edu.tests.tasks.CounterUniqTask;

import java.util.*;
import java.util.concurrent.*;

/**
 * these tests are intended to confirm correctness of counters designed
 * <p>
 * every thread runs simple task {@link CounterConsistencyTask} or {@link CounterUniqTask}
 */
class CommonCounterTest {

    private int bound;

    CommonCounterTest(int bound) {
        this.bound = bound;
    }

    /**
     * single thread consistency
     *
     * @throws InterruptedException
     */

    void consistencyTest(SharedCounter counter) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new CounterConsistencyTask(counter, bound));
        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        Assertions.assertEquals(bound, counter.get());
    }


    /**
     * two thread consistency test
     *
     * @throws InterruptedException
     * @throws ExecutionException
     */

    void lowContentionConsistencyTest(SharedCounter counter) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 2; i++) {
            executor.execute(new CounterConsistencyTask(counter, bound));
        }
        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        Assertions.assertEquals(2 * bound, counter.get());
    }


    /**
     * many thread with high consistency test
     *
     * @param counter
     * @throws InterruptedException
     * @throws ExecutionException
     */
    void highContentionConsistencyTest(SharedCounter counter) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            executor.execute(new CounterConsistencyTask(counter, bound));
        }

        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        Assertions.assertEquals(10 * bound, counter.get());
    }


    void uniquenessPerOneTest(SharedCounter counter) throws InterruptedException {
        List<Callable<List<Integer>>> tasks = new ArrayList<>();
        tasks.add(new CounterUniqTask(bound, counter));
        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<Future<List<Integer>>> futures = executor.invokeAll(tasks);
        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        // Assertions.assertEquals(bound, counter.get());
        try {
            if (futures.get(0).get() != null) {
                Set<Integer> result = new HashSet<>(futures.get(0).get());

                Assertions.assertEquals(result.size(), futures.get(0).get().size());

            } else {
                Assertions.assertTrue(false);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    void uniquenessThroughManyTest(SharedCounter counter) throws InterruptedException, ExecutionException {
        List<Callable<List<Integer>>> tasks = new ArrayList<>();
        tasks.add(new CounterUniqTask(bound, counter));
        tasks.add(new CounterUniqTask(bound, counter));
        tasks.add(new CounterUniqTask(bound, counter));
        tasks.add(new CounterUniqTask(bound, counter));
        tasks.add(new CounterUniqTask(bound, counter));
        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<Future<List<Integer>>> futures = executor.invokeAll(tasks);
        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        for (int i = 0; i < futures.size() - 1; i++) {
            for (int j = i + 1; j < futures.size(); j++) {
                Assertions.assertTrue(Collections.disjoint(futures.get(i).get(), futures.get(j).get()));
            }
        }
    }


    void uniquenessPerManyTest(SharedCounter counter) throws InterruptedException {
        List<Callable<List<Integer>>> tasks = new ArrayList<>();
        tasks.add(new CounterUniqTask(bound, counter));
        tasks.add(new CounterUniqTask(bound, counter));
        tasks.add(new CounterUniqTask(bound, counter));
        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<Future<List<Integer>>> futures = executor.invokeAll(tasks);
        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        // Assertions.assertEquals(bound, counter.get());
        for (Future<List<Integer>> future : futures) {
            try {
                if (future.get() != null) {
                    Set<Integer> result = new HashSet<>(futures.get(0).get());
                    Assertions.assertEquals(result.size(), futures.get(0).get().size());

                } else {
                    Assertions.assertTrue(false);
                }
            } catch (ExecutionException e1) {
                e1.printStackTrace();
            }
        }
    }


}
