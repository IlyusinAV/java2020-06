package hw05.bytecode.interfaces;

import hw05.bytecode.annotations.Log;

public interface MyClassInterface {

  void calculation(int param1);

  @Log
	void calculation(int param1, int param2);

	void calculation(int param1, int param2, String param3);
}
