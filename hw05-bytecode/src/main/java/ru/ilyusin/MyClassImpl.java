package ru.ilyusin;

import ru.ilyusin.annotations.Log;
import ru.ilyusin.interfaces.MyClassInterface;

public class MyClassImpl implements MyClassInterface {

    public void calculation(int param1) {
    }

    @Log
    public void calculation(int param1, int param2) {
    }

    public void calculation(int param1, int param2, String param3) {
    }

}
