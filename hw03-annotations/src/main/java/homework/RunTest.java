package homework;

import static homework.testFramework.TestEngine.testIt;

/**
 * Точка входа в программу - запускатель теста.
 */
public class RunTest {
    public static void main(String[] args) {
        Class<TestClass> clazz = TestClass.class;
        testIt(clazz);
    }
}
