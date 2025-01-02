package com.damao.common.context;

public class BaseContext {
    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentUid(Long id) {
        threadLocal.set(id);
    }

    public static Long getCurrentUid() {
        return threadLocal.get();
    }

    public static void removeCurrentUid() {
        threadLocal.remove();
    }
}
