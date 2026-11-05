package com.sayazhan;

public class Main {

    public static void main(String[] args) {

        InputGenerator generator = new InputGenerator();

        System.out.println("Random: "
                + generator.randomString(10));

        System.out.println("Repeated: "
                + generator.repeatedString(10, 'a'));

        System.out.println("Worst pattern: "
                + generator.worstPattern(5));
    }
}
