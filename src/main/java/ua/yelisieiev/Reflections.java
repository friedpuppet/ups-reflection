package ua.yelisieiev;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class Reflections {
    private int a;
    public double b;
    private Boolean boo;

    public Object instantiateObject(Class<?> someClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return someClass.getDeclaredConstructor().newInstance();
    }

    public List<String> callNoParamsMethods(Object someObject) throws InvocationTargetException, IllegalAccessException {
        List<String> resultsList = new ArrayList<>();
        for (Method declaredMethod : someObject.getClass().getDeclaredMethods()) {
            if (declaredMethod.getParameterCount() == 0) {
                declaredMethod.setAccessible(true);
                resultsList.add(String.valueOf(declaredMethod.invoke(someObject)));
            }
        }
        return resultsList;
    }

    public List<String> getNonPublicMethods(Class<?> someClass) {
        List<String> nonpublicMethods = new ArrayList<>();
        for (Method declaredMethod : someClass.getDeclaredMethods()) {
            if (!Modifier.isPublic(declaredMethod.getModifiers())) {
                nonpublicMethods.add(declaredMethod.getName());
            }
        }
        return nonpublicMethods;
    }

    public List<Class> getAncestors(Class<?> someClass) {
        List<Class> ancestors = new ArrayList<>();
        Class currentClass = someClass;
        while (currentClass != null) {
            if (currentClass != someClass) {
                ancestors.add(currentClass);
            }
            for (Class aClass : currentClass.getInterfaces()) {
                ancestors.add(aClass);
            }
            currentClass = currentClass.getSuperclass();
        }
        return ancestors;
    }

    public void nullify(Object someObject) throws IllegalAccessException {
        for (Field declaredField : someObject.getClass().getDeclaredFields()) {
            declaredField.setAccessible(true);
            if (!Modifier.isPublic(declaredField.getModifiers())) {
//                System.out.println(declaredField.getType().toString() + " " + declaredField.getName());
                if (!declaredField.getType().isPrimitive()) {
//                    System.out.println(declaredField.getType().toString() + " " + declaredField.getName());
                    declaredField.set(someObject, null);
                } else if (declaredField.getType() == Integer.TYPE) {
//                    System.out.println(declaredField.getType().toString() + " " + declaredField.getName());
                    declaredField.setInt(someObject, 0);
                } else if (declaredField.getType() == Boolean.TYPE) {
//                    System.out.println(declaredField.getType().toString() + " " + declaredField.getName());
                    declaredField.setBoolean(someObject, false);
                } else if (declaredField.getType() == Double.TYPE) {
//                    System.out.println(declaredField.getType().toString() + " " + declaredField.getName());
                    declaredField.setDouble(someObject, 0.0);

                }
            }
        }

    }

    public List<String> getFinalMethodsSignatures(Object someObject) throws NoSuchFieldException, IllegalAccessException {
        List<String> signaturesList = new ArrayList<>();
        for (Method declaredMethod : someObject.getClass().getDeclaredMethods()) {
            if (Modifier.isFinal(declaredMethod.getModifiers())) {
                signaturesList.add(String.valueOf(declaredMethod.toString()));
            }
        }
        return signaturesList;
    }


}
