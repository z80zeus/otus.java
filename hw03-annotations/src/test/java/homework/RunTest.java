package homework;

import static homework.testFramework.TestEngine.testIt;

public class RunTest {
    /**
     * Точка входа в программу - запускатель теста.
     * @param args Программа воспринимает единственный, но обязательный аргумент: название класса с тестами.
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Bad param: no class name.");
            showUsage(System.err);
            return;
        }
        testIt(args[0]);
    }

    /**
     * Вывод информации об использовании программы. Функция может выводить в любой поток, поддерживающий печать.
     * @param stream Поток для вывода.
     */
    private static void showUsage(java.io.PrintStream stream) {
        stream.println("\nUsage:");
        stream.println("RunTest <TestClassName>");
        stream.println("TestClassName - class name contains annotated test functions.");
    }
}
