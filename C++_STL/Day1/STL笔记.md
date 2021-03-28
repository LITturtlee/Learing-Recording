## STL笔记

### Day1

#### array

+ 连续存储,不能动态地扩大或缩小。

+ begin() 返回容器第一个元素的迭代器，end()返回容器最后一个元素之后的理论元素迭代器。rbegin()、rend()返回迭代器的反向指针，以上四种均可修改所指内容。cbegin()、cend()常量迭代器，不可修改指针所指内容。

+ operator[] 不会检查边界   array::at会检查边界。operator[]越界会读到内存中其他内容，而at()越界会抛出异常,这俩操作返回的都是引用.![image-20210328211901200](/home/ng/.config/Typora/typora-user-images/image-20210328211901200.png)

  ![image-20210328211922558](/home/ng/.config/Typora/typora-user-images/image-20210328211922558.png)

+ data()返回数组对象第一个指针,由于数组中的元素存储在连续的存储位置,所以检索到的指针可以偏移以访问数组中的任何元素。

+ fill()用 val 填充数组所有元素,将 val 设置为数组对象中所有元素的值。

+ get(array)形如:std::get<0>(myarray);传入一个数组容器,返回指定位置元素的引用。

+ relational operators (array)
  形如:arrayA != arrayB、arrayA > arrayB;依此比较数组每个元素的大小关系。

