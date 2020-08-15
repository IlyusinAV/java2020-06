package hw05.bytecode.utils;

import hw05.bytecode.MyClassImpl;
import hw05.bytecode.annotations.Log;
import hw05.bytecode.interfaces.MyClassInterface;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyProxy {

    private MyProxy() {
    }

    public static MyClassInterface createMyClass() {
        InvocationHandler handler = new DemoInvocationHandler(new MyClassImpl());
        return (MyClassInterface) Proxy.newProxyInstance(MyProxy.class.getClassLoader(),
                new Class<?>[]{MyClassInterface.class}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final MyClassInterface myClass;

        DemoInvocationHandler(MyClassInterface myClass) {
            this.myClass = myClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.isAnnotationPresent(Log.class)) {
                System.out.print ("executed method: " + method.getName());
                for (int i=0; i < args.length; i++) {
                    System.out.print (" param: " + args[i]);
                }
                System.out.print("\n");
            }
            return method.invoke(myClass, args);
        }
     }
}
