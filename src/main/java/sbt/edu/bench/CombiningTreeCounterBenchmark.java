package sbt.edu.bench;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import sbt.edu.sharedcounter.SharedCounter;
import sbt.edu.sharedcounter.combiningtree.CombiningTreeCounter;

import java.util.concurrent.TimeUnit;

@State(Scope.Group)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@BenchmarkMode(Mode.Throughput)
@OperationsPerInvocation(1000000)
public class CombiningTreeCounterBenchmark {


    private final static SharedCounter counter = new CombiningTreeCounter(20);

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder().include(CombiningTreeCounterBenchmark.class.getSimpleName())
                .warmupIterations(2)
                .measurementIterations(5)
                .forks(1).build();

        new Runner(opt).run();

    }

    @Benchmark
    @Group("oneThreadTest")
    @GroupThreads(value = 1)
    public void oneThread(Blackhole bh) throws Exception {

        for (int i = 0; i < 1000000; i++) {
            bh.consume(counter.getAndIncrement());
        }
    }

    @Benchmark
    @Group("twoThreadTest")
    @GroupThreads(value = 2)
    public void twoThreads(Blackhole bh) throws Exception {
        for (int i = 0; i < 1000000; i++) {
            bh.consume(counter.getAndIncrement());
        }
    }

    @Benchmark
    @Group("fourThreadTest")
    @GroupThreads(value = 4)
    public void fourThreads(Blackhole bh) throws Exception {
        for (int i = 0; i < 1000000; i++) {
            bh.consume(counter.getAndIncrement());
        }
    }

    @Benchmark
    @Group("eightThreadTest")
    @GroupThreads(value = 8)
    public void eightThreads(Blackhole bh) throws Exception {
        for (int i = 0; i < 1000000; i++) {
            bh.consume(counter.getAndIncrement());
        }
    }

    @Benchmark
    @Group("sixteenThreadTest")
    @GroupThreads(value = 16)
    public void sixteenThreads(Blackhole bh) throws Exception {
        for (int i = 0; i < 1000000; i++) {
            bh.consume(counter.getAndIncrement());
        }
    }

}
