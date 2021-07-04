package ru.otus;

import com.google.common.primitives.Ints;

public class HelloOtus {
    public static void main(String[] args) {
        System.out.println("Hello Otus!");
        int[] ints = { 1, 2, 3, 4, 5 };
        System.out.println("Max = " + Ints.max(ints));
    }
}
