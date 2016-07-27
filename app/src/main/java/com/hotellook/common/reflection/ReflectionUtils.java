package com.hotellook.common.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import timber.log.Timber;

public class ReflectionUtils {
    public static void invokeMethodExceptionSafe(Object methodOwner, String method, TypedObject... arguments) {
        if (methodOwner != null) {
            Class<?>[] types;
            if (arguments == null) {
                try {
                    types = new Class[0];
                } catch (Throwable th) {
                    Timber.m751d("call method exception", new Object[0]);
                    return;
                }
            }
            types = new Class[arguments.length];
            Object[] objects = arguments == null ? new Object[0] : new Object[arguments.length];
            if (arguments != null) {
                int limit = types.length;
                for (int i = 0; i < limit; i++) {
                    types[i] = arguments[i].getType();
                    objects[i] = arguments[i].getObject();
                }
            }
            Method declaredMethod = methodOwner.getClass().getMethod(method, types);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(methodOwner, objects);
        }
    }

    public static Field findDeclaredFieldAndMakeItAccessible(Class clazz, String fieldName) {
        Field field = findDeclaredField(clazz, fieldName);
        if (field != null) {
            field.setAccessible(true);
        }
        return field;
    }

    public static Field findDeclaredField(Class clazz, String fieldName) {
        Field field = null;
        while (clazz != null) {
            try {
                field = clazz.getDeclaredField(fieldName);
                break;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        return field;
    }
}
