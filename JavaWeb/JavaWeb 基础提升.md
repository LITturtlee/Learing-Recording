# JavaWeb 基础提升

## 网络基础

### TCP协议与udp协议

+ 这两都是数据传输的协议。简单区别：udp快不安全，tcp慢安全。udp常用在数字电视、游戏中，TCP常用在文件传输中。

### HTTP协议

+ http协议默认端口是80，https协议默认端口是443
+ 请求头描述了请求本身的一些特性：如地址、传输文件类型等。总结：请求行是一些基本信息，而头部与请求和响应相关的信息。

## HTML

## CSS

### 选择器

|     选择器      | 示例  |              示例说明              |
| :-------------: | :---: | :--------------------------------: |
|     .class      |       |             类名选择器             |
|       #id       |       |              id选择器              |
|        *        |       |              所有元素              |
|     element     |       |             标签选择器             |
| element,element | div,p |        所有\<div>、\<p>元素        |
| element element | div p |       \<div>内的所有\<p>元素       |
| element>element | div>p |     所有父级是\<div>的\<p>元素     |
| element+element | div+p | 所有紧挨着\<div>元素之后的\<p>元素 |

### 块级元素与内联元素

**区别：**

+ 块级元素(block)特性：
  1. 总是独占一行，表现为另起一行开始，而且其后的元素也必须另起一行显示;
  2. 宽度(width)、高度(height)、内边距(padding)和外边距(margin)都可控制;
+ 内联元素(inline)特性：
  1. 和相邻的内联元素在同一行;
  2. 宽度(width)、高度(height)、内边距的top/bottom(padding-top/padding-bottom)和外边距的
     top/bottom(margin-top/margin-bottom)都不可改变，就是里面文字或图片的大小;

**块级元素主要有：**

+ div , dl , fieldset , form , h1 , h2 , h3 , h4 , h5 , h6 , hr , ol , p , table , ul , li

**内联元素主要有：**

+ a , i , img , input , select , span , strong ,textarea

可以通过给display属性设置内联标签变为块级元素。也可设置块为内联块。display:block;display:inline-block

**使用浮动的时候，父元素要清除浮动，不然父元素高度会坍塌，子元素浮动后不会自动撑开父元素。**

动画小框架：animate.css

bootstrap中的css框架，其实就是引入css，在需要的标签上添上需要的样式class。bootstrap的样式一般都是响应式的。

格栅布局，可以学习学习。 

阿里矢量图，一些图标



## JavaScrip

### 基础补充

+ let与var

  1. 作用域不一样，var是函数作用域，而let是块作用域，
  2.  let不能在定义之前访问该变量，但是var是可以得。
  3.  let不能被重新定义，但是var是可以的。

  **以后推荐let**

+ return终止当前方法且返回值，并且脚本后面的方法也不会执行。

+ 对象可以直接用JSON代码