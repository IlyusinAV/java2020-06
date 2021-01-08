package ru.ilyusin.utils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.List;
import java.util.ArrayList;

public class OscillatorImpl implements Oscillator {
    private static final int WAVELENGTH = 18;
    private static final int PERIODS = 2;
    private List<Integer> items;
    private CompletableFuture[] jobs = new CompletableFuture[WAVELENGTH];

    public OscillatorImpl() {
        items = this.build();
    }

    public void run() throws ExecutionException, InterruptedException {
        synchronized (items) {
            int completed;

            for (int times = 0; times < PERIODS; times++) {
                completed = 0;
                for (int i = 0; i < WAVELENGTH; i++) {
                    int finalI = i;
                    CompletableFuture job = CompletableFuture.supplyAsync(() -> getItem(finalI));
                    jobs[i] = job;
                }
                while (completed < WAVELENGTH) {
                    for (int j = 0; j < WAVELENGTH; j++) {
                        if (jobs[j].isDone()) {
                            completed++;
                        }
                    }
                }
                for (int k = 0; k < WAVELENGTH; k++) {
                    jobs[k].thenAccept(result -> System.out.print(result + ", "));
                }
            }

            System.out.print("\n");
        }
    }

    private List<Integer> build() {
        List<Integer> oscillation = new ArrayList<>();

        IntStream natural = IntStream.iterate(1, i -> i + 1);
        natural
                .limit(10)
                .forEach(oscillation::add);

        IntStream descending = IntStream.iterate(9, i -> i - 1);
        descending
                .limit(8)
                .forEach(oscillation::add);

        return oscillation;
    }

    private String getItem(int i) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return items.get(i).toString();
    }

}