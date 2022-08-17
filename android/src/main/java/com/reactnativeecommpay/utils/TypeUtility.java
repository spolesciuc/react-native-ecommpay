package com.reactnativeecommpay.utils;


public class TypeUtility {
  public static boolean stringIsNotEmpty(String str) {
    return str != null && !str.trim().isEmpty();
  }

  public static boolean stringIsEmpty(String str) {
    return !stringIsNotEmpty(str);
  }

  public static long convertToLong(double value) {
    return Double.valueOf(value).longValue();
  }
}
