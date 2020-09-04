package utils;

import annotations.Log;
import interfaces.MyClassInterface;

import java.lang.reflect.*;
import java.util.*;

public class MyProxy {
    static Class clazz = null;
    static List<Method> annotatedMethods = new ArrayList<>();
    static InvocationHandler handler = null;

    private MyProxy() {
    }

    public static MyClassInterface createMyClass(String myClassImpl) {
        try {
            clazz = Class.forName(myClassImpl);
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(Log.class)) annotatedMethods.add(method);
            }            
            handler = new DemoInvocationHandler((MyClassInterface) clazz.getDeclaredConstructor().newInstance());    
        } catch (Exception e) {
            System.out.println("Can't find class for logging");
        }

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
            if (isAnnotatedMethod(method)) {
                System.out.print("executed method: " + method.getName());
                for (int i = 0; i < args.length; i++) {
                    System.out.print(" param: " + args[i]);
                }
                System.out.print("\n");
            }
            return method.invoke(myClass, args);
        }
        
        private boolean isAnnotatedMethod (Method method) {
            for (Method candidatMethod : annotatedMethods) {
                if (candidatMethod.getName().equals(method.getName()) && 
                    Arrays.equals(candidatMethod.getParameterTypes(), method.getParameterTypes())) {
                    return true;
                }
            }
            return false;
        }
    }
}
