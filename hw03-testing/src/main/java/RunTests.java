import java.lang.reflect.*;

public class RunTests {
	public RunTests (String testClass) {
		int good = 0;
		int bad = 0;
		
		ModuleLoader loader = new ModuleLoader("", ClassLoader.getSystemClassLoader());
		try {
			Class tc = loader.loadClass(testClass);
		
			try {
				Constructor tcc = tc.getConstructor();
		
				Method[] methods = tc.getDeclaredMethods();
	      for (Method method : methods) {
					if (method.isAnnotationPresent(Test.class)) {
						
						try {
							Object tobj = tcc.newInstance();	
						
							for (Method beforemethod : methods) {
								if (beforemethod.isAnnotationPresent(Before.class)) {
									try {
										if ((boolean)beforemethod.invoke(tobj)) {
											System.out.println ("Before test: OK");
										}
									}
									catch (Exception tfe) {
										System.out.println ("Before test: failure");
									};
								}
							}
								
							try {
								if ((boolean)method.invoke(tobj)) {
									good++;
									System.out.println ("Test " + method.getName() + ": OK");
								}
							}	catch (Exception tfe) {
									bad++;
									System.out.println ("Test " + method.getName() + ": failure");
							};
								
							for (Method aftermethod : methods) {
								if (aftermethod.isAnnotationPresent(After.class)) {
									try {
										if ((boolean)aftermethod.invoke(tobj)) {
											System.out.println ("After test: OK");
										}
									}
									catch (Exception tfe) {
										System.out.println ("After test: failure");
									};
								}
							}
						} catch (InstantiationException ie) {}
							catch (IllegalAccessException iae) {}
							catch (InvocationTargetException ite) {}
					}
				}
      } catch (NoSuchMethodException nsme) {}
		}	catch (ClassNotFoundException cnfe) {System.out.println ("file TestClass.class not found");};
    System.out.println("Good: " + good + "\nBad: " + bad + "\nTotal: " + (good + bad));
	}
}