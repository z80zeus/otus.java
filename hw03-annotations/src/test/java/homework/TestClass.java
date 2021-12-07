package homework;

import homework.testFramework.annotations.*;

/**
 * Тест, тестирующий Class4Test с использованием testFramework.
 */
public class TestClass {
    @Before
    void runBeforeEachTestIteration() {
        System.out.println("Before test.");
    }

    @After
    void runAfterEachTestIteration() {
        System.out.println("After test.");
    }

    @Test
    void test1() throws Exception {
        System.out.println("Test1.");
        final var testIt = new Class4Test();
        testIt.intField = 1;
        if (testIt.intField != 1) throw new Exception("Bad test1");
    }

    @Test
    void test2() throws Exception {
        System.out.println("Test2.");
        final var testIt = new Class4Test();
        testIt.intField = Integer.MAX_VALUE;
        if (testIt.intField != Integer.MAX_VALUE) throw new Exception("Bad test2");
    }

    @Test
    void test3() throws Exception {
        System.out.println("Test3.");
        final var testIt = new Class4Test();
        testIt.intField = Integer.MIN_VALUE;
        if (testIt.intField != Integer.MAX_VALUE) throw new Exception("Bad test3");
    }
}
