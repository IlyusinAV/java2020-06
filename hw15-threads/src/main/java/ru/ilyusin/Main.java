package ru.ilyusin;

import ru.ilyusin.utils.OscillatorImpl;

import java.time.LocalTime;

class Main {
    public static void main(String... args) throws InterruptedException {
        var oscillator = new OscillatorImpl();

        JThread t1 = new JThread("Thread1", oscillator);
        JThread t2 = new JThread("Thread2", oscillator);

        System.out.println("Starting: " + LocalTime.now());
        t1.start();
        t1.join();

        t2.start();
        t2.join();
        System.out.println("Finished: " + LocalTime.now());
    }
}