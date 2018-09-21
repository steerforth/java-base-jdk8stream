package com.steer.base;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @Program: java-base-jdk8stream
 * @Author: Steerforth
 * @Description: jdk8流
 * @Date: 2018-09-21 10:50
 */
public class StreamTest {

    /**
     * stream的构造
     * Stream.of()
     * .stream()
     */
    public static void structureStream(){
        //1.Individual values
        Stream stream = Stream.of("a","b","c");
        //2.Arrays
        String[] strArray = new String[]{"a","b","c"};
        stream = Stream.of(strArray);
        stream = Arrays.stream(strArray);
        //3.Collections
        List<String> list = Arrays.asList(strArray);
        stream = list.stream();
    }

    /**
     * 转换流为其他数据结构
     * 转为数组：toArray()
     * 转为Collection: collect()
     */
    public static void transformStream(){
        Stream stream = Stream.of("a","b","c");
        //1.Array
//        String[] strArray1 = (String[]) stream.toArray(String[]::new);
        //2.Collection
//        List<String> list1 = (List<String>) stream.collect(Collectors.toList());
//        List<String> list2 = (List<String>) stream.collect(Collectors.toCollection(ArrayList::new));
//        Stack stack = (Stack) stream.collect(Collectors.toCollection(Stack::new));
        //3.String
        String str = stream.collect(Collectors.joining()).toString();

    }


    /**
     * 构造数值流
     */
    public static void structureNumStream(){
        IntStream.of(new int[]{1,2,3}).forEach(System.out::println);
        IntStream.range(5,8).forEach(System.out::println);
        IntStream.rangeClosed(14,18).forEach(System.out::println);
    }

    /**
     * 转化大写
     */
    public static void toUpper(){
        List<String> list = Arrays.asList("a","b","c");
        List<String> upperList = list.stream().map(String::toUpperCase).collect(Collectors.toList());
    }

    /**
     * 转化为平方数
     */
    public static void square(){
        List<Integer> list = Arrays.asList(1,2,3);
        List<Integer> squareList = list.stream().map(n->n*n).collect(Collectors.toList());
    }


    /**
     * flat  把层级结构扁平化
     */
    public static void flatMap(){
        Stream<List<Integer>> inputStream = Stream.of(Arrays.asList(1),Arrays.asList(2,3),Arrays.asList(4,5,6));
        Stream<Integer> outputStream = inputStream.flatMap(childList->childList.stream());
        List<Integer> flatList = outputStream.collect(Collectors.toList());
    }

    /**
     * filter 过滤元素
     */
    public static void filter(){
        Integer[] sixNums = {1,2,3,4,5,6};
        Integer[] events = Stream.of(sixNums).filter(n->n%2==0).toArray(Integer[]::new);
    }


    /**
     * peek 会返回一个新的stream可继续操作
     */
    public static void peek(){
       List<String> a =  Stream.of("one","two","three","four").filter(e->e.length()>3).peek(e-> System.out.println("filter value:"+e)).map(String::toUpperCase).peek(e-> System.out.println("mapped value:"+e)).collect(Collectors.toList());
    }

    public static void optional(){
        String strA = "abcd",strB = null;
        System.out.println(strA);
        System.out.println("");
        System.out.println(strB);
        //old
        if (strA != null){
            System.out.println(strA);
        }
        //new
        Optional.ofNullable(strA).ifPresent(System.out::println);

        //old
        System.out.println(strB == null ? -1:strB.length());
        //new
        System.out.println(Optional.ofNullable(strB).map(String::length).orElse(-1));

    }

    public static void main(String[] args) {
        optional();
    }
}
