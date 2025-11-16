package com.jzz.util;

import com.jzz.model.PageResult;
import com.jzz.model.Result;
import com.jzz.model.Student;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * JSON 工具类
 * JSON Utility Class
 *
 * 用于对象与 JSON 字符串之间的转换
 * Used for conversion between objects and JSON strings
 *
 * 注意：这是一个简化的实现，生产环境建议使用 Gson 或 Jackson 等成熟库
 * Note: This is a simplified implementation. For production, use mature libraries like Gson or Jackson
 *
 * @author Jzz
 * @version 1.0
 */
public class JsonUtil {

    /**
     * 将对象转换为 JSON 字符串
     * Convert object to JSON string
     *
     * @param obj 要转换的对象
     * @return JSON 字符串
     */
    public static String toJson(Object obj) {
        if (obj == null) {
            return "null";
        }

        // 处理 Result 类型
        if (obj instanceof Result) {
            return resultToJson((Result<?>) obj);
        }

        // 处理 PageResult 类型
        if (obj instanceof PageResult) {
            return pageResultToJson((PageResult<?>) obj);
        }

        // 处理 List 类型
        if (obj instanceof List) {
            return listToJson((List<?>) obj);
        }

        // 处理 Student 类型
        if (obj instanceof Student) {
            return studentToJson((Student) obj);
        }

        // 处理基本类型
        if (obj instanceof String) {
            return "\"" + escapeJson(obj.toString()) + "\"";
        }

        if (obj instanceof Number || obj instanceof Boolean) {
            return obj.toString();
        }

        // 默认使用反射处理
        return objectToJson(obj);
    }

    /**
     * 将 Result 对象转换为 JSON
     * Convert Result object to JSON
     */
    private static String resultToJson(Result<?> result) {
        StringBuilder json = new StringBuilder("{");
        json.append("\"code\":").append(result.getCode()).append(",");
        json.append("\"message\":\"").append(escapeJson(result.getMessage())).append("\"");

        if (result.getData() != null) {
            json.append(",\"data\":").append(toJson(result.getData()));
        }

        json.append("}");
        return json.toString();
    }

    /**
     * 将 PageResult 对象转换为 JSON
     * Convert PageResult object to JSON
     */
    private static String pageResultToJson(PageResult<?> pageResult) {
        StringBuilder json = new StringBuilder("{");
        json.append("\"currentPage\":").append(pageResult.getCurrentPage()).append(",");
        json.append("\"pageSize\":").append(pageResult.getPageSize()).append(",");
        json.append("\"totalCount\":").append(pageResult.getTotalCount()).append(",");
        json.append("\"totalPages\":").append(pageResult.getTotalPages()).append(",");
        json.append("\"data\":").append(listToJson(pageResult.getData()));
        json.append("}");
        return json.toString();
    }

    /**
     * 将 List 转换为 JSON 数组
     * Convert List to JSON array
     */
    private static String listToJson(List<?> list) {
        if (list == null || list.isEmpty()) {
            return "[]";
        }

        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                json.append(",");
            }
            json.append(toJson(list.get(i)));
        }
        json.append("]");
        return json.toString();
    }

    /**
     * 将 Student 对象转换为 JSON
     * Convert Student object to JSON
     */
    private static String studentToJson(Student student) {
        StringBuilder json = new StringBuilder("{");
        json.append("\"id\":").append(student.getId()).append(",");
        json.append("\"studentNo\":\"").append(escapeJson(student.getStudentNo())).append("\",");
        json.append("\"name\":\"").append(escapeJson(student.getName())).append("\",");
        json.append("\"gender\":").append(student.getGender()).append(",");
        json.append("\"genderText\":\"").append(student.getGenderText()).append("\",");
        json.append("\"age\":").append(student.getAge()).append(",");
        json.append("\"major\":\"").append(escapeJson(student.getMajor())).append("\",");
        json.append("\"className\":\"").append(escapeJson(student.getClassName())).append("\",");
        json.append("\"phone\":\"").append(escapeJson(student.getPhone())).append("\",");
        json.append("\"email\":\"").append(escapeJson(student.getEmail())).append("\",");
        json.append("\"enrollmentDate\":\"").append(student.getEnrollmentDate()).append("\",");
        json.append("\"status\":").append(student.getStatus()).append(",");
        json.append("\"statusText\":\"").append(student.getStatusText()).append("\"");
        json.append("}");
        return json.toString();
    }

    /**
     * 使用反射将任意对象转换为 JSON
     * Convert any object to JSON using reflection
     */
    private static String objectToJson(Object obj) {
        StringBuilder json = new StringBuilder("{");
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        boolean first = true;
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(obj);

                if (value == null) {
                    continue; // 跳过 null 值
                }

                if (!first) {
                    json.append(",");
                }
                first = false;

                json.append("\"").append(field.getName()).append("\":");

                if (value instanceof String) {
                    json.append("\"").append(escapeJson(value.toString())).append("\"");
                } else if (value instanceof Number || value instanceof Boolean) {
                    json.append(value);
                } else if (value instanceof Date || value instanceof Timestamp) {
                    json.append("\"").append(value.toString()).append("\"");
                } else {
                    json.append(toJson(value));
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        json.append("}");
        return json.toString();
    }

    /**
     * 转义 JSON 字符串中的特殊字符
     * Escape special characters in JSON string
     *
     * @param str 原始字符串
     * @return 转义后的字符串
     */
    private static String escapeJson(String str) {
        if (str == null) {
            return "";
        }
        return str.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    /**
     * 简单的 JSON 响应生成方法
     * Simple JSON response generation method
     *
     * @param success 是否成功
     * @param message 消息
     * @param data 数据
     * @return JSON 字符串
     */
    public static String buildJsonResponse(boolean success, String message, Object data) {
        if (success) {
            return toJson(Result.success(message, data));
        } else {
            return toJson(Result.error(message));
        }
    }

    /**
     * 测试 JSON 工具类
     * Test JSON Utility
     */
    public static void main(String[] args) {
        // 测试 Student 转 JSON
        Student student = new Student();
        student.setId(1);
        student.setStudentNo("2021001");
        student.setName("张三");
        student.setGender(1);
        student.setAge(20);
        student.setMajor("计算机科学与技术");
        student.setClassName("计科2101");
        student.setPhone("13800138001");
        student.setEmail("zhangsan@example.com");
        student.setStatus(1);

        System.out.println("Student JSON:");
        System.out.println(toJson(student));

        // 测试 Result 转 JSON
        Result<Student> result = Result.success("查询成功", student);
        System.out.println("\nResult JSON:");
        System.out.println(toJson(result));
    }
}
