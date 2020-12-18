package hw05.bytecode.utils;

import hw05.bytecode.annotations.Log;
import hw05.bytecode.interfaces.MyClassInterface;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class MyProxy {

    private MyProxy() {
    }

    public static MyClassInterface createMyClass(String myClassImpl) {
        try {
            Class myClassImplClass = Class.forName(myClassImpl);
            List<Method> annotatedMethods = new ArrayList<>();
            Method[] methods = myClassImplClass.getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(Log.class)) annotatedMethods.add(method);
            }
            InvocationHandler handler = new DemoInvocationHandler(myClassImplClass, annotatedMethods);
            return (MyClassInterface) Proxy.newProxyInstance(MyProxy.class.getClassLoader(),
                    new Class<?>[]{MyClassInterface.class}, handler);

        } catch (Exception e) {
            System.out.println("Can't find class for logging");
            return null;
        }

    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final Class myClass;
        private final List<Method> annotatedMethods;

        DemoInvocationHandler(Class myClass, List<Method> annotatedMethods) {
            this.myClass = myClass;
            this.annotatedMethods = annotatedMethods;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (annotatedMethods.indexOf(method) >= 0) {
                System.out.print("executed method: " + method.getName());
                for (int i = 0; i < args.length; i++) {
                    System.out.print(" param: " + args[i]);
                }
                System.out.print("\n");
            }
            return method.invoke(myClass, args);
        }
    }
}
