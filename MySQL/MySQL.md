## MySQL

### day1

* 安装与配置
  端口、utf8

* sql、DB、DBMS分别是：
  DB：数据库，以文件形式存在
  DBMS：数据库管理系统，常见：MySQL Oracle
  SQL：结构化查询语言，高级语言
  关系：DBMS->MySQL->DB

* 表：table是数据库基本组成单元，所以数据以表格形式组织，目的可读性强
  一个表包括行和列，行被称为数据（记录）列被称为字段。一个字段应该包括哪些属性：字段名、数据类型、相关的约束。

* SQL语句分类
  DQL：数据查询语言，查询语言，凡是select语句都是DQL。
  DML：数据操作语言，insert delete update，对表中数据结构进行增删改。
  DDL：数据定义语言，create drop alter，对表结构的增删改。
  TCL：事务控制语言，commit提交事务，rollback回滚事务。
  DCL：数据控制语言，grant授权、revoke撤销权限等。

* 导入数据

  第一步：登陆mysql数据库管理系统，mysql -uroot -p

  第二步：查看有哪些数据库，show databases;(MySQL命令，非SQL语句)

  第三步：创建数据库，create database LITturtle;(MySQL命令，非SQL语句)

  第四步：使用LITturtle数据，use LITturtle;(MySQL命令，非SQL语句)

  第五步：查看当前使用的数据库有哪些表，show tables;(MySQL命令，非SQL语句)

  第六步：初始化，source 绝对路径

* .sql的文件是sql脚本，source可以直接执行sql脚本。

* 常用MySQL命令

  删除数据库，drop database LITturtle;

  查看表结构，命令desc 

  查看表数据，select * from _table;

  结束一条语句  \c

  查看当前数据库，select version();

  查看建表语句，show create table emp;

* 简单查询语句（DQL）

  语法：select 字段1，字段2，... from 表名；

  字段可以参与数学运算，select ENAME,SAL*12 as YEARSAL from EMP;（as 关键字段可以省略）

  字符串用单引号。

  查询全部字段，select * from EMP；(* 效率较低)

* 条件查询

  语法：select 字段... from 表名 where 条件 

  关系符号：=,!=,<,>,>=,<=,between(可省)...and(左小右大闭区间),is null, and ,or,in（值1，值2）,not , like

  运算符优先级可以用( )
  
  模糊查询like：模糊查询%代表任意多个字符，\_代表任意一个字符。eg：select ENAME from EMP where ENAME like '%O%';         select ENAME from EMP where ENMAE like '_A%'              ;      select ENAME from EMP where ENMAE like '%\\_%'
  
* 排序

  select ENAME,SAL from EMP order by SAL ;     //升序

  select ENAME,SAL from EMP order by SAL asc;   //升序

   select ENAME,SAL from EMP order by SAL desc;  //降徐

  多字段排序：order by 字段1 排序规则，字段2 排序规则。考前优先级越高。

  select ENAME,JOB,SAL from EMP order by SAL desc;

  语法：select 字段 from 表名 where 条件 order by 排序规则

* 分组函数

  count,sum,max,min,avg,一共5个，又称多行函数，多入一出。

  语法：select fun(字段) from table;

  **分组函数自动忽略NULL**

  **分组函数不可直接使用在where语句中，group by的执行顺序在where之后**，eg：select ENAME,SAL from EMP where SAL > avg(SAL); //error

  找出高于平均工资的员工解决办法：select ENAME,SAL from EMP where SAL > (select avg(SAL) from EMP);

  count(\*)与count(字段)，count(\*)：不是统计某个字段中数据个数，而是统计总记录条数。count(字段)：表示统计某字段不为NULL的数据总量。

* 单行处理函数

  select ENAME,(SAL+COMM)*12 as YEARSAL from EMP;

  注：只要运算中存在NULL整个结果为NULL

  ifnull(),空处理函数。

  ifnull(可能为NULL的数据，被当作什么处理), eg: select ENAME,ifnull(COMM,0) as COMM from EMP;

* group by 和 having

  group by: 按照某个字段或在某些字段进行分组。having : having是对分组之后的数据进行再次过滤。

  分组函数一般会和group by一起使用，且分组函数都是在group by语句执行结束之后才会执行。当无group by语句时，表自成一组。 **group by的执行顺序在where之后**，eg:select max(SAL) from EMP group by JOB;
  
  select ..(5) from ..(1) where .. (2) group by ..(3) having .. (4) order by ..(6)
  
  **当有group by时，参加select后只能跟分组函数以及分组中的字段**
  
  能否多字段联合一起分组。eg:找出每个部门不同工作岗位的最高薪资。select DEPTNO,JOB,max(SAL) from EMP group by DEPTNO,JOB order by DEPTNO desc;
  
  having，用于过滤，在group by 后执行，而where在group by之前执行。**having 不可单独用，与group by连用**
  
  eg:找出每个部门最高薪资并大于2900：select DEPTNO,max(SAL) from EMP where SAL>2900 group by DEPTNO;
  
  eg :找出每个部门平均薪资且大于2000：select DEPTNO,avg(SAL) from EMP group by DEPTNO having avg(SAL) > 2000;

### day2

+ **查询结果去重,关键字distinct**,eg:select distinct JOB from EMP;

  **distinct只能出现在所有字段的最前面**,select ENAME,distinct JOB from EMP;//error   select JOB,DEPTNO from EMP; //正确

  统计岗位数量 select count(distinct JOB) from EMP;

+ 连接查询

  实际情况下，大部分不是单表查询，一般都是多张表联合查询取出最终结果。**多张表连接解决数据冗余问题**

  笛卡尔乘积现象：当两张表进行连接查询时，没有任何条件进行限制，最终的查询结果条数是两张表记录的乘积。**但是避免现象不意味低层避免**

  eg：找出每个员工的部门名称，要求显示员工名和部门名。select e.ENAME,d.DNAME from EMP e,DEPT d where e.DEPTNO = d.DEPTNO;(sql92  不常用)

+ 内连接之等值连接：最大特点是：条件是等量关系

  语法：... from A (inner)join B on 连接关系 where ...
  
+ 内连接之非等值连接：最大特点是：连接条件中的关系是非等量关系。

  eg ：找出每个员工的工资等级，要求显示员工名、工资、工资等级。

  select E.ENAME,E.SAL,S.GRADE from EMP E join SALGRADE S on E.SAL between S.LOSAL and S.HISAL;

+ 内连接之自连接：最大的特点是：一张表看做两张表。自己连自己

  eg：找出每个员工的上级领导，要求显示员工名和对应的领导名。

  select a.ENAME as employee,b.ENAME as employer from EMP a join EMP b on a.MGR=b.EMPNO;

+ 外连接(使用据多)

  内外连接的区别：**内连接**：假设A和B表进行连接，使用内连接的话，凡是A表和B表能匹配上的记录查询出来，这就是内连接，AB俩表没有主副之分，两张表是平等的。**外连接**：假设A和B表进行连接，使用外连接的话，AB两张表有一张表是主表，一张表是副表，主要查询主表中的数据，顺带查询副表，当副表中的数据没有和主表中的数据匹配上，副表自动模拟出NULL与之匹配。

  外连接分类 ：左外连接（左连接）：表示左边的表为主表。右外连接（右连接）：表示右边的表为主表。

  select a.ENAME as employee,b.ENAME as employer from EMP a **left** join EMP b on a.MGR=b.EMPNO;

  找出某个部门没有员工：select D.* from EMP E right join DEPT D on E.DEPTNO=D.DEPTNO where E.DEPTNO is null;

+ 全连接业务要求很少。

+ 三张表及以上的查询

  eg ：找出每一个员工的部门名称以及工资等级。EMP、DEPT、SALGRADE

  select E.ENAME,D.DNAME,S.GRADE from EMP E join DEPT D on E.DEPTNO=D.DEPTNO join SALGRADE S on E.SAL between S.LOSAL and S.HISAL;

  eg：找出每一个员工的部门名称、工资等级、以及上级领导

  select E.ENAME,D.DNAME,S.GRADE,EB.ENAME as boos from EMP E join DEPT D on E.DEPTNO=D.DEPTNO join SALGRADE S on E.SAL between S.LOSAL and S.HISAL left join EMP EB on E.MGR=EB.EMPNO;
  
+ Navicat 工具


* 子查询，selelct 语句中嵌套select
  select ..(select) from .. (select) where... (select)
* Where后嵌套：Eg:select * from EMP where SAL > (select avg(SAL) from EMP);
* From后嵌套：Eg:找出每个部门平均薪资等级。first：select avg(SAL) as avgSAL from EMP group by EMPNO;  **second**: select T.* , S.GRADE from (select avg(SAL) as avgSAL from EMP group by EMPNO) T left join SALGRADE S on T.avgSAL between S.LOSAL and S.HISAL;
* Select后嵌套：select E.ENAME,(select D.DNAME from DEPT D where E.DEPTNO=D.DEPTNO) as DNAME from EMP E;这句话即同等连查员工表和部门表。
* Union(可以将查询结果相加（条数拼接））select ENAME,JOB from EMP where JOB = ‘MANGER’ union select ENAME,JOB from EMP where JOB = ‘SALESMAN’;
  **查询前后结果的列数得一样**
* **limit**重点中的重点，以后**分页查询**全靠它。
  limit事mysql特有的，其他数据库中没有，不通用。limit取结果集中的部分数据，这是它的作用。
  语法机制：limit startIndex（从0开始，0表示第一条数据），length
  **limit是最后执行**
  eg：select ENAME,SAL from EMP order by SAL desc limit 3,6;
  **通用的标准分页sql**：每页显示pageSize条记录：第pageNo页：（pageNo - 1）*pageSize，pageSize）
* 创建表：
  语法格式：create table 表名（字段1 数据类型， 字段2 数据类型 ， 字段3 数据类型...）；
  **mysql中常见类型**：
  int：整型（java中int）
  bigint：长整型（java中的long）
  float：浮点型（java中的float double）
  char：定长字符串（java中的String）
  varchar：可变字符串（java中的StringBuffer/StringBuilder）
  date：日期类型（java中的java.sql.Data)
  BLOB：二进制大对象（存储图片、视频等流媒体信息）
  CLOB：字符大对象（存储较大文本，比如，可以存储4G的字符串）
  **char和varchar如何选择**：char定长分配空间，而varchar动态分配空间，两者都得在初始化长度。对于字段长度不变优先选择char，例如性别、生日。字段变长选择varchar例如简介、姓名。**char效率高于varchar**
  eg：create table t_student{
  NO bigint,
  NAME varchar(255),
  SEX char(1),
  CLASSNO varchar(255),
  birth char(10)
  };
* Insert语句插入数据
  **语法格式：insert into 表名（字段1，字段2，字段3，....) values（值1，值2，值3，....) ,要求：前后列数相同**，只插入一个字段时其他字段自动null补全。
  eg ：insert into t_student(no,name,sex,classno,brith) values(1,’zhangsan’,’1’,’gaosan1ban’,’1950-10-12’);
  注：当insert成功后，表中必多一行，且即使多这一行记录中某些字段为NULL，后期也只有通过update进行更新。
  insert插入多行：insert into t_student values(),();  这条语句中缺省了字段，会默认全部插入所以括号中的值要匹配字段列数。
* 建表设置默认值
  create table t_student{ 
  no bigint,
  name varchar(255),
  sex char(1) default 1,
  classno varchar(255),
  birth char(10)
  };
* 删除表，drop table if exists t_student; 当这个表存在时删除这个表
* **表的复制**，语法：create table 表名 as select语句；eg：create table emp1 as select * from emp；
* 将查询结果插入到一张表中，eg：insert into dept1 select * from dept；
* 修改数据，**语法格式： update 表名 set 字段1=值1，字段2=值2 ... where 条件；**
  **注：没有条件整表全更新**
  eg：update dept1 set loc = ‘SHANGHAI’ , DNAME = ‘RENSHIBU’ where deptno =10;        update dept1 self loc = ‘x’ , dname = ‘y’;   **后面这条语句将表中指定字段全修改**
* 删除数据，**语法格式 delete from 表名 where 条件; 注没有条件全删除。** eg:delete from dept1 where deptno=10;       delete from dept1;(删除所有记录，**不是删表**）注：**delete不删除物理空间，所以慢**
* 删除大表（快），truncate table emp1；注：**表被截断，不可回滚，永久丢失**
* 对于表结构的修改，这里不讲，大家可以用工具完成，因为实际开发中，很少中途修改表的结构。出现在java中的sql包括：insert、delete、update、selec（这些对数据操作的语句）
* 约束（constraint）
  非空约束（not null）：约束字段不能为NULL
  唯一约束（unique）：约束字段不重复
  主键约束（primary key）：约束字段既不空也不为NULL，也不能重复（简称PK）
  外键约束（foreign key）：
  检查约束（check）：注意oracle数据库有check约束而mysql没有。
* 非空约束 not null
  drop table if exists t_user；
  create table t_user( id int,username varchar(255) **not null**, password varchar(255) );**注：没有表级约束。**

### Day3

* 唯一性约束，unique修饰字段具有唯一性，不能重复，**但可以为null**。可以在列声明完后，用unique（字段1，字段2）进行多字段联合唯一性（**表级约束**），这种方式有别于单独在后面直接加unique（**列级约束**）。
* 主键约束，主键约束的列级约束在字段后加primary key，表级约束在字段声明后加primary key（字段）。主键约束特点：不能为NULL，不能重复。**一张表的主键只有一个**
* 主键的作用：
  1、表的设计三范式中有要求，第一范式就要求任何一张表都应该有主键。
  2、主键的作用：主键值时这行记录在这张表当中的唯一标识。（例如生份证id）
* 主键的分类：
  根据主键字段数量划分：**单一主键**：推荐使用，常用。**复合主键**：多字段联合添加一个主键约束，复合主键不建议使用，违背三范式。
  根据主键性质划分：**自然主键**：主键与业务无关的自然数（推荐该方式）。**业务主键**：主键值和业务挂钩，例如用卡号做主键。实际中不推荐使用，因为业务发生改变时主键也可能需要改变，而改变主键可能导致主键重复而不能修改。
* **主键自增**，将原来的primary key后再加**auto_increment**。
* 外键约束，**语法结构：（在字段声明结束后）foreign key(classno) references t_class (con)**
  外键的作用可以理解为**子表**某字段的值范围限制在**父表**某字段的范围内。所以在用了外键后要注意顺序。**注：子表外键引用父表的字段该字段必须具有唯一性，且外键值可以为NULL。**

#### 事务（commit、rollback）

+ 一个事务是一个完整的业务逻辑单元，不可再分。**要想保证以上两条DML语句同时成功或同时失败，那么就需要使用数据库的事务机制**

+ 和事务相关的语句只有DML语句（insert、delete、update），因为这三个语句都是和数据库表当中的数据相关，**事务存在的意义是保证数据的完整性**

+ 事务四大特性：

  A、原子性：事务是最小的工作单元，不可再分。

  C、一致性：事务必须保证多条DML语句同时成功或在同时失败。

  I、隔离性：事务A与事务B之间具有隔离。

  D、持久性：持久性说的是最终数据必须持久化到硬盘文件中，事务才算成功的结束。

+ 事务隔离

  第一级别：读未提交（read uncommitted）对方事务还未提交，我们当前事务可以读取到对方提交的数据。读未提交存在脏读（dirty read）现象。

  第二级别：读已提交（read committed）对方事务提交之后数据我方可以读取，解决了脏读现象。但存在不可重复读问题。

  第三级别：可重复读（repetable read）这种隔离解决了不可重复读问题但是存在读取数据是幻象。

  第四级别：序列化读/串行化读，解决所以问题但是存在效率问题，事务得排队。 

+ mysql中事务默认情况下是**自动提交**即执行一次DML语句就会提交事务，通过**start transaction；**语句开启事务，之后的DML语句都是改在缓存里，这个时候事务结束要通过commit或rollback操作。commit即提交事务将结果写入磁盘，rollback即回滚事务，回到事务开始前的状态。**两个语句都会清空缓存，结束事务**

+ mysql数据库默认的隔离级别是：可重复读。oracle数据库默认的隔离级别是：读已提交。

  ```mysql
  select @@transaction_isolation;             //查看隔离级别
  set session transaction isolation level repeatable read;    //修改隔离级别
  ```

  

#### 索引（低层B树）

+ 索引好比书的目录，通过目录可以快速找到对应资源。在数据库查询一张表的时候有俩检索方式：1、全表扫描2、根据索引检索（效率高）。

  索引虽然可提高效率，但不可随意添加，因为索引也是对象，也需要维护。例如，表中数据改动频繁就不宜加入索引，因为一旦数据变动索引也得变动。

  **添加索引是给某一或某些字段添加**

  where后字段没有索引，sql语句会全表扫描，而当where后字段有索引时，sql语句会按索引扫描。

+ 什么时候考虑给字段添加索引？

  1. 数据量大
  2. 该字段很少的DML操作。
  3. 该字段经常出现在where子句中。

+ **主键和具有unique约束的字段自动添加索引**，所以根据主键查询效率较高。尽量根据主键检索。

+ 创建索引对象，create index  索引名称 on 表名（字段名）；

  ```mysql
  create index EMP_SAL_INDEX on EMP(SAL);
  drop index EMP_SAL_INDEX on EMP;
  ```

  删除索引对象，drop index 索引名称 on 表名；

+ **查看sql语句执行计划**，可用来查看字段到底有木有索引。

  ```mysql
  explain select ENAME,SAL from EMP where SAL = 5000;
  ```

+ 索引什么时候失效？

  ```mysql
  select ENAME from EMP where ENAME like '%A%';
  ```

  模糊查询，第一个通配符是%，这个时候索引失效。

#### 视图(view)

+ 视图即是对DQL（查询）语句进行创建一张虚拟表，在该表上可以实现增删改查的功能，**但是对视图数据修改，会影响原始数据。**(通过视图影响原表数据，不是直接操作原表)

+ 创建、删除视图：

  ```mysql
  create view myview as select ENAME,EMPNO from EMP
  drop view myview;
  ```

+ 视图可以隐藏表的实现细节 ，保密级别高的数据库，是对程序员提供视图进行操作。

#### DBA命令（了解）

+ 数据库导出数据：

  ```shell
  mysqldump bjpowernode>\home\ng -uroot -p
  ```

+ 导入数据:source file.sql

#### 数据库设计三范式（重点，面试常问）

+ 三范式：（解决数据冗余的问题）
  1. 任何一张表都应该有主键，并且每一个字段原子性不可再分。
  2. 建立在第一范式的基础之上，所有非主键字段完全依赖主键，不能产生部分依赖（一般不要使用联合主键）。**多对多情况：三张表，关系表俩外键**
  3. 第三范式建立在第二范式基础之上 ，所有非主键字段直接依赖主键，不能产生传递依赖。**一对多，两张表，多的表加外键**
+ 实际开发过程中，有的时候会以冗余换执行速度。
+ 一对一设计的两种方案：**主键共享、外键唯一**

