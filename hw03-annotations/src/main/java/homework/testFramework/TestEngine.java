package homework.testFramework;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestEngine {
    private final List<Method> beforeMethods = new ArrayList<>();
    private final List<Method> afterMethods = new ArrayList<>();
    private final List<Method> testMethods = new ArrayList<>();

    public static void testIt(Class<?> clazz) {
        System.out.println(clazz.getName());
        for (Method method : clazz.getDeclaredMethods()) {
            System.out.println(method.getName() + ": " + Arrays.toString(method.getDeclaredAnnotations()));
        }
    }
}
