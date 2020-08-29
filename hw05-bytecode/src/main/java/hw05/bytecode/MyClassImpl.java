package hw05.bytecode;

import hw05.bytecode.annotations.Log;
import hw05.bytecode.interfaces.MyClassInterface;

public class MyClassImpl implements MyClassInterface {

    public void calculation(int param1) {
    }

    @Log
    public void calculation(int param1, int param2) {
    }

    public void calculation(int param1, int param2, String param3) {
    }

}
