## MySQL
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

  关系符号：=,!=,<,>,>=,<=,between(可省)...and(左小右大闭区间),is null, and ,or,in,not , like

  

