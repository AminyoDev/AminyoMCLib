package dev.aminyo.aminyomclib.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Reflection utilities for advanced operations
 */
public final class ReflectionUtils {

    private static final Map<String, Class<?>> CLASS_CACHE = new HashMap<>();

    private ReflectionUtils() {}

    /**
     * Get class by name with caching
     * @param className class name
     * @return class or null if not found
     */
    public static Class<?> getClass(String className) {
        return CLASS_CACHE.computeIfAbsent(className, name -> {
            try {
                return Class.forName(name);
            } catch (ClassNotFoundException e) {
                return null;
            }
        });
    }

    /**
     * Create instance of class
     * @param className class name
     * @param args constructor arguments
     * @return instance or null if failed
     */
    public static Object createInstance(String className, Object... args) {
        Class<?> clazz = getClass(className);
        if (clazz == null) return null;

        return createInstance(clazz, args);
    }

    /**
     * Create instance of class
     * @param clazz class
     * @param args constructor arguments
     * @return instance or null if failed
     */
    public static Object createInstance(Class<?> clazz, Object... args) {
        try {
            if (args.length == 0) {
                return clazz.newInstance();
            }

            Class<?>[] paramTypes = new Class<?>[args.length];
            for (int i = 0; i < args.length; i++) {
                paramTypes[i] = args[i] != null ? args[i].getClass() : Object.class;
            }

            Constructor<?> constructor = clazz.getConstructor(paramTypes);
            return constructor.newInstance(args);

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Get field value
     * @param object object instance
     * @param fieldName field name
     * @return field value or null if not found
     */
    public static Object getFieldValue(Object object, String fieldName) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Set field value
     * @param object object instance
     * @param fieldName field name
     * @param value new value
     * @return true if successful
     */
    public static boolean setFieldValue(Object object, String fieldName, Object value) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Invoke method
     * @param object object instance
     * @param methodName method name
     * @param args method arguments
     * @return method result or null if failed
     */
    public static Object invokeMethod(Object object, String methodName, Object... args) {
        try {
            Class<?>[] paramTypes = new Class<?>[args.length];
            for (int i = 0; i < args.length; i++) {
                paramTypes[i] = args[i] != null ? args[i].getClass() : Object.class;
            }

            Method method = object.getClass().getDeclaredMethod(methodName, paramTypes);
            method.setAccessible(true);
            return method.invoke(object, args);

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Check if class exists
     * @param className class name
     * @return true if exists
     */
    public static boolean classExists(String className) {
        return getClass(className) != null;
    }

    /**
     * Check if method exists
     * @param clazz class
     * @param methodName method name
     * @param paramTypes parameter types
     * @return true if exists
     */
    public static boolean methodExists(Class<?> clazz, String methodName, Class<?>... paramTypes) {
        try {
            clazz.getDeclaredMethod(methodName, paramTypes);
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    /**
     * Check if field exists
     * @param clazz class
     * @param fieldName field name
     * @return true if exists
     */
    public static boolean fieldExists(Class<?> clazz, String fieldName) {
        try {
            clazz.getDeclaredField(fieldName);
            return true;
        } catch (NoSuchFieldException e) {
            return false;
        }
    }
}