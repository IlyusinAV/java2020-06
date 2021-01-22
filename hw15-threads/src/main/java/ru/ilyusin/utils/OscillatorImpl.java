package ru.ilyusin.utils;

import java.util.Arrays;
import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.List;
import java.util.ArrayList;

public class OscillatorImpl implements Oscillator {
    private static final int WAVELENGTH = 18;
    private static final int PERIODS = 2;
    private final List<Integer> oscillation = new ArrayList<>();

    public OscillatorImpl() {
        IntStream natural = IntStream.iterate(1, i -> i + 1);
        natural
                .limit(10)
                .forEach(oscillation::add);

        IntStream descending = IntStream.iterate(9, i -> i - 1);
        descending
                .limit(8)
                .forEach(oscillation::add);
    }

    public void run() {
        var forkJoinPool = new ForkJoinPool();
        for (int i = 0; i < PERIODS; i++) {
            RecursiveTask<String> futureTask = new RecursiveTask<>() {
                @Override
                protected String compute() {
                    return makeWave();
                }
            };
            forkJoinPool.submit(futureTask);
            System.out.print(futureTask.join());
        }
        System.out.print("\n");
    }

    private String makeWave() {
        StringBuilder wave = new StringBuilder();
        ExecutorService service = Executors.newSingleThreadExecutor();
        CompletableFuture[] tasks = new CompletableFuture[WAVELENGTH];
        IntStream.range(0, WAVELENGTH).forEach(i -> {
            var finalI = i;
            Supplier<String> task = () -> getItem(finalI);
            tasks[i] = CompletableFuture.supplyAsync(task, service);
        });

        CompletableFuture.allOf(tasks)
                .thenRun(() -> Arrays.stream(tasks).forEach(result -> wave.append(result.join())));

        service.shutdown();

        return wave.toString();
    }

    private String getItem(int i) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return oscillation.get(i).toString() + ", ";
    }
}