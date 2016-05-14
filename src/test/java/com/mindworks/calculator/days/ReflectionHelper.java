package com.mindworks.calculator.days;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static java.lang.reflect.Modifier.FINAL;

public class ReflectionHelper {
    private static final String MODIFIERS = "modifiers";

    public static void setField(Object object, final String fieldName, final Object fieldValue) throws Exception {
        final Class clazz = object.getClass();
        final Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);

        final Field modifiersField = Field.class.getDeclaredField(MODIFIERS);
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~FINAL);

        field.set(object, fieldValue);
    }

    public static Object invokeStaticPrivateMethod(final Class objectClass,
                                                   final String methodName,
                                                   final Class<?>[] argClasses,
                                                   final Object... argument) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        final Method tagMethod = makePrivateMethodAccessible(objectClass, methodName, argClasses);
        return tagMethod.invoke(null, argument);
    }

    private static Method makePrivateMethodAccessible(final Class objectClass,
                                                      final String methodName,
                                                      final Class... argClasses) throws NoSuchMethodException {
        Method tagMethod;
        try {
            tagMethod = objectClass.getDeclaredMethod(methodName, argClasses);
        } catch (NoSuchMethodException e) {
            if (objectClass.getSuperclass() != null) {
                tagMethod = objectClass.getSuperclass().getDeclaredMethod(methodName, argClasses);
            } else {
                throw e;
            }
        }
        tagMethod.setAccessible(true);
        return tagMethod;
    }
}
