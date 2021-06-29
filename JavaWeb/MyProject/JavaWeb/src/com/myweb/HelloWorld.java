package com.myweb;


import java.util.*;

/**
 * @author Ng
 */
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("hello");
        int[] a = new int[] {1,2,3,4};
        StringBuffer temp = new StringBuffer();
        temp.append(2);
        temp.append("heeee");
        System.out.println(temp);
        System.out.println(Integer.MAX_VALUE);
        //自动装箱
        Integer x = 123;
        Integer z  = 123;
        Integer y = new Integer("123");
        System.out.println(x.equals(y));
        System.out.println(x == z);
        Date nowTime = new Date();
        System.out.println(nowTime);
        long begin = System.currentTimeMillis();
        print();
        long end = System.currentTimeMillis();
        System.out.println(end-begin);
        int b = 1;
        try {
            if(b==0){
                throw new Exception("test");
            }
            System.out.println("exception test");
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("tttt");

        Collection c = new ArrayList();
        c.add(0);
        Iterator it = c.iterator();
        c.add(1);
        System.out.println(it.next());

        Deque<Integer> myStack = new ArrayDeque<>();
        myStack.push(1);
        myStack.push(2);
        myStack.pop();
    }
    public static void print(){
        for(int i=0;i<1000;i++){
//            System.out.println("i = " + i);
        }
    }
}
