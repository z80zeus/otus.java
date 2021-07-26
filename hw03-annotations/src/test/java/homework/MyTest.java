package homework;

import homework.class4test.TestIt;
import homework.myTester.*;

public class MyTest {
    @Before
    void runBeforeEachTestIteration() {
    }

    @After
    void runAfterEachTestIteration() {
    }

    @Test
    void test1() {
        TestIt testIt = new TestIt();
        testIt.intField = 1;
    }

    @Test
    void test2() {
        TestIt testIt = new TestIt();
        testIt.intField = Integer.MAX_VALUE;
    }

    @Test
    void test3() {
        TestIt testIt = new TestIt();
        testIt.intField = Integer.MIN_VALUE;
    }
}
