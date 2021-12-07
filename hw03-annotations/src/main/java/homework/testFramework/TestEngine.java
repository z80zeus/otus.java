package homework.testFramework;

import homework.testFramework.annotations.After;
import homework.testFramework.annotations.Before;
import homework.testFramework.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestEngine {

    /**
     * Запуск теста из класса с указанным именем.
     * @param testClassName Имя класса, содержащего тесты (аннотированные функции).
     */
    public static void testIt(String testClassName) {
        try {
            var clazz = TestEngine.class.getClassLoader().loadClass(testClassName);
            System.out.println("Loaded testClass " + testClassName + "\n");
            testIt(clazz);
        }
        catch (ClassNotFoundException e) {
            failTestMessage(e.toString());
        }
    }

    /**
     * Запуск теста из указанного класса.
     * @param clazz Класс, содержащий тесты (аннотированные функции).
     */
    public static void testIt(Class<?> clazz) {
        final var beforeMethods = new ArrayList<Method>();
        final var afterMethods = new ArrayList<Method>();
        final var testMethods = new ArrayList<Method>();

        getAnnotatedMethodsFromClass(clazz, beforeMethods, afterMethods, testMethods);
        runTest(clazz, beforeMethods, afterMethods, testMethods);
    }

    /**
     * Выборка аннотированных методов из указанного класса.
     * @param clazz Класс для выборки методов.
     * @param beforeMethods В этот список будут помещены методы указанного класса, помеченные аннотацией before.
     * @param afterMethods В этот список будут помещены методы указанного класса, помеченные аннотацией after.
     * @param testMethods В этот список будут помещены методы указанного класса, помеченные аннотацией test.
     */
    private static void getAnnotatedMethodsFromClass(
            Class<?> clazz,
            List<? super Method> beforeMethods,
            List<? super Method> afterMethods,
            List<? super Method> testMethods) {
        for (var method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Before.class)) {
                method.setAccessible(true);
                beforeMethods.add(method);
            }

            if (method.isAnnotationPresent(After.class)) {
                method.setAccessible(true);
                afterMethods.add(method);
            }

            if (method.isAnnotationPresent(Test.class)) {
                method.setAccessible(true);
                testMethods.add(method);
            }
            //System.out.println(method.getName() + ": " + Arrays.toString(method.getDeclaredAnnotations()));
        }
    }

    /**
     * Запуск теста из указанного класса по подготовленным данным.
     * @param clazz Класс, который будет использоваться для создания объектов для тестирования.
     * @param beforeMethods Методы класса, которые должны будут выполнены ПЕРЕД каждым тестом.
     * @param afterMethods Методы класса, которые должны будут выполнены ПОСЛЕ каждого теста.
     * @param testMethods Тесты.
     */
    private static void runTest(Class<?> clazz,
                                List<? extends Method> beforeMethods,
                                List<? extends Method> afterMethods,
                                List<? extends Method> testMethods) {
        var passedTest = 0;
        for (var testMethod: testMethods)
            passedTest += testClassMethod(clazz, beforeMethods, afterMethods, testMethod);

        showSummary(testMethods.size(), passedTest);
    }

    /**
     * Выполнить один тестовый метод из указанного класса.
     * @param clazz Класс, который будет использоваться для создания объекта для тестирования.
     * @param beforeMethods Методы класса, которые должны будут выполнены ПЕРЕД каждым тестом.
     * @param afterMethods Методы класса, которые должны будут выполнены ПОСЛЕ каждого теста.
     * @param testMethod Тестовый метод.
     * @return 1 - если тест прошёл, 0 - если тест не прошёл.
     */
    private static int testClassMethod(Class<?> clazz,
                                   List<? extends Method> beforeMethods,
                                   List<? extends Method> afterMethods,
                                   Method testMethod) {

        Object testObject;
        try {
            testObject = clazz.getDeclaredConstructor().newInstance();
        }
        catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            return 0;
        }

        try {
            for (var beforeMethod: beforeMethods) beforeMethod.invoke(testObject);
        }
        catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return 0;
        }

        try {
            testMethod.invoke(testObject);
            passTestMessage(testMethod.getName());
            return 1;
        }
        catch (Exception e) {
            failTestMessage(testMethod.getName() + ": " + e.getCause());
            return 0;
        }
        finally {
            for (var afterMethod: afterMethods)
                try {
                    afterMethod.invoke(testObject);
                }
                catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * Вывод итогов прогона тестов.
     * @param tests Количество тестов всего.
     * @param passedTest Количество успешно пройденных тестов.
     */
    private static void showSummary(int tests, int passedTest) {
        System.out.println("Summary:");
        System.out.println("Tests: " + tests);
        System.out.println("Passed: " + passedTest);
        System.out.println("Failed: " + (tests - passedTest));
    }

    /**
     * Вывод сообщения о проваленном тесте.
     * @param msg Текст сообщения.
     */
    private static void failTestMessage(String msg) {
        System.out.println("Test failed: " + msg);
    }

    /**
     * Вывод сообщения об успешно пройденном тесте.
     * @param msg Текст сообщения.
     */
    private static void passTestMessage(String msg) {
        System.out.println("Test passed: " + msg);
    }
}
