package com.jzz.util;

/**
 * 字符串工具类
 * String Utility Class
 *
 * 提供常用的字符串操作方法
 * Provides common string operation methods
 *
 * @author Jzz
 * @version 1.0
 */
public class StringUtil {

    /**
     * 判断字符串是否为空或 null
     * Check if string is empty or null
     *
     * @param str 要检查的字符串
     * @return true 为空，false 不为空
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * 判断字符串是否不为空
     * Check if string is not empty
     *
     * @param str 要检查的字符串
     * @return true 不为空，false 为空
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 将字符串转换为整数，如果转换失败返回默认值
     * Convert string to integer, return default value if conversion fails
     *
     * @param str 要转换的字符串
     * @param defaultValue 默认值
     * @return 转换后的整数
     */
    public static Integer toInt(String str, Integer defaultValue) {
        if (isEmpty(str)) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(str.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * 将字符串转换为整数
     * Convert string to integer
     *
     * @param str 要转换的字符串
     * @return 转换后的整数，失败返回 null
     */
    public static Integer toInt(String str) {
        return toInt(str, null);
    }

    /**
     * 将字符串转换为长整数
     * Convert string to long
     *
     * @param str 要转换的字符串
     * @param defaultValue 默认值
     * @return 转换后的长整数
     */
    public static Long toLong(String str, Long defaultValue) {
        if (isEmpty(str)) {
            return defaultValue;
        }
        try {
            return Long.parseLong(str.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * 将字符串数组转换为整数数组
     * Convert string array to integer array
     *
     * @param strArray 字符串数组
     * @return 整数数组
     */
    public static Integer[] toIntArray(String[] strArray) {
        if (strArray == null || strArray.length == 0) {
            return new Integer[0];
        }

        Integer[] intArray = new Integer[strArray.length];
        for (int i = 0; i < strArray.length; i++) {
            intArray[i] = toInt(strArray[i]);
        }
        return intArray;
    }

    /**
     * 为模糊查询添加通配符
     * Add wildcards for fuzzy search
     *
     * @param str 原始字符串
     * @return 添加通配符后的字符串
     */
    public static String toLikePattern(String str) {
        if (isEmpty(str)) {
            return "%%";
        }
        return "%" + str.trim() + "%";
    }

    /**
     * 去除字符串两端空格，null 安全
     * Trim string, null-safe
     *
     * @param str 要处理的字符串
     * @return 处理后的字符串
     */
    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    /**
     * 首字母大写
     * Capitalize first letter
     *
     * @param str 原始字符串
     * @return 首字母大写的字符串
     */
    public static String capitalize(String str) {
        if (isEmpty(str)) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * 首字母小写
     * Uncapitalize first letter
     *
     * @param str 原始字符串
     * @return 首字母小写的字符串
     */
    public static String uncapitalize(String str) {
        if (isEmpty(str)) {
            return str;
        }
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    /**
     * 判断字符串是否为数字
     * Check if string is numeric
     *
     * @param str 要检查的字符串
     * @return true 是数字，false 不是数字
     */
    public static boolean isNumeric(String str) {
        if (isEmpty(str)) {
            return false;
        }
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 获取字符串的安全长度（null 返回 0）
     * Get safe length of string (return 0 for null)
     *
     * @param str 字符串
     * @return 长度
     */
    public static int length(String str) {
        return str == null ? 0 : str.length();
    }

    /**
     * 测试字符串工具类
     * Test String Utility
     */
    public static void main(String[] args) {
        // 测试 isEmpty
        System.out.println("isEmpty(null): " + isEmpty(null));
        System.out.println("isEmpty(\"\"): " + isEmpty(""));
        System.out.println("isEmpty(\"  \"): " + isEmpty("  "));
        System.out.println("isEmpty(\"abc\"): " + isEmpty("abc"));

        // 测试 toInt
        System.out.println("\ntoInt(\"123\"): " + toInt("123"));
        System.out.println("toInt(\"abc\", 0): " + toInt("abc", 0));

        // 测试 toLikePattern
        System.out.println("\ntoLikePattern(\"张三\"): " + toLikePattern("张三"));

        // 测试 isNumeric
        System.out.println("\nisNumeric(\"123\"): " + isNumeric("123"));
        System.out.println("isNumeric(\"abc\"): " + isNumeric("abc"));
    }
}
