package ua.yelisieiev;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ua.yelisieiev.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReflectionsTest {
    Reflections reflections = new Reflections();

    private static interface Nanny {

    }

    private static interface Homeworker {

    }

    private static class Baba implements Nanny {

    }

    private static class Mama extends Baba implements Homeworker {

    }

    private static class TestClass extends Mama {
        public boolean mamaImNaked = true;
        private int age = 4;
        private ArrayList<String> friendsList = new ArrayList<>();
        private boolean attendsSchool = true;
        private double height = 90.5;

        public final String getHello() {
            return "Hello";
        }

        protected final int getSum(int a, int b) {
            return a + b;
        }

        private double getPi() {
            return 3.14;
        }
    }

    @DisplayName("Return object of a given class")
    @Test
    public void test_InstantiateGivenClass() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Object newObject = reflections.instantiateObject(String.class);
        assertEquals(String.class, newObject.getClass());
    }

    @DisplayName("Calls every method without parameters of a given object")
    @Test
    public void test_CallAllMethodsWithoutParams() throws InvocationTargetException, IllegalAccessException {
        List<String> resultList = reflections.callNoParamsMethods(new TestClass());
        assertTrue(resultList.remove("Hello"));
        assertTrue(resultList.remove("3.14"));
        assertEquals(0, resultList.size());
    }

    @DisplayName("Shows signatures of all final methods of a given object")
    @Test
    public void test_ShowFinalMethodSignatures() throws NoSuchFieldException, IllegalAccessException {
        List<String> methodsSignaturesList = reflections.getFinalMethodsSignatures(new TestClass());
        assertTrue(methodsSignaturesList.remove("protected final int ua.yelisieiev.ReflectionsTest$TestClass.getSum(int,int)"));
        assertTrue(methodsSignaturesList.remove("public final java.lang.String ua.yelisieiev.ReflectionsTest$TestClass.getHello()"));
        assertEquals(0, methodsSignaturesList.size());
    }

    @DisplayName("Shows all non public methods of a given class")
    @Test
    public void test_ShowAllNonpublicMethods() {
        List<String> methodNamesList = reflections.getNonPublicMethods(TestClass.class);
        assertTrue(methodNamesList.remove("getSum"));
        assertTrue(methodNamesList.remove("getPi"));
        assertEquals(0, methodNamesList.size());
    }

    @DisplayName("Shows all ancestors and interfaces of a given class")
    @Test
    public void test_ShowAllAncestors() {
        List<Class> ancestorsList = reflections.getAncestors(TestClass.class);
        assertTrue(ancestorsList.remove(Object.class));
        assertTrue(ancestorsList.remove(Mama.class));
        assertTrue(ancestorsList.remove(Baba.class));
        assertTrue(ancestorsList.remove(Nanny.class));
        assertTrue(ancestorsList.remove(Homeworker.class));
        assertEquals(0, ancestorsList.size());
    }

    @DisplayName("Nullify all private fields of a given object")
    @Test
    public void test_Nullify() throws IllegalAccessException {
        TestClass someObject = new TestClass();
        reflections.nullify(someObject);
        assertTrue(someObject.mamaImNaked);
        assertEquals(0, someObject.age);
        assertNull(someObject.friendsList);
        assertFalse(someObject.attendsSchool);
        assertEquals(0.0, someObject.height);
    }
}
