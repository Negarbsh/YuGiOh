package com.mygdx.game.java.view.exceptions;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ExceptionCreator {
    public static MyException createANewObject(String[] result) {
        String fullClassName = result[1];
        Object[] initials = new Object[result.length - 2];
        for (int i = 2; i < result.length; i++)
            initials[i - 2] = result[i];

        try {
            Class clazz = Class.forName(fullClassName);
            Class[] paramsTypeArray = new Class[initials.length];
            for (int i = 0; i < initials.length; i++) {
                paramsTypeArray[i] = initials[i].getClass();
            }
            Constructor cons = clazz.getDeclaredConstructor(paramsTypeArray);
            cons.setAccessible(true);

            return (MyException) cons.newInstance(initials);
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
