package utils;

import annotations.After;
import annotations.Before;
import annotations.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class RunTests {
	private static Class tc;
	private static List<Method> beforeMethods = new ArrayList<>();
	private static List<Method> testMethods = new ArrayList<>();
	private static List<Method> afterMethods = new ArrayList<>();
	
	public static void run (String testClass) {
		int good = 0;
		int bad = 0;
		
		try {
			tc = Class.forName(testClass);
			readAnnotatedMethods(tc);
			for (Method testmethod : testMethods) {
				Object tobj = createTestObject(tc);
				runBefore(tobj);
				try {
					testmethod.invoke(tobj);
					good++;
					System.out.println ("Test " + testmethod.getName() + ": OK");
				}	catch (Exception tfe) {
					bad++;
					System.out.println ("Test " + testmethod.getName() + ": failure");
				};
				runAfter(tobj);
			}
		}	catch (ClassNotFoundException cnfe) {System.out.println ("file TestClass.class not found");};		
    System.out.println("Good: " + good + "\nBad: " + bad + "\nTotal: " + (good + bad));
	}
	
	private static void readAnnotatedMethods (Class tc) {
		Method[] methods = tc.getDeclaredMethods();
	  for (Method method : methods) {
	   	if (method.isAnnotationPresent(Before.class)) beforeMethods.add(method);
	   	if (method.isAnnotationPresent(Test.class)) testMethods.add(method);
	   	if (method.isAnnotationPresent(After.class)) afterMethods.add(method);
	  }
	}
	
	private static Object createTestObject(Class tc) {
		try {
			Constructor tcc = tc.getConstructor();
			try {
				return tcc.newInstance();	
			} catch (InstantiationException ie) {}
				catch (IllegalAccessException iae) {}
				catch (InvocationTargetException ite) {}
		} catch (NoSuchMethodException nsme) {}
		return null;
	}
	
	private static void runBefore (Object tobj) {
		for (Method beforemethod : beforeMethods) {
			try {
				beforemethod.invoke(tobj);
				System.out.println ("Before test: OK");
			}	catch (Exception tfe) {
				System.out.println ("Before test: failure");
			};
		}
	}
	
	private static void runAfter (Object tobj) {
		for (Method aftermethod : afterMethods) {
			try {
				aftermethod.invoke(tobj);
				System.out.println ("After test: OK");
			}	catch (Exception tfe) {
				System.out.println ("After test: failure");
			};
		}				
	}	
	
}