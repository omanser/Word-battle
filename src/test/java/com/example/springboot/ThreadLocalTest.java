package com.example.springboot;

import org.junit.jupiter.api.Test;

public class ThreadLocalTest {
    @Test
    public void testThreadLocal() {
        ThreadLocal t1 = new ThreadLocal();

        new Thread(() -> {
            t1.set("消炎");
            System.out.println(Thread.currentThread().getName() + ": " +t1.get());
            System.out.println(Thread.currentThread().getName() + ": " +t1.get());
            System.out.println(Thread.currentThread().getName() + ": " +t1.get());
        }, "蓝色").start();

        new Thread(() -> {
            t1.set("药尘");
            System.out.println(Thread.currentThread().getName() + ": " +t1.get());
            System.out.println(Thread.currentThread().getName() + ": " +t1.get());
            System.out.println(Thread.currentThread().getName() + ": " +t1.get());
        }, "白色").start();
    }
}
