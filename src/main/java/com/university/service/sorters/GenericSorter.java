package com.university.service.sorters;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class GenericSorter {
    public static <T> void sort(List<T> list, String... constructorNames) {
        list.sort((o1, o2) -> {
            try {
                Object[] values1 = getConstructorValues(o1, constructorNames);
                Object[] values2 = getConstructorValues(o2, constructorNames);

                // Compare based on the constructor values
                for (int i = 0; i < Math.min(values1.length, values2.length); i++) {
                    int comparison = compareValues(values1[i], values2[i]);
                    if (comparison != 0) {
                        return comparison;
                    }
                }
                // If all compared values are equal, sort by object hash code (or some other means)
                return Integer.compare(o1.hashCode(), o2.hashCode());
            } catch (Exception e) {
                e.printStackTrace();
                return 0; // In case of error, consider them equal
            }
        });
    }

    private static <T> Object[] getConstructorValues(T object, String[] constructorNames) throws Exception {
        Object[] values = new Object[constructorNames.length];
        for (int i = 0; i < constructorNames.length; i++) {
            Method method = object.getClass().getMethod(constructorNames[i]);
            values[i] = method.invoke(object);
        }
        return values;
    }

    private static int compareValues(Object v1, Object v2) {
        // If both are null, consider equal
        if (v1 == null && v2 == null) return 0;
        if (v1 == null) return 1; // nulls are considered greater
        if (v2 == null) return -1;

        // Compare based on type
        if (v1 instanceof Comparable && v2 instanceof Comparable) {
            return ((Comparable<Object>) v1).compareTo(v2);
        }
        // Fallback comparison (you can customize this)
        return v1.toString().compareTo(v2.toString());
    }
}
