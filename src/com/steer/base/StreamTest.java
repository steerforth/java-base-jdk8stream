package com.steer.base;

import java.util.*;
import java.util.function.Supplier;
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

    /**
     * 聚合求值
     */
    public static void reduce(){
        //拼接A B C D
            //无起始值
        String concatStr = Stream.of("A","B","C","D").reduce(String::concat).get();
            //有起始值
        String concatStr2 = Stream.of("A","B","C","D").reduce("",String::concat);
    }

    public static void limitAndSkip(){
        List<String> list = Arrays.asList("a","b","c","d","e");
        //limit限制结果为前4个   skip过滤掉前2个
        List<String> newList = list.stream().map(String::toUpperCase).limit(4).skip(2).collect(Collectors.toList());
    }

    public static void sort(){
        List<String> list = Arrays.asList("a","b","c","d","e");

        List<String> newList = list.stream().map(String::toUpperCase).limit(4).skip(2).sorted((a,b)-> {
            return b.compareTo(a);
        }).collect(Collectors.toList());
    }

    public static void distinct(){
        List<String> list = Arrays.asList("a","a","b","b","e");
        List<String> distinctList =list.stream().map(String::toUpperCase).distinct().collect(Collectors.toList());
    }

    public static void match(){
        List<Person> personList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Person person = new Person("name"+i,i);
            personList.add(person);
        }

        boolean a = personList.stream().allMatch(p->p.getNo() >3);
        boolean b = personList.stream().anyMatch(p->p.getNo() >3);
        boolean c = personList.stream().noneMatch(p->p.getNo() >3);

    }
    /**
     *supplier一般用于生成随机数
     */
    public static void supplier(){
        Random seed = new Random();
        Supplier<Integer> random = seed::nextInt;
        Stream.generate(random).limit(10).forEach(System.out::println);

        //自定义Supplier
        Stream.generate(PersonSupplier::new).limit(10).forEach(p->System.out.println(p.get().getName()+":"+p.get().getNo()));
    }

    /**
     *iterate
     */
    public static void iterate(){
        //等差数列
        Stream.iterate(0,n->n+3).limit(10).forEach(x->System.out.println(x+" "));
    }

    /**
     *groupingBy/partitioningBy
     */
    public static void group(){
        List<Person> personList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Person person = new Person("name"+i,i);
            personList.add(person);
        }
        //返回以person的no属性作为key，  List<person>作为value  进行分组
        Map<Integer,List<Person>> map = personList.stream().collect(Collectors.groupingBy(Person::getNo));
        //key为false和true, value为no>3和no<=3的2组List<person>
        Map<Boolean,List<Person>> map2 = personList.stream().collect(Collectors.partitioningBy(p->p.getNo()>3));
        List<Person> personList2 = map2.get(true);
    }

    /**
     * 并行流
     * 底层使用JoinFork线程池
     * 执行任务数等于CPU核数，以达到最大利用CPU资源
     */
    public static void parallelStream(){
        List<Person> personList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Person person = new Person("name"+i,i);
            personList.add(person);
        }
        //按顺序
        personList.parallelStream().forEachOrdered(p->{
            System.out.println(p.getName());
        });
    }

    public static String collectJoin(){
        List<String> data = new ArrayList<>();
        data.add("1");
        data.add("11");
        data.add("4");
        data.add("5");
        data.add("7");
        return data.stream().collect(Collectors.joining(","));
    }

    public static void main(String[] args) {
//        group();
    }

}
