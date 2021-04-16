## Java学习笔记

### 第一章 Java概述

+ Java编程规范：

  包的名称由一个小写字母序列组成。
  类的名称由大写字母开头,其他字母都由小写的单词组成。
  类的实例的名称由一个小写字母开头,后面的单词由大写字母开头。
  常量的名称都大写,并且指出完整含义。
  参数的名称无其他具体规定。
  数组的命名使用“类型[] 数组名”的形式

+ 如果需要用 java 命令直接运行一个 Java 类,这个 Java 类必须包含 main 方法,这个 main 方法必须使用 public 和 static 来修饰,必须使用 void 声明该方法的返回值,而且该方法的参数类型只能是一个字符串数组,而不能是其他形式的参数。对于这个 main 方法而言,前面的 public 和 static 修饰符的位置可以互换,但其他部分则是固定的。

### 第二章 Java程序设计基础

+ final关键字声明常量，在定义常量时就需要对该常量进行初始化。

+ public static 修饰全局变量，即不需要创建对象也访问的变量。

+ 变量赋值，初始化变量有两种方式:一种是声明时直接赋值,一种是先声明、后赋值。

  | 名称                | 修饰           | 访问                       | 生命周期                                                     |
  | ------------------- | :------------- | -------------------------- | ------------------------------------------------------------ |
  | 全局变量(实例变量） | 无static修饰   | 对象名.变量名              | 只要对象被当作引用,实例变量就将存在                          |
  | 静态变量(类变量)    | 用 static 修饰 | 类名.变量名或对象名.变量名 | 其生命周期取决于类的生命周期。类被垃圾回收机制彻底回收时才会被销毁 |
  | 局部变量            | 无修饰         | 作用域中直接使用           | 声明变量的作用域中                                           |


### 第三章 流程控制语句

### 第四章 Java字符串的处理

+ String 字符串转整型 int 有以下两种方式:

  **Integer.parseInt(str)**
  Integer.valueOf(str).intValue()

+ 整型 int 转 String 字符串类型有以下 3 种方法:

  String s = String.valueOf(i);

  String s = Integer.toString(i);

  String s = "" + i;

  使用第三种方法相对第一第二种耗时比较大。
  
+ concat()实现了将一个字符串连接到另一个字符串的后面。eg: str1 = str1.concat(str2)

+ length()获取字符串长度。eg：str1.length()，注：**数组length是属性**

+ toLowerCase()、toUPperCase()，分别返回字符串所有字母的大小写。

+ trim()去除字符串中的首尾空格，eg:str1.trim();

+ substring()，截取（提取）子字符串。eg：str1.substring(beginIndex,endIndex(可缺省)))

+ split()分割字符串，split() 方法可以按指定的分割符对目标字符串进行分割,分割后的内容存放在**字符串数组中。**

  ```java
  str.split(String sign)
  str.split(String sign,int limit)
  String[] strs = str.split(",");
  //str 为需要分割的目标字符串。
  //sign 为指定的分割符,可以是任意字符串。
  //limit 表示分割后生成的字符串的限制个数,如果不指定,则表示不限制,直到将整个目标字符串完全分割为止。
  ```

  **“.”和“|”都是转义字符,必须得加“\\”。**

+ String 类提供了 3 种字符串替换方法replace()、replaceFirst()、replaceAll()。

  replace() 方法用于将目标字符串中的指定字符(串)替换成新的字符(串)：

  ```java
  str1.replace(String oldChar, String newChar)
  ```

  replaceFirst()替换第一个匹配某正则表达式的第一个子字符串。

  replaceAll() 方法用于将目标字符串中匹配某正则表达式的所有子字符串替换成新的字符串。**正则表达式目前不太了解**。

+ equals()，返回两个字符是否相同。**没有重载==**

+ equalsIgnoreCase()，比较时不区分大小写。

+ ==在java中没有被String重载，所以==只返回两个对象引用看它们是否引用相同的实例。

+ compareTo()，str.compareTo(String otherstr);如果按字典顺序 str 位于 otherster 参数之前,比较结果为一个负整数;如果 str 位于 otherstr 之后,比较结果为一个正整数;如果两个字符串相等,则结果为 0。

+ “”是一个长度为 0 且占内存的空字符串,在内存中分配一个空间,可以使用 Object 对象中的方法。**null 是空引用,表示一个对象的值,没有分配内存**

+ indexOf() 方法用于返回字符(串)在指定字符串中**首次**出现的索引位置,如果能找到,则返回索引值,否则返回 -1。

+ lastIndexOf() 方法用于返回字符(串)在指定字符串中**最后一次**出现的索引位置,如果能找到则返回索引值,否则返回 -1。

+ charAt() 方法可以在字符串内根据指定的索引查找字符，**String 类没有重载[]**

+ String、StringBuffer 和 StringBuilderl类的区别：

  String 是 Java 中基础且重要的类,被声明为 final class,是不可变字符串。因为它的不可变性,所以拼接字符串时候会产生很多无用的中间对象,如果频繁的进行这样的操作对性能有所影响。

  StringBuffer 就是为了解决大量拼接字符串时产生很多中间对象问题而提供的一个类。它提供了 append 和 add 方法,可以将字符串添加到已有序列的末尾或指定位置,它的本质是一个线程安全的可修改的字符序列。

  在很多情况下我们的字符串拼接操作不需要线程安全,所以 StringBuilder 登场了。StringBuilder 是 JDK1.5 发布的,它和StringBuffer 本质上没什么区别,就是去掉了保证线程安全的那部分,减少了开销。

+ StringBuffer类

  初始化：

  ```java
  StringBuffer str1 = new StringBuffer(); // 定义一个空的字符串缓冲区,含有 16 个字符的容量
  StringBuffer str2 = new StringBuffer(10);   // 定义一个含有 10 个字符容量的字符串缓冲区
  StringBuffer str3 = new StringBuffer("青春无悔"); // 定义一个含有(16+4)的字符串缓冲区,"青春无悔"为 4 个字符
  ```

  替换字符： StringBuffer 对象.setCharAt(int index, char ch);

  ```java
  StringBuffer sb = new StringBuffer("hello");
  sb.setCharAt(1,'E');
  ```

  反转字符：StringBuffer 对象.reverse();

  删除字符串：StringBuffer 对象.deleteCharAt(int index);   StringBuffer 对象.delete(int start,int end);
  
+ 正则表达式(Regular Expression)

  简单理解，用于字符串校验匹配。

  **暂时了解，将来有用时再回细看**

### 第五章：Java数字和日期处理

#### Java Math类常用方法

+ 