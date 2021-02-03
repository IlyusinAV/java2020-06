package ru.ilyusin;

import ru.ilyusin.utils.MemoryUtil;

import java.time.LocalTime;
import java.util.*;

public class OOMTest extends MemoryUtil {
    public static void main (String[] args) throws InterruptedException {
        boolean isOOM = false;
        Queue<byte[][]> memoryWasterList = new ArrayDeque<>();
        Runtime runtime = Runtime.getRuntime();
        System.out.printf("total: %11d   max: %d%n", runtime.totalMemory(), runtime.maxMemory());
        MemoryUtil.startGCMonitor();
        while (!isOOM) {
            try {
                memoryWasterList.add(new byte[1024][32 * 1_024]);
                memoryWasterList.add(new byte[1024][32 * 1_024]);
            } catch (OutOfMemoryError outOfMemoryError) {
                MemoryUtil.stopGCMonitor();
                isOOM = true;
            }
            Thread.sleep(1000);
            memoryWasterList.poll();
            Thread.sleep(1000);
            System.out.printf("time: %s used:  %11d%n", LocalTime.now(), runtime.totalMemory() - runtime.freeMemory());
        }
    }
}