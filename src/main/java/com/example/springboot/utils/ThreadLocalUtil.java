package com.example.springboot.utils;

public class ThreadLocalUtil {
    private static final ThreadLocal THREAD_LOCAL = new ThreadLocal();
    public static void set(Object userId) {
        THREAD_LOCAL.set(userId);
    }
    public static <T> T get() {
        return (T)THREAD_LOCAL.get();
    }
    // 避免出现内存泄漏
    public static void remove() {
        THREAD_LOCAL.remove();
    }
}
