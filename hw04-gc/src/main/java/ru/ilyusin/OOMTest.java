package ru.ilyusin;

import java.util.*;

public class OOMTest extends MemoryUtil {
    public static void main (String[] args) throws InterruptedException {
        Queue<byte[][]> memoryWasterList = new ArrayDeque<>();
        Runtime runtime = Runtime.getRuntime();
        System.out.printf("total: %11d   max: %d%n", runtime.totalMemory(), runtime.maxMemory());
        while (true) {
            memoryWasterList.add(new byte[1024][32 * 1_024]);
            memoryWasterList.add(new byte[1024][32 * 1_024]);
            Thread.sleep(1000);
            memoryWasterList.poll();
            Thread.sleep(1000);
            System.out.printf("used:  %11d%n", runtime.totalMemory() - runtime.freeMemory());
        }
    }
}