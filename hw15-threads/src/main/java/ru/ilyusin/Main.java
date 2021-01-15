package ru.ilyusin;

import ru.ilyusin.utils.OscillatorImpl;

class Main {
    public static void main(String... args) throws InterruptedException {
        var oscillator = new OscillatorImpl();

        JThread t1 = new JThread("Thread1", oscillator);
        JThread t2 = new JThread("Thread2", oscillator);

        t1.start();
        t1.join();

        t2.start();
        t2.join();
    }
}