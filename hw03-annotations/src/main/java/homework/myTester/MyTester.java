package homework.myTester;

import java.lang.annotation.*;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value=ElementType.METHOD)
public @interface Before {}

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value=ElementType.METHOD)
public @interface After {}

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value=ElementType.METHOD)
public @interface Test {}

public class MyTester {
    public static void runTest(String className) throws ClassNotFoundException {
        Class clazz = Class.forName(className);
    }
}
