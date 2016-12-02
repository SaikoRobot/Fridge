package com.saikorobot.fridge;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * ClassUtils.
 */
class ClassUtils {

    private static final String TYPE_NAME_PREFIX = "class ";

    private static String getClassName(Type type) {
        if (type == null) {
            return "";
        }
        String clazzName = type.toString();
        if (clazzName.startsWith(TYPE_NAME_PREFIX)) {
            clazzName = clazzName.replace(TYPE_NAME_PREFIX, "");
        }
        return clazzName;
    }

    private static Class<?> getClass(Type type)
            throws ClassNotFoundException {
        String className = getClassName(type);
        if (className == null || className.isEmpty()) {
            return null;
        }
        return Class.forName(className);
    }

    static boolean isTypeAssignableFrom(Type type, Class<?> clazz) {
        Class<?> aClazz = null;
        try {
            aClazz = getClass(type);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (aClazz != null && clazz.isAssignableFrom(aClazz)) {
            return true;
        }
        return false;
    }

    static boolean isGenericTypeAssignableFrom(ParameterizedType type, Class<?> clazz) {
        Type[] actualTypes = type.getActualTypeArguments();
        for (Type actualType : actualTypes) {
            Class<?> aClazz = null;
            try {
                aClazz = getClass(actualType);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (aClazz != null && clazz.isAssignableFrom(aClazz)) {
                return true;
            }
        }
        return false;
    }
}
