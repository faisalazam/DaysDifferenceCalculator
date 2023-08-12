package com.mindworks.calculator.days;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static java.lang.reflect.Modifier.FINAL;

public class ReflectionHelper {
    private static final String MODIFIERS = "modifiers";

    /*
        I guess, since Java 17, to use reflection, we need to add the following to VM options. POM.xml has already
        been updated accordingly, but don't forget to add the following to VM option if running this class from IDE:
        --add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.lang.reflect=ALL-UNNAMED
     */
    public static void setField(Object object, final String fieldName, final Object fieldValue) throws Exception {
        final Class<?> clazz = object.getClass();
        final Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);

        final Method getDeclaredFields0 = Class.class.getDeclaredMethod("getDeclaredFields0", boolean.class);
        getDeclaredFields0.setAccessible(true);
        final Field[] fields = (Field[]) getDeclaredFields0.invoke(Field.class, false);
        Field modifiersField = null;
        for (Field each : fields) {
            if (MODIFIERS.equals(each.getName())) {
                modifiersField = each;
                break;
            }
        }
        assert modifiersField != null;
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~FINAL);
        field.set(object, fieldValue);
    }

    public static Object invokeStaticPrivateMethod(final Class<?> objectClass,
                                                   final String methodName,
                                                   final Class<?>[] argClasses,
                                                   final Object... argument) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        final Method tagMethod = makePrivateMethodAccessible(objectClass, methodName, argClasses);
        return tagMethod.invoke(null, argument);
    }

    private static Method makePrivateMethodAccessible(final Class<?> objectClass,
                                                      final String methodName,
                                                      final Class<?>... argClasses) throws NoSuchMethodException {
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
