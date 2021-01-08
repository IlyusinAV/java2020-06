package ru.ilyusin;


import ru.ilyusin.utils.Oscillator;
import ru.ilyusin.utils.OscillatorImpl;

import java.util.concurrent.ExecutionException;

class JThread extends Thread implements Runnable {
    Oscillator oscillator;

    JThread(String name, Oscillator oscillator) {
        super(name);
        this.oscillator = oscillator;
    }

    @Override
    public void run() {
        System.out.print(Thread.currentThread().getName() + ": ");
        try {
            oscillator.run();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}