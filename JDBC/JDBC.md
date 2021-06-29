# JDBC
## url
* url:统一资源定位符（网络中某个资源的绝对路径），url包括协议、IP、PORT、资源名
* Connection conn = null;
	Statement stmt =null;
	try{
	DriverManger.registerDriver(new com.mysql.jdbc.cj.Driver());
	conn = DriverManger.getConnection(“jdbc:mysal://localhost:3306/bjpowernode”,”root”,”970216”);   jdbc sql语句不要分号
	stmt = conn.createStatement();
	String sql = “delete from dept where deptno = 40”;
	int count = stmt.executeUpdate(sql); count是返回操作数据的条数
	}catch(SQLException e){
	e.printStackTrace();
	}finally{
	if(stmt!=null){
		try{
			stmt.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	if(conn!=null){
		try{
			conn.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
* 第二种常用注册方式：
Class.forName(“com.mysql.jdbc.Driver”);    该方式不需要接受返回值，只需要它的类加载动作，**为啥只需要一句话，因为该类的静态代码块中已经帮我们注册了驱动了。**，常用这种方式是因为参数是字符串，可以写到xxx.properties文件中。
* 实际开发中不建议在程序中把数据库信息写死到java程序中
import java.sql.*;
Import java.until.*;
使用资源绑定器绑定属性配置文件：
ResourceBundle bundle = ResourceBundle.getBundle(“jdbc”);
String driver = bundle.getString(“driver”);
String url = bundle.getString(“url”);
String user = bundle.getString(“user”);
String password = bundle.getString(“password”);
* jdbc中列的下标从1开始。
* int executeUpdate(insert_delete_update)
ResulteSet executeQuery(select)

Connection conn = null;
Statement stmt = null;
ResultSet rs = null;

用while(rs.next())遍历查询结果，rs.getString(“字段名(注是查询结果的字段名)“);  除列String类型取出数据   
 **列名称不是表中名称，而是查询结果集的列名称。**
* 用户登陆数据库业务：
  1、需求：模拟用户登陆实现功能。
  2、业务描述：程序运行的时候，提供一个输入的入口，可以让用户输入用户名和密码。java程序接收到输入后连入数据库验证用户和密码是否合法。
  3、数据库准备，常用Powerdesigner
  4、sql注入，输入用户：fdsa 密码：fdsa’ or ‘1’ = ‘1

  5、用户输入的sql语句，注入实际上是因为密码中有sql语句的关键字，sql语句编译执行时语句产生了其他的语义。

* 解决sql注入问题

  只要用户提供信息步参与SQL语句的编译过程，问题就解决了。

  要想用户信息不参与SQL语句，那么必须使用java.sql.PreparedStatement,PreparedStatement是属于预编译的数据库操作对象。PreparedStatement的原理是：预先对SQL语句的框架进行编译，然后再给SQL传值。

  ```java
  String sql = "select * from t_user where loginName=? and loginPwd=?";
  ```

  其中一个？表示一个占位符，一个？将来将接受一个“值”。注：占位符不能使用单引号括起来。

* 在业务需要sql注入的时候才用statement。类似必须使用拼接的时候，如需要升序或降序。

## JDBC事务机制

+ 只要执行任意一条DML语句，则自动提交一次。这是JDBC默认的事务行为。但是实际业务中，通常都是N条DML语句共同联合才能完成的，必须保证他们这些DML语句在同一事务中同时成功或失败。 

+ ```java
  conn.setAutoCommit(false);
  conn.commit();
  conn.rollback();
  ```

## 行级锁（悲观锁）

```mysql
select ENAME,JOB,SAL from EMP where JOB = 'MANGER' for update
```

通过在语句后加for update将会对指定行进行行级锁，若此事务没结束时这些记录无法被其他事务修改。

悲观锁事务必须排队，数据锁住了，不允许并发。 乐观锁：支持并发，事务也不需要排队，只不过需要一个版本号，当事务提交阶段发现版本号变化则回滚。

## 连接池

+ 一般业务中为了避免资源重复新建和删除利用连接池提高效率。
+ 同时连接池的存在能够避免服务器在高并发的情况下崩溃。

## dbutils

+ ResultSettHandler接口是用来处理查询结果的，可以在这个接口中实现将结果封装成对象。

+ 用jar包中自带的实现接口类BeanHander好处理，但是对于当entity类与数据库中字段名不同时，不一定能映射正确，这时可以手写hanler进行自定义映射。

  ```java
  new ResultSetHandler<List<User>>(){
  @Override
  public List<User> users = new ArrayList<>();
  while(rs.next(){
  String name = rs.getString("name");
  String password = rs.getString("password");
  users.add(new User(name,password));
  return users
  })
  }
  ```

  

