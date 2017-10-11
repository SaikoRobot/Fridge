package com.saikorobot.fridge;

import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseArray;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Fridge.
 */
public final class Fridge {

    private static final Type ARRAY_LIST_INTEGER = new ArrayList<Integer>() {
    }.getClass().getGenericSuperclass();

    private static final Type ARRAY_LIST_STRING = new ArrayList<String>() {
    }.getClass().getGenericSuperclass();

    private static final Type ARRAY_LIST_CHAR_SEQUENCE = new ArrayList<CharSequence>() {
    }.getClass().getGenericSuperclass();

    public static void restoreInstanceState(Object target, Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return;
        }
        try {
            processRestore(target, savedInstanceState);
        } catch (IllegalAccessException e) {
            //
        }
    }

    public static void saveInstanceState(Object target, Bundle outState) {
        try {
            processSave(target, outState);
        } catch (IllegalAccessException e) {
            //
        }
    }

    private static void processRestore(Object target, Bundle savedInstanceState)
            throws IllegalAccessException {
        final String className = target.getClass().getName();
        final Field[] fields = target.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            State state = field.getAnnotation(State.class);
            if (state == null) {
                continue;
            }
            String key = className + "." + field.getName();
            if (savedInstanceState.get(key) == null) {
                continue;
            }
            Object value = savedInstanceState.get(key);
            field.set(target, value);
        }
    }

    private static void processSave(Object target, Bundle outState)
            throws IllegalAccessException {
        final String className = target.getClass().getName();
        final Field[] fields = target.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            State state = field.getAnnotation(State.class);
            if (state == null) {
                continue;
            }
            String key = className + "." + field.getName();
            Object value = field.get(target);
            Class<?> type = field.getType();
            if (type.equals(ArrayList.class)) {
                ParameterizedType genericType = (ParameterizedType) field.getGenericType();
                if (genericType.equals(ARRAY_LIST_INTEGER)) {
                    outState.putIntegerArrayList(key, (ArrayList<Integer>) value);
                } else if (genericType.equals(ARRAY_LIST_STRING)) {
                    outState.putStringArrayList(key, (ArrayList<String>) value);
                } else if (genericType.equals(ARRAY_LIST_CHAR_SEQUENCE)) {
                    outState.putCharSequenceArrayList(key, (ArrayList<CharSequence>) value);
                } else if (ClassUtils.isGenericTypeAssignableFrom(genericType, Parcelable.class)) {
                    outState.putParcelableArrayList(key, (ArrayList<? extends Parcelable>) value);
                }
            } else if (type.equals(SparseArray.class)) {
                ParameterizedType genericType = (ParameterizedType) field.getGenericType();
                if (ClassUtils.isGenericTypeAssignableFrom(genericType, Parcelable.class)) {
                    outState.putSparseParcelableArray(key,
                            (SparseArray<? extends Parcelable>) value);
                }
            } else if (!type.isArray()) {
                if (type.equals(boolean.class)) {
                    outState.putBoolean(key, (boolean) value);
                } else if (type.equals(int.class)) {
                    outState.putInt(key, (int) value);
                } else if (type.equals(float.class)) {
                    outState.putFloat(key, (float) value);
                } else if (type.equals(short.class)) {
                    outState.putShort(key, (short) value);
                } else if (type.equals(byte.class)) {
                    outState.putByte(key, (byte) value);
                } else if (type.equals(char.class)) {
                    outState.putChar(key, (char) value);
                } else if (type.equals(long.class)) {
                    outState.putLong(key, (long) value);
                } else if (type.equals(double.class)) {
                    outState.putDouble(key, (double) value);
                } else if (type.equals(CharSequence.class)) {
                    outState.putCharSequence(key, (CharSequence) value);
                } else if (type.equals(String.class)) {
                    outState.putString(key, (String) value);
                } else if (type.equals(Bundle.class)) {
                    outState.putBundle(key, (Bundle) value);
                } else if (type.equals(Parcelable.class) || ClassUtils
                        .isTypeAssignableFrom(type, Parcelable.class)) {
                    outState.putParcelable(key, (Parcelable) value);
                } else if (type.equals(IBinder.class)) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                        outState.putBinder(key, (IBinder) value);
                    }
                } else if (type.equals(Serializable.class) || ClassUtils
                        .isTypeAssignableFrom(type, Serializable.class)) {
                    outState.putSerializable(key, (Serializable) value);
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        if (type.equals(Size.class)) {
                            outState.putSize(key, (Size) value);
                        } else if (type.equals(SizeF.class)) {
                            outState.putSizeF(key, (SizeF) value);
                        }
                    }
                }
            } else {
                if (type.equals(boolean[].class)) {
                    outState.putBooleanArray(key, (boolean[]) value);
                } else if (type.equals(int[].class)) {
                    outState.putIntArray(key, (int[]) value);
                } else if (type.equals(float[].class)) {
                    outState.putFloatArray(key, (float[]) value);
                } else if (type.equals(short[].class)) {
                    outState.putShortArray(key, (short[]) value);
                } else if (type.equals(byte[].class)) {
                    outState.putByteArray(key, (byte[]) value);
                } else if (type.equals(char[].class)) {
                    outState.putCharArray(key, (char[]) value);
                } else if (type.equals(long[].class)) {
                    outState.putLongArray(key, (long[]) value);
                } else if (type.equals(double[].class)) {
                    outState.putDoubleArray(key, (double[]) value);
                } else if (type.equals(CharSequence[].class)) {
                    outState.putCharSequenceArray(key, (CharSequence[]) value);
                } else if (type.equals(String[].class)) {
                    outState.putStringArray(key, (String[]) value);
                } else if (type.equals(Parcelable[].class) || ClassUtils
                        .isTypeAssignableFrom(type, Parcelable[].class)) {
                    outState.putParcelableArray(key, (Parcelable[]) value);
                }
            }

        }
    }
}
