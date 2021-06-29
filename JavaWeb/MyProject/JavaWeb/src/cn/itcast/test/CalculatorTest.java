package cn.itcast.test;

import cn.itcast.junit.Calculator;
import org.junit.Assert;
import org.junit.Test;

public class CalculatorTest {
    @Test
    public void testAdd(){
        Calculator c = new Calculator();
        int result = c.add(1,3);
        System.out.println(result);
        Assert.assertEquals(4,result);
    }
    @Test
    public void testSub(){
        Calculator c = new Calculator();
        int result = c.sub(3,2);
        System.out.println(result);
        Assert.assertEquals(1,result);
        for (int i=0;i<10;i++){
            System.out.println((2==1));
        }
    }
}
