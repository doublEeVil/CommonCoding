package com._22evil.test.lambda;

import org.junit.Test;

import java.util.Objects;

/**
 * Objects类
 * 总体来看，除了Objects.requireNonNull(Object obj),
 * 其他方法都用的不多
 */
public class TestObjects {

    @Test
    public void testObjects() {
        Integer intVal = null;
        Objects.requireNonNull(intVal);
    }

    @Test
    public void testDeepEqual() {
        int[] arr1 = new int[]{1,2,3};
        int[] arr2 = new int[] {1,2,3};
        System.out.println(arr1 == arr2); //  false
        System.out.println(Objects.equals(arr1, arr2)); // false
        System.out.println(Objects.deepEquals(arr1, arr2)); // true
    }
}
