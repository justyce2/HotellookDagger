package com.hotellook.utils.threads;

import android.os.Handler;
import android.os.HandlerThread;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TaskExecutor extends HandlerThread {
    private static Map<String, TaskExecutor> sThreads;
    private Handler mHandler;

    static {
        sThreads = new ConcurrentHashMap();
    }

    private TaskExecutor(String name) {
        super(name);
        start();
        this.mHandler = new Handler(getLooper());
    }

    public static TaskExecutor getTaskExecutor(String name) {
        synchronized (name.intern()) {
            if (sThreads.containsKey(name)) {
                TaskExecutor taskExecutor = (TaskExecutor) sThreads.get(name);
                return taskExecutor;
            }
            TaskExecutor executor = new TaskExecutor(name);
            sThreads.put(name, executor);
            return executor;
        }
    }

    public void post(Runnable runnable) {
        this.mHandler.removeCallbacksAndMessages(null);
        this.mHandler.post(runnable);
    }
}
