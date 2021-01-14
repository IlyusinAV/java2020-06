package ru.ilyusin;

import ru.ilyusin.utils.OscillatorImpl;

class Main {
    public static void main(String... args) {
        var oscillator = new OscillatorImpl();

        JThread t1 = new JThread("Thread1", oscillator);
        JThread t2 = new JThread("Thread2", oscillator);

        t1.start();

        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t2.start();
    }
}