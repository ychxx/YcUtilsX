package com.yc.ycutilsx;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yc.yclibx.comment.YcEmpty;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class TestJava {
    public void funt(int[] a, List<Integer> b) {

    }

    public void funt2() {
        funt(new int[]{1, 2, 3, 4}, Arrays.asList(1, 2, 3));
    }

    public static class Bean {
        String name;
        String code;

        @Override
        public String toString() {
            return "Bean{" +
                    "name='" + name + '\'' +
                    ", code='" + code + '\'' +
                    '}';
        }
    }

    class Json {

        /**
         * code : 500
         * data :
         * exceptionClazz :
         * message : 服务器异常
         * success : false
         */

        private int code;
        private String data;
        private String exceptionClazz;
        private String message;
        private boolean success;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getExceptionClazz() {
            return exceptionClazz;
        }

        public void setExceptionClazz(String exceptionClazz) {
            this.exceptionClazz = exceptionClazz;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        @Override
        public String toString() {
            return "Json{" +
                    "message='" + message + '\'' +
                    '}';
        }
    }

    class Json2<T> {

        /**
         * code : 500
         * data :
         * exceptionClazz :
         * message : 服务器异常
         * success : false
         */

        private int code;
        private T data;
        private String message;
        private boolean success;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }


        @Override
        public String toString() {
            return "Json2{" +
                    "message='" + message + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) {
//        List<String> data = new ArrayList<>();
//        data.add("1");
//        System.out.println("1." + YcEmpty.getNoEmptyString(data, 0));
//        System.out.println("1." + YcEmpty.getNoEmptyString(data, 1));
//        System.out.println("1." + YcEmpty.getNoEmptyString(data, 1, "default"));
//        System.out.println("1." + YcEmpty.getNoEmptyString(null, 1));
//        System.out.println("1." + YcEmpty.getNoEmptyString(null, 1, "default"));
//
//        List<Bean> data2 = new ArrayList<>();
//        data2.add(new Bean());
//        System.out.println("2." + YcEmpty.getNoEmptyString(data2, 0));
//        System.out.println("2." + YcEmpty.getNoEmptyString(data2, 1));
//        System.out.println("2." + YcEmpty.getNoEmptyT(Bean.class, data2, 1).toString());
//        String jsonData = "{ \"code\":500, \"data\":\"\", \"exceptionClazz\":\"\", \"message\":\"\\u670D\\u52A1\\u5668\\u5F02\\u5E38\", \"success\":false }";
//        Json json1 = new Gson().fromJson(jsonData, Json.class);
//        System.out.println(json1.toString());
//        Type type = new TypeToken<Json2<String>>() {
//        }.getType();
//        Json2<String> json2 = new Gson().fromJson(jsonData, type);
//        System.out.println(json2.toString());
//
//
//        String jsonData2 = "{\"code\": 500,\"exceptionClazz\": \"\",\"message\": \"服务器异常\",\"success\": false}";
//
//        Type type2 = new TypeToken<Json2<Integer>>() {
//        }.getType();
//        Json2<Integer> json3 = new Gson().fromJson(jsonData2, type2);
//        System.out.println(json3.toString());
    }

}
