package ru.ilyusin.utils;

import java.util.concurrent.ExecutionException;

public interface Oscillator {
    public void run() throws ExecutionException, InterruptedException;
}