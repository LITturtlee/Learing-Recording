# Java并发编程

### 创建和运行线程

+ 一般采用方式二进行编程，即将任务和线程分开。**注：若cpu是多核，下面几种方式创建的线程可能在不同的线程序列上，所以cpu执行时可能是并行执行。**

  ``` java
  //方式一
  Thread t = new Thread("t1"){
      @Override
      public void run() {
          log.debug("Thread running");
      }
  };
  t.start();
  //方式二
  Runnable runnable = new Runnable() {
      @Override
      public void run() {
          log.debug("Thread running");
      }
  };
  Thread t2 = new Thread(runnable,"t2");
  t2.start();
  //方式三
  Runnable runnable1 = () -> {
      log.debug("Thread running");
  };
  Thread t3 = new Thread(runnable1,"t3");
  t3.start();
  //方式四 FutureTask配合Callable
  ```
  
+ 多线程并发执行，一般交替执行，谁先谁后由操作系统执行。是不是多核心并行也是无法控制的。

### 简单原理

#### 栈与栈帧

+ 每个栈由多个栈帧（Frame）组成，对应着每次方法调用时所占用的内存
+ 每个线程只能有一个活动栈帧，对应着当前正在执行的那个方法

#### 线程上下文切换

因为以下一些原因导致 cpu 不再执行当前的线程，转而执行另一个线程的代码

+ 线程的 cpu 时间片用完
+ 垃圾回收
+ 有更高优先级的线程需要运行
+ 线程自己调用了 sleep、yield、wait、join、park、synchronized、lock 等方法

当 Context Switch 发生时，需要由操作系统保存当前线程的状态，并恢复另一个线程的状态，Java 中对应的概念
就是程序计数器（Program Counter Register），它的作用是记住下一条 jvm 指令的执行地址，是线程私有的

+ 状态包括程序计数器、虚拟机栈中每个栈帧的信息，如局部变量、操作数栈、返回地址等
+ Context Switch 频繁发生会影响性能

### 常见方法

![image-20210708112654516](F:\CodeAndNote\Learing-Recording\java并发编程\Java并发编程.assets\image-20210708112654516.png)

![image-20210708112717536](F:\CodeAndNote\Learing-Recording\java并发编程\Java并发编程.assets\image-20210708112717536.png)

### sleep与yield

#### sleep

1. 调用 sleep 会让当前线程从 Running 进入 Timed Waiting 状态（阻塞），**sleep是不占用CPU的**
2. 其它线程可以使用 interrupt 方法打断正在睡眠的线程，这时 sleep 方法会抛出 InterruptedException，**注：此处并不是唤醒**
3. 睡眠结束后的线程未必会立刻得到执行
4. 建议用 TimeUnit 的 sleep 代替 Thread 的 sleep 来获得更好的可读性

#### sleep的应用

+ sleep可以防止CPU占用超过100%，同样能实现该效果可以用wait或条件变量达到类似的效果。
+ 不同的是：**后两种都要加锁**，并且需要相应的唤醒操作，一般适用于要进行同步的场景，sleep适用于无需锁同步的场景。

#### yield

1. 调用 yield 会让当前线程从 Running 进入 Runnable 就绪状态，然后调度执行其它线程
2. 具体的实现依赖于操作系统的任务调度器

### join方法

+ 作用：等待线程结束，用 join，加在 t1.start() 之后即可。**线程的同步应用**

  `join()、join(long n)后者是有时效的等待`

### interrupt方法详解

+ 打断 sleep，wait，join 的线程这几个方法都会让线程进入阻塞状态，打断 sleep 、wait、join的线程, 会清空打断状态。即`isInterrupted()`仍为`false`。
+ 打断普通线程，打断不会立即中断，需要用打断标记由线程自己判断是否要停止线程。
+ `isInterrupted()与interrupted()`两者的区别是前者不清楚标志位，后者会清除标准位。

#### interrupt的——两阶段终止模式

```java
class TPTInterrupt {
	private Thread thread;
	public void start(){
		thread = new Thread(() -> {
		while(true) {
			Thread current = Thread.currentThread();
			if(current.isInterrupted()) {
				log.debug("料理后事");
				break;
			}
			try {
			Thread.sleep(1000);
			log.debug("将结果保存");
			} catch (InterruptedException e) {
                //这里相当于是重置打断标记位
				current.interrupt();
				}
			// 执行监控操作
			}
		},"监控线程");
		thread.start();
	}
	public void stop() {
	thread.interrupt();
	}
}
```

#### 打断park线程

+ `LockSupport.park()`会阻塞当前线程，`interrupt()`可以打断park线程。并且打断会置线程打断状态为`true`。**注：若打断且标记为真，线程再`LockSupport.park()`将无法再阻塞线程，必须要打断标记为假，这个方法才用用。可以用`interrupted()`**

#### 不推荐的停止线程的方法

+ `stop()`：停止线程运行——>替待：两阶段方式

+ `suspend()`:挂起(暂停)线程运行——>替代：之后会有更好的办法

+ `resume()`：恢复线程运行

  **以上方法已过时，容易破坏同步代码块，造成线程死锁**

### 守护线程 

默认情况下，Java 进程需要等待所有线程都运行结束，才会结束。有一种特殊的线程叫做守护线程，只要其它非守护线程运行结束了，即使守护线程的代码没有执行完，也会强制结束。

```java
// 设置该线程为守护线程
t1.setDaemon(true);
t1.start();
```

#### 典型的守护进程

+ JVM中的垃圾回收线程
+ Tomcat中的Acceptor和Poller线程，所以 Tomcat 接收到 shutdown 命令后，不会等待它们处理完当前请求

### 线程的状态

#### 五种状态——操作系统

![image-20210709161407170](F:\CodeAndNote\Learing-Recording\java并发编程\Java并发编程.assets\image-20210709161407170.png)

![在这里插入图片描述](F:\CodeAndNote\Learing-Recording\java并发编程\Java并发编程.assets\20190323112736522.png)操作系统进程的五种状态

解决内存紧张的状态转换图

![在这里插入图片描述](F:\CodeAndNote\Learing-Recording\java并发编程\Java并发编程.assets\20190323112812132.png)

#### 六种状态——Java API

根据Thread.State枚举，分为六种状态

![image-20210709161732281](F:\CodeAndNote\Learing-Recording\java并发编程\Java并发编程.assets\image-20210709161732281.png)

+ NEW，和初始状态一样，相当于new了没有调用start。

+ RUNNABLE，涵盖了操作系统中的就绪、运行和阻塞。

+ Java中的阻塞态(区别于操作系统的阻塞状态，但是同样不会分到时间片)

  ```
  1. BLOCKED，          eg:拿不到锁
  2. WAITING,           eg:join,notify
  3. TIMED_WAITING,     eg:sleep
  ```

+ TERMINATED,终止状态。

### 共享模型——管程

+ 临界区:一段代码块内如果存在对共享资源的多线程读写操作，称这段代码块为临界区。

+ 竟态条件:多个线程在临界区内执行，由于代码的执行序列不同而导致结果无法预测，称 之为发生了竞态条件

+ 为了解决临界区的竟态台条件发生，有多种手段：

  + 阻塞式:synchronized，Lock
  + 非阻塞式:原子变量

+ 注意

  虽然 java 中互斥和同步都可以采用 synchronized 关键字来完成，但它们还是有区别的：

  + 互斥是保证临界区的竞态条件发生，同一时刻只能有一个线程执行临界区代码
  + 同步是由于线程执行的先后、顺序不同、需要一个线程等待其它线程运行到某个点

#### 解决方案——synchronized

以互斥的方式解决临界区竞态问题。

```java
//语法,由线程对对象加锁
synchronized(对象) //线程1，  线程2(blocked){
//临界区
}
```

思考：

+ 如果把 synchronized(obj) 放在 for 循环的外面，如何理解？-- 原子性
+ 如果 t1 synchronized(obj1) 而 t2 synchronized(obj2) 会怎样运作？-- 锁对象
+ 如果 t1 synchronized(obj) 而 t2 没有加会怎么样？如何理解？-- 锁对象

方法上的synchronized

**注：类锁和对象锁不会互斥**，我们知道，类的对象实例可以有很多个，但是每个类只有一个class对象，所以不同对象实例的对象锁是互不干扰的，但是每个类只有一个类锁。但是有一点必须注意的是，其实类锁只是一个概念上的东西，并不是真实存在的，它只是用来帮助我们理解锁定实例方法和静态方法的区别的

```java
/*

 *  非静态方法:

 *  给对象加锁(可以理解为给这个对象的内存上锁,注意 只是这块内存,其他同类对象都会有各自的内存锁),这时候

 *  在其他一个以上线程中执行该对象的这个同步方法(注意:是该对象)就会产生互斥

 

 *  静态方法: 

 *  相当于在类上加锁(*.class 位于代码区,静态方法位于静态区域,这个类产生的对象公用这个静态方法,所以这块

 *  内存，N个对象来竞争), 这时候,只要是这个类产生的对象,在调用这个静态方法时都会产生互斥

 */

class Test{
	public synchronized void test() {
	}
}
//等价于,锁住了对象实例，对象锁
class Test{
	public void test() {
		synchronized(this) {
		}
	}
}
```



```java
class Test{
	public synchronized static void test() {
	}
}
//等价于，类锁
class Test{
	public static void test() {
		synchronized(Test.class) {
		}
	}
}
```

#### 变量的线程安全分析

+ 成员变量与静态变量
  + 如果不存在线程之间共享，则不存在线程安全问题。
  + 如果存在共享问题，则需要考虑是否存在读写操作。
+ 局部变量
  + 局部变量是线程安全的
  + 局部变量引用的对象则未必(取决于是否逃逸)
+ 常见线程安全类
  + String、Integer、StringBuffer、Random、Vector、Hashtable、java.util.concurrent包下的类
  + 注：线程安全类中的每一个方法能保证原子性，但是多方法组合就能保证线程安全了。
+ 不可变类
  + String、Integer 等都是不可变类，因为其内部的状态不可以改变，因此它们的方法都是线程安全的

```shell
for /L %n in (1,1,10) do java -cp ".;F:\MavenRepo\ch\qos\logback\logback-classic\1.2.3\logback-classic-1.2.3.jar;F:\MavenRepo\ch\qos\logback\logback-core\1.2.3\logback-core-1.2.3.jar;F:\MavenRepo\org\slf4j\slf4j-api\1.7.30\slf4j-api-1.7.30.jar" com.wbw.test.Practice02
```

### Monitor

#### Java对象头

```
Mark Word: 
Klass Word: 指针，指向该对象从属的类对象， 简单理解通过指针找到类对象
|--------------------------------------------------------------|
| Object Header (64 bits) 									   |
|------------------------------------|-------------------------|
| Mark Word (32 bits) 				 | Klass Word (32 bits)    |
|------------------------------------|-------------------------|


用synchronized上锁的话会从Normal状态跳到Heavyweight Locked状态，前30位指向JVM中的Monitor
会将一个对象与JVM中的一个Monitor进行关联
|-------------------------------------------------------|--------------------|
| Mark Word (32 bits) 									| 			   State |
|-------------------------------------------------------|--------------------|
| hashcode:25 		| age:4 | biased_lock:0 | 01 		|			  Normal |
|-------------------------------------------------------|--------------------|
| thread:23 | epoch:2 | age:4 | biased_lock:1 | 01 		|			  Biased |
|-------------------------------------------------------|--------------------|
| ptr_to_lock_record:30 | 00 							| Lightweight Locked |
|-------------------------------------------------------|--------------------|
| ptr_to_heavyweight_monitor:30 | 10 					| Heavyweight Locked |
|-------------------------------------------------------|--------------------|
| | 11 													|      Marked for GC |
|-------------------------------------------------------|--------------------|
```

![image-20210715143255021](F:\CodeAndNote\Learing-Recording\java并发编程\Java并发编程.assets\image-20210715143255021.png)

+ 一开始Monitor中的Owner为null
+ 当Thread2执行synchronized(obj)就会将Monitor的所有者Owner置为Thread2,Monitor中只能有一个Owner
+ 在Thread2上锁的过程中，如果Thread3,Thread4,Thread5也来执行synchronized(obj)就会进入EntryList，进入阻塞态。
+ Thread2执行完后将唤醒队列中的线程，竞争可公平也可不公平。
+ WaitSet在wait-notify再细讲。

**注**： 

+ synchronized必须进入同一给对象的monitor才有作用
+ 不加synchronized的对象不会关联monitor

#### synchronized的优化

+ 轻量级锁
  + 用于优化多线程无竞争下的优化
  + 调用synchronized的方法，会在对应线程的对应栈帧中创建锁记录，交换锁记录与对象的Mark Word，以代替Monitor。
  + 若发生竞争则会进行锁膨胀          
+ 自旋优化
  + 重量级锁竞争的时候，还可以使用自旋来进行优化，如果当前线程自旋成功（即这时候持锁线程已经退出了同步块，释放了锁），这时当前线程就可以避免阻塞。
+ 偏向锁
  + 用于优化轻量级锁无竞争时的操作。
  + 偏向锁是默认开启的(jvm可设置偏向锁延迟、偏向锁是否启用)。
  + 一旦使用了hashcode，对象的偏向锁将被紧用，因为偏向锁要存线程id，但是hashcode也要用很多位，二者不能同时存在。
  + 发生其他线程使用，则撤销偏向锁，改为使用轻量锁。
  + wait/notify会撤销偏向锁和轻量级锁。
  + 重偏向，当偏向被撤销多次(20)时，jvm会进行批量重偏向。
  + 批量撤销，如果撤销偏向锁超过(40)次后，jvm整个类对象都变得不可偏向，新对象也是不可偏向的。
  + 锁消除，JIT会进行优化操作，对无用的锁进行消除。

#### wait-notify 

+ 原理

  ![image-20210719195711073](F:\CodeAndNote\Learing-Recording\java并发编程\Java并发编程.assets\image-20210719195711073.png)

  join也会使得调用线程进入wait状态，区别是join是不用锁的。

+ wait-notify 的三个基本API介绍

  ```java
  obj.wait(); //让进入 object 监视器的线程到 waitSet 等待
  obj.wait(long timeout) //有时限的等待
  obj.notify(); //在 object 上正在 waitSet 等待的线程中挑一个唤醒
  obj.notifyAll(); //让 object 上正在 waitSet 等待的线程全部唤醒
  ```

  **注：**obj被锁对象，所有wait线程将进入该obj所分配的monitor的waitSet中。

+ wait线程也是可以由interrupt打断并唤醒的。

#### wait notify的正确打开方式

+ wait(long timeout)与sleep(long n)的区别

  + sleep是Thread的方法，而wait是Object方法
  + sleep不需要强制和synchronized配合使用，但wait需要和synchronized一起用
  + sleep在睡眠的同时，不会释放对象的锁，但wait在等待的时候需要释放对象锁
  + 注：他们的状态都是TIME_WAITING，而不带参数的wait会使线程变成WAITING状态。

+ 使用notifyAll时，若存在多个wait线程被唤醒，但并不是所有线程都满足条件而退出，这时可以用while进行自旋再重新进入wait状态。

  ```
   synchronized(lock){
   	while(条件不成立){
   		lock.wait();
   	}
   	//干活
   }
   //另一个线程
   synchronized(lock){
   	lock.notifyAll();
   }
  ```

#### join的原理

```java
//Thread类中
public final void join() throws InterruptedException {
    join(0);
}


public final synchronized void join(long millis) throws InterruptedException {
    long base = System.currentTimeMillis();  //获取当前时间
    long now = 0;

    if (millis < 0) {
        throw new IllegalArgumentException("timeout value is negative");
    }

    if (millis == 0) {    //这个分支是无限期等待直到b线程结束
        while (isAlive()) {
            wait(0);
        }
    } else {    //这个分支是等待固定时间，如果b没结束，那么就不等待了。
        while (isAlive()) {
            long delay = millis - now;
            if (delay <= 0) {
                break;
            }
            wait(delay);
            now = System.currentTimeMillis() - base;
        }
    }
}
```

注意这个`wait()`方法是Object类中的方法，再来看sychronized的是谁：

```java
public final synchronized void join(long millis) throws InterruptedException { ... }
```

成员方法加了synchronized说明是`synchronized(this)`，this是谁啊？this就是threadA子线程对象本身。也就是说，主线程持有了threadA这个对象的锁。

+ 总结:首先join() 是一个synchronized方法， 里面调用了wait()，这个过程的目的是让持有这个同步锁的线程进入等待，那么谁持有了这个同步锁呢？答案是主线程，因为主线程调用了threadA.join()方法，相当于在threadA.join()代码这块写了一个同步代码块，谁去执行了这段代码呢，是主线程，所以主线程被wait()了。然后在子线程threadA执行完毕之后，JVM会调用lock.notify_all(thread);唤醒持有threadA这个对象锁的线程，也就是主线程，会继续执行。

#### 几种模式上的应用

+ 同步模型:保护性暂停
+ 异步模型:生产者/消费者(RabbitMQ是进程间消息队列)

#### park于Unpark

+ 它们是 LockSupport 类中的方法

  ```java
  // 暂停当前线程
  LockSupport.park();
  // 恢复某个线程的运行
  LockSupport.unpark(暂停线程对象)
  ```

  **注：**如果先unpark，后park，则park不会停下来。

+ 特点:

  + wait，notify 和 notifyAll 必须配合 Object Monitor 一起使用，而 park，unpark 不必
  + park & unpark 是以线程为单位来【阻塞】和【唤醒】线程，而 notify 只能随机唤醒一个等待线程，notifyAll是唤醒所有等待线程，就不那么【精确】
  + park & unpark 可以先 unpark，而 wait & notify 不能先 notify

#### 多把锁

+ 互不相干的业务可将锁的粒度细分，从而可以增强并发度
+ 但是会存在潜在隐患，当一个线程同时需要获得多把锁时，就容易发生死锁。

#### 线程的活跃性

+ 死锁:

+ 活锁:活锁出现在两个线程互相改变对方的结束条件，最后谁也无法结束
+ 饥饿：一个线程由于优先级太低，始终得不到 CPU 调度执行，也不能够结束

解决方法：可用ReentrantLock解决

#### ReentrantLock（JUC下的一个类)

+ 特点：

  + 可中断，synchronized是不能被interrupt的
  + 可设置超时时间
  + 可设置公平锁
  + 支持多个条件变量(支持多个waitSet而synchronized只有一个waitSet)

+ 基本语法

  ```java
  // 获取锁
  reentrantLock.lock();
  try {
  // 临界区
  } finally {
  // 释放锁
  reentrantLock.unlock();
  }
  
  class YouObj extends ReentrantLock
  ```

+ 可打断：`lock.lockInterruptibly();`

+ 锁超时:`lock.trylock()`或者`lock.trylock(1,TimeUnit.SECONDS)`带时间的`trylock`是可以打断的。

+ 公平锁:

  + ReentrantLock是默认不公平的

+ 条件变量 

  ```java
  Condition condition1 = lock.newConditon();
  Condition condition2 = lock.newConditon();
  
  lock.lock();
  //await 前需要获得锁
  //await 执行后，会释放锁，进入 conditionObject 等待
  //await 的线程被唤醒（或打断、或超时）取重新竞争 lock 锁
  //竞争 lock 锁成功后，从 await 后继续执行
  condition1.await();
  
  condition1.signalAll();
  condition2.signal();
  ```

#### 同步控制的方式

+ wait - notify
+ park - unpark
+ condition: await - signal

### 共享模型之内存

####  Java内存模型

JMM 即 Java Memory Model，它定义了主存、工作内存抽象概念，底层对应着 CPU 寄存器、缓存、硬件内存、CPU 指令优化等。
JMM 体现在以下几个方面

+ 原子性 - 保证指令不会受到线程上下文切换的影响
+ 可见性 - 保证指令不会受 cpu 缓存的影响
+ 有序性 - 保证指令不会受 cpu 指令并行优化的影响

#### 可见性

+ 可见性问题:因JIT即时编译的存在,优化了线程读取主存中的数据(通过缓存),从而会存在主存内存中的变量不可见.
+ 解决方案:在共享变量上添加关键字`volatile`,它可以用来修饰成员变量和静态成员变量，他可以避免线程从自己的工作缓存中查找变量的值，必须到主存中获取 它的值，线程操作 volatile 变量都是直接操作主存
+ `synchronized`临界区中的共享变量也会保证可见性,但是`volatile`更加轻量一些,不用创建monitor.**线程加锁时,会先清空工作内存,在主存中拷贝新变量的副本到工作内存中,执行完代码后将共享变量的值刷新到主内存中.**
+ **注**:`volatile`是不能解决原子性问题.而`synchronized`即能保证原子性也能保证可见性.

#### 有序性

+ 产生原因是JVM为了优化程序,会在改变一些指令的顺序(不改变程序的结果),但是正因为这种情况,在多线程的情况下存在隐患.
+ 一般要用压测工具(如:jcstress)进行测试才有可能出错.

