# SpringBoot(内部约定)

## 一、注入实体类的方法

**spring mvc**会自动扫当前包及其子包

1. 配置文件

2. @Configuration+@Bean

3. @Import(Class)，这种方式是为了方便我们加载别人包下的配置文件，因为我们不可能完全扫描完别人的包。

4. 实现ImportSelector接口类的对象。这个接口里面有个方法返回一个字符串数组，数组里面是类全路径名字，把这个接口结果返回给@Import就可实现多个import。

5. 现在常用的配置方式是用配置文件。

6. yml以及properties配置文件中对象注入方法

   ```java
   @Component
   //下面这个注解作用是，去配置文件（yml）中吧对应前缀中属性值
   //赋予下面这个在容器中的对象
   @ConfigurationProperties(prefix="vip")
   public class Vip(){
   	private String name;
   	private Integer age;
   }
   ```

mapper的扫描两种方式：

+ @Mapper
+ @MapperScan

## 二、依赖分析

## 三、web配置

**首先，springboot是一个基于约定的框架。**

1. 欢迎页：默认```classpath:/static/index.html```
2. 内嵌服务器是可以更换的
3. 静态资源如js、css、img等资源文件是默认放在```classpath:/static/或者classpath:/public/```俩文件下。当然有需要可以自己实现。

### **重点：配置类进行web配置**

1. 拦截器
   + 自己实现一个HandlerIterceptor，在实现webMvcConfiger接口里面重写对应方法，注册拦截器，并对拦截器进行模式匹配。
2. 资源
   + 在WebMvcConfiger接口中重写对应方法，对资源进行注册。通过前缀classpath:、file:、等进行资源映射。
3. 消息转化器
   + springboot或者是说springMVC中是默认配置了jackson作为消息转化的，只需要引入相关jar包即可。
   + 要自己实现converter的话就需要自己相应接口，然后再配置文件中进行注册。
4. 视图解析器
   + springboot不太支持jsp，springboot模式的视图解析器是用的thymeleaf，所以引入jar包即可，大部分配置都以被springboot默认设置了。如果你有需求更改配置，可以在yml文件中自行更改。
   + 配置文件中的Configuration文件的addViewController，可以配置视图跳转。
5. 跨域配置

## 四、跨域

浏览器是跨域问题的前提，无浏览器就无跨域问题。 所以一种跨域的解决方法：服务器之间不存在跨域问题。

1. 同源策略

   + 同源是指域名，协议，端口相同。

2. 跨域

   + 一个网站需要访问另一个网站时。前后分离时存在这种需求。 

3. 三种解决方法

   + 先问

   + 服务器发请求，用一个controller来转发

     ```
     @RequestMapping("api/**")
     @ResponseBody
     public Object proxy(HttpServletRequest request){
     	System.out.println(proxyAddress);
     	retrun restTemplate.getForObject(
     	proxyAddress+request.getRequestURI().replace("api",""))
     }
     ```

     

   + jsonp、Nginx

4. springboot的跨域目前需要了解的几种方式(前后分离的时候，前端的浏览器服务需要访问后端的服务，所以一般后端会开发一个跨域口给前端访问。)

   + springboot配置cors跨域，**对某一接口配置，可以在方法上添加 @CrossOrigin 注解 **

   + 实现配置类WebMvcConfigurerAdapter中的addCorsMappings方法。

   + 手动添加Filter

     ```java
     @Bean
     public FilterRegistrationBean corsFilter() {
         UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
         CorsConfiguration config = new CorsConfiguration();
         config.setAllowCredentials(true);
         config.addAllowedOrigin("http://localhost:9000");
         config.addAllowedOrigin("null");
         config.addAllowedHeader("*");
         config.addAllowedMethod("*");
         source.registerCorsConfiguration("/**", config); // CORS 配置对所有接口都有效
         FilterRegistrationBean bean = newFilterRegistrationBean(new CorsFilter(source));
         bean.setOrder(0);
         return bean;
     }
     ```

## 五、打包

1. 打jar包是默认打的，springboot呢主要是打jar包。

2. 打war包

   + 打war包我们是希望把东西打成war包拿到其他tomcat上跑，虽然我们的程序是跑在内嵌的tomcat上面的，但是可以通过先排除再引入加上scope:provided。

   + 打成war包，main方法其实就已经失效了。

   + 理解@SpringBootApplication这个注解，起码要知道这个注解是能够帮助我们扫包和注bean。打了war包就不走main，这边就要想办法进行初始化。

     ```java
     @Component
     public class ServletInitializer extends SpringBootServletInitializer {
     @Override
     	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
     		return builder.sources(MySpringBootApplication.class);
     	}
     }
     ```

3. 简单理解一下：没了main，用外置tomcat，没了xml配置文件，tomcat启动会先尝试找webxml，找不到会去找一个启动类，启动类会再加载所有实现了webapplicationinitializer的类，上面我们自己实现的springbootservletinitializer就会被加载进去从而实现配置。

## 六、启动系统任务

有两个接口CommandLineRunner和ApplicationRunner，实现了这两项接口的类会在系统启动后自动调用执行run方法。

```java
@Component
@Order(1)
public class CommandRunnerOne implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("启动测试1");
    }
}
```

这玩意主要是用来进行初始化工作的。典型应用呢就是redis预热。

## 七、配置文件顺序

简单来说我们平常都是把配置文件放在resource下的，这个配置文件的优先级是最低的，但是这样做是有好处的，因为打包后全在jar里，我要改配置文件就变复杂了，但是我在jar包同级目录下加配置文件（这个文件的优先级更高）。

**配置文件的东西是覆盖加合并**，重复的低优先级被高优先级占用

## 八、多环境配置

开发过程中是有开发、测试、上线等环境。(最起码数据库不一样)

1. 三种不同的yml文件``` application-dev、application-prod、application-test```

2. 在主yml文件中对所选环境激活即可

   ```yml
   spring:
   	profiles:
   		active:dev #指定那个环境
   ```

3. 这个选择激活可以用命令行

## 九、原生servle、listener、filter

这里有个应用点：druid的状态监测页面需要进行原生配置，这个应用要用下面配置类的开发。

1. spring中支持原生servlet、filter、listener，即可以在spring中以注解的方式完成servlet。

2. 实现了servlet应该是不会去找controller。

3. 两种实现方式：

   + 注解开发，application上加一个扫包注解，在各实现类上加对应web注解

   + 配置类开发，实现各类，在servletConfiguration中进行注册

     **spring会主动发现这些bean，将其配置到对应的servlet上**

## 十、观察者设计模式

简单理解：就是监听器上注册事件，事件一旦发生就触发监听器相应的响应。

## 十一、定时任务

定时任务典型应用：日报、月包。每日短信等。

1. @EnableScheduling，本质上是@Import(SchedulingConfiguration.class)
2. 如何实现：
   + 首先得是注入的Bean
   + 需要定时的方法上加入注解@Schedule。
3. 4种不同的定时机制：fixedDelay、fixedRate、initialDelay、cron。(**具体使用用时再查**)



# springboot(外部整合)

以下介绍一些整合组件，具体依赖步骤看看pdf，我这记一些我没过或者感觉是重点的内容。

## 一、Junit

## 二、JPA

这个是spring规定的持久层的规范，实现这个规范最好的是hibernate。与mybatis是一个作用。

## 三、mybatis

1. 步骤：
   + 配置文件配数据源
   + 配置文件配mybatis
   + 所有dao层加@Mapper，springboot自动扫这个类，就不用再scanner扫接口咯。

## 四、pageHelper

加依赖，配置，大概原理是在你执行sql前拦截再反射。

## 五、整合日志

依赖已有，加配置(xml)

+ 需要使用的时候，网上找一个相应的配置文件，在resource中加lombok.xml文件spring会自动加载这个配置。具体以后使用需求自己再细用。

## 六、thymleaf

## 七、Swagger

自动生成后端接口文件。





# Springboot小抄

+ **@Table** 当实体类与其映射的数据库表名不同名时需要使用

+ @Accessors(chain=true)

      ```java
      //开起chain=true后可以使用链式的set
      User user=new User().setAge(31).setName("pollyduan");//返回对象
      ```
      
+ @ControllerAdvice

     1. 全局异常处理
     2. 全局数据绑定
     3. 全局数据预处理

+ @Transactional



