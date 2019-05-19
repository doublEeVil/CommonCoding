package com._22evil.test.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Fork(value = 2, jvmArgs = {"-Xms4G", "-Xmx4G"})
public class JMHTest {

    private int action(int val) {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {

        }
        return 2 * val;
    }

    @Benchmark
    public int test1() {
        return IntStream.of(1,5,6,7,54).map(this::action).sum();
    }

    public static void main(String[] args) throws RunnerException{
        Options opt = new OptionsBuilder()
                .include(JMHTest.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }
}
