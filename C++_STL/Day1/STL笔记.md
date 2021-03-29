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

+ swap()通过 x 的内容交换数组的内容,这是另一个相同类型的数组对象(包括相同的大小)。与其他容器的交换成员函数不同,此成员函数通过在各个元素之间执行与其大小相同的单独交换操作,以线性时间运行。

+ relational operators (array)
  形如:arrayA != arrayB、arrayA > arrayB;依此比较数组每个元素的大小关系。

#### vector

+ vector 为它们的元素使用连续的存储位置，和数组一样可以用指针偏移的方式访问，和和数组一样高效，不同的是可以动态改变大小。内部实现资源的动态分配和释放，所以当你需要动态分配数组时，完全可以利用vector代替。当vector容量不够用时，将会自动重新分配新的内存，而且会额外分配更多的空间(一般是以两倍进行)。当然重新分配空间并将元素拷贝进新空间是相当耗时的，所以当你知道容器大约的元素量时，可以用reserve(n)进行设置容器的容量。

  ```c++
  vector<int> v1;
  vector<int> v2(5);
  vector<int> v3;
  v3.resize(10);
  vector<int> v4;
  v4.reserve(10);
  cout<<"size of v1 : "<<v1.size()<<"   "<<"size of v2 : "<<v2.size()<<"   "<<"size of v3 : "<<v3.size()<<"   "<<"size of v4 : "<<v4.size()<<"   "<<endl;
  cout<<"capacity of v1 : "<<v1.capacity()<<"   "<<"capacity of v2 : "<<v2.capacity()<<"   "<<"capacity of v3 : "<<v3.capacity()<<"   "<<"capacity of v4 : "<<v4.capacity()<<"   "<<endl;
  cout<<"v1: ";for(auto  it= v1.begin();it!=v1.end();it++)cout<<*it<<" ";cout<<endl;
  cout<<"v2: ";for(auto  it= v2.begin();it!=v2.end();it++)cout<<*it<<" ";cout<<endl;
  cout<<"v3: ";for(auto  it= v3.begin();it!=v3.end();it++)cout<<*it<<" ";cout<<endl;
  cout<<"v4: ";for(auto  it= v4.begin();it!=v4.end();it++)cout<<*it<<" ";cout<<endl;
  for(int i=0;i<5;i++){
      v1.push_back(i);v2.push_back(i);v3.push_back(i);v4.push_back(i);
  }
  cout<<"size of v1 : "<<v1.size()<<"   "<<"size of v2 : "<<v2.size()<<"   "<<"size of v3 : "<<v3.size()<<"   "<<"size of v4 : "<<v4.size()<<"   "<<endl;
  cout<<"capacity of v1 : "<<v1.capacity()<<"   "<<"capacity of v2 : "<<v2.capacity()<<"   "<<"capacity of v3 : "<<v3.capacity()<<"   "<<"capacity of v4 : "<<v4.capacity()<<"   "<<endl;
  cout<<"v1: ";for(auto  it= v1.begin();it!=v1.end();it++)cout<<*it<<" ";cout<<endl;
  cout<<"v2: ";for(auto  it= v2.begin();it!=v2.end();it++)cout<<*it<<" ";cout<<endl;
  cout<<"v3: ";for(auto  it= v3.begin();it!=v3.end();it++)cout<<*it<<" ";cout<<endl;
  cout<<"v4: ";for(auto  it= v4.begin();it!=v4.end();it++)cout<<*it<<" ";cout<<endl;
  ```

  result:

  size of v1 : 0   size of v2 : 5   size of v3 : 10   size of v4 : 0   
  capacity of v1 : 0   capacity of v2 : 5   capacity of v3 : 10   capacity of v4 : 10   
  v1: 
  v2: 0 0 0 0 0 
  v3: 0 0 0 0 0 0 0 0 0 0 
  v4: 
  size of v1 : 5   size of v2 : 10   size of v3 : 15   size of v4 : 5   
  capacity of v1 : 8   capacity of v2 : 10   capacity of v3 : 20   capacity of v4 : 10   
  v1: 0 1 2 3 4 
  v2: 0 0 0 0 0 0 1 2 3 4 
  v3: 0 0 0 0 0 0 0 0 0 0 0 1 2 3 4 
  v4: 0 1 2 3 4 

  Process finished with exit code 0

+ vector::operator=,将新内容分配给容器,替换其当前内容,并相应地修改其大小。

+ vector::max_size,返回该 vector 可容纳的元素的最大数量。这是容器可以达到的最大潜在大小,但容器无法保证能够达到该大小:在达到该大小之前的任何时间,仍然无需分配存储。

  size of v1 : 0   size of v2 : 5   size of v3 : 10   size of v4 : 0   
  max_size of v1 : 2305843009213693951   max_size of v2 : 2305843009213693951   max_size of v3 : 2305843009213693951   max_size of v4 : 2305843009213693951   

+ vector::resize,调整容器的大小,使其包含 n 个元素。n>size,内容将被缩小到前 n 个元素,并销毁它们。n<size,以val(默认为0)填充容器到达n的大小。n>capacity(容量)将会重新分配。

+ vector::capacity,返回当前为 vector 分配的存储空间的大小,这个 capacity(容量)不一定等于 vector 的 size。它可以相等或更大,额外的空间允许适应增长,而不需要重新分配每个插入。

+ vector::reserve,请求容量至少可以容纳n个元素，n>当前容量将重新分配，n<当前容量此方法无用。

+ vector::shrinktofit,请求降低capacity与size匹配，该方法由编译器决定是否释放多余内存，不具有约束力。并且该方法不会改变元素大小。

+ vector::data,返回容器第一个元素指针，可用于指针偏移访问。

+ vector::assign,

  ```c++
  void assign (InputIterator first, InputIterator last);
  second.assign (first.begin(),first.end()-1);
  void assign (size_type n, const value_type& val);
  first.assign (7,100);
  void assign (initializer_list<value_type> il);
  int myints[] ={1776,7,4};
  third.assign (myints,myints+3);
  ```

  (1)形参迭代器指针、(2)n个新内容均用val填充、(3)可用数组分配

  vector::assign与vector::operatro=的区别：

+ vector::push_back,在 vector 的最后一个元素之后添加一个新元素。val 的内容被复制(或移动)到新的元素。这有效地将容器 size 增加了一个,如果新的矢量 size 超过了当前 vector 的 capacity,则导致所分配的存储空间自动重新分配。

+ vector::pop_back(),删除 vector 中的最后一个元素,有效地将容器 size 减少一个。

  

