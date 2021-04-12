## STL笔记

### Day1

### 迭代器

+ 目的：容器都会用到排序、查找、求和等操作，利用迭代器作为中介将不同容器内部差异进行封装，利用泛形技术实现容器与算法的分离。简单来讲,迭代器和 C++ 的指针非常类似,它可以是需要的任意类型,通过迭代器可以指向容器中的某个元素,如果需要,还可以对该元素进行读/写操作。

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
  ```

+ vector::operator=,将新内容分配给容器,替换其当前内容,并相应地修改其大小。

+ vector::max_size,返回该 vector 可容纳的元素的最大数量。这是容器可以达到的最大潜在大小,但容器无法保证能够达到该大小:在达到该大小之前的任何时间,仍然无需分配存储。

  ```
  size of v1 : 0   size of v2 : 5   size of v3 : 10   size of v4 : 0   
  max_size of v1 : 2305843009213693951   max_size of v2 : 2305843009213693951   max_size of v3 : 2305843009213693951   max_size of v4 : 2305843009213693951  
  ```

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

+ vector::insert,在指定元素前插入新元素。注意插入可能引起重新分配，且因vector是基于数组，所以末尾或开头之外的位置进行插入操作均会引起其他元素的移动，尽量避免此操作，因为这种操作相对低效。*注：插入后it可能将失效，需要去获取新的，在容器最后插入且不超出容量的情况下不it不会失效。*

  ```c++
  vector<int> v1(10,1);
  vector<int> v2 = {1,2,3};
  vector<int>::iterator it;
  it = v1.begin();
  v1.insert(it,66);
  //"it" no longer valid, need to get a new one
  cout<< "v1: ";for(auto i:v1)cout<<i<<" ";cout<<endl;
  it = v1.begin() + 1;
  v1.insert(it,v2.begin(),v2.end());
  cout<< "v1: ";for(auto i:v1)cout<<i<<" ";cout<<endl;
  it = v1.begin() + 4;
  v1.insert(it,2,99);
  cout<< "v1: ";for(auto i:v1)cout<<i<<" ";cout<<endl;
  it = v1.begin() + 6;
  int ar[3] = {123,456,789};
  v1.insert(it,ar,ar+3);
  cout<< "v1: ";for(auto i:v1)cout<<i<<" ";cout<<endl;
  ```

  ```
  Result:
  
  v1: 66 1 1 1 1 1 1 1 1 1 1 
  v1: 66 1 2 3 1 1 1 1 1 1 1 1 1 1 
  v1: 66 1 2 3 99 99 1 1 1 1 1 1 1 1 1 1 
  v1: 66 1 2 3 99 99 123 456 789 1 1 1 1 1 1 1 1 1 1 
  v1: 66 1 2 3 99 99 123 456 789 1 1 1 1 1 1 1 1 1 1 88 
  789
  ```

  

+ vector::erase，删除单个元素或一系列元素([first,last])，同样因为基于数组，所以被擦除元素后可能会导致vector的部分元素重新定位，所以相对list、forward_list做erase操作更低效(因为list、forward_list是链表)。

+ vector::remove，删除容器中与指定元素相同的元素，返回最后一个元素下一个位置的迭代器。remove() 的实现原是,在遍历容器中的元素时,一旦遇到目标元素,就做上标记,然后继续遍历,直到找到一个非目标元素,即用此元素将最先做标记的位置覆盖掉,同时将此非目标元素所在的位置也做上标记,等待找到新的非目标元素将其覆盖。

+ vector其他删除方式：swap()+pop_back()

+ vector::swap,通过 x 的内容交换容器的内容,x 是另一个相同类型的 vector 对象。尺寸可能不同。

+ vector::clear，从 vector 中删除所有的元素(被销毁),留下 size 为 0 的容器。不保证重新分配,并且由于调用此函数, vector 的 capacity 不保证发生变化。强制重新分配的典型替代方法是使用 swap: vector<T>().swap(x);

  还可以用swap::vector<T>(x).swap(x)清除x中多余容量。

  ```c++
  v1.clear();v2.clear();
  cout<<"capacity of v1: "<<v1.capacity()<<endl;
  cout<<"capacity of v2: "<<v2.capacity()<<endl;
  vector<int>().swap(v1);
  vector<int>().swap(v2);
  cout<<"capacity of v1: "<<v1.capacity()<<endl;
  cout<<"capacity of v2: "<<v2.capacity()<<endl;
  ```

  ```
  Result: 
  
  capacity of v1: 3
  capacity of v2: 20
  capacity of v1: 0
  capacity of v2: 0
  ```

  

+ vector::emplace，用于在vector容器指定位置之前插入**一个**新的元素。iterator emplace (const_iterator pos, args...);其在args...表示新插入元素的构造函数相对应的多个参数。简单的理解 args...，即被插入元素的构造函数需要多少个参数，那么在 emplace() 的第一个参数的后面，就需要传入相应数量的参数。

  **与insert的区别：简单的理解，就是 emplace() 在插入元素时，是在容器的指定位置直接构造元素，而不是先单独生成，再将其复制（或移动）到容器中。因此，在实际使用中，推荐优先使用 emplace()。**

+ vector::get_allocator,allocator， 即空间配置器，用于实现内存的动态分配与释放。那么为什么在vector中定义allocator而不直接使用new和delete呢？原因便是减少开销。我们知道，new和delete申请与释放内存的开销是比较大的。如果多次new与delete会使程序的效率大大降低。这时开发者很聪明，定义了一个allocator来实现内存的管理。

### Day2

#### deque

+ 和 vector 非常相似,区别在于使用该容器不仅尾部插入和删除元素高效,在头部插入或删除元素也同样高效,时间复杂度都是 O(1) 常数阶,但是在容器中某一位置处插入或删除元素,时间复杂度为 O(n) 线性阶;**deque容器中存储元素并不能保证所有元素都存储在连续空间中**

+ 相对vector增加实现了在容器头部添加和删除元素的成员函数，同时删除了capacity()、reserve()、data()成员函数。

+ deque底层存储机制

  ![image-20210331233603267](/home/ng/.config/Typora/typora-user-images/image-20210331233603267.png)

  ![image-20210331234713863](/home/ng/.config/Typora/typora-user-images/image-20210331234713863.png)

  由此deque中无data()，deque不保证元素连续存储，所以不要用指针访问deque。

+ at与operator[]区别是是否检查边界，为什么operator[]不检测边界呢？因为效率，每次访问元素都检测边界相对低效。

+ 删除操作：pop_front()、pop_back()、erase()、swap()+pop_back()

#### List

+ STL list 容器,又称双向链表容器,即该容器的底层是以双向链表的形式实现的。这意味着,list 容器中的元素可以分散存储在内存空间里,而不是必须存储在一整块连续的内存空间中。实际场景中,如何需要对序列进行大量添加或删除元素的操作,而直接访问元素的需求却很少,这种情况建议使用 list 容器存储序列。

+ 只能运用迭代器才能访问list容器中存储的各个元素。list的迭代器与array、vector、deque有所不同，list迭代器为双向迭代器，不支持从随机访问。 这意味着,假设 p1 和 p2 都是双向迭代器,则它们支持使用 ++p1、 p1++、 p1--、 p1++、 *p1、 p1==p2 以及 p1!=p2 运算符,但不支持以下操作：

  + p1[i]:不能通过下标访问 list 容器中指定位置处的元素。
  + p1-=i、 p1+=i、 p1+i 、p1-i:双向迭代器 p1 不支持使用 -=、+=、+、- 运算符。
  + p1<p2、 p1>p2、 p1<=p2、 p1>=p2:双向迭代器 p1、p2 不支持使用 <、 >、 <=、 >= 比较运算符。

  **值的注意的是list的insert、splice等操作不会引起list迭代器失效，甚至执行删除操作，仅对被删除元素的迭代器失效，其余迭代器不受影响。**

  **特别注意，非连续存储的容器，删除插入等操作的确不会引起迭代器失效，但是会出现迭代器错位的情况。比如删除前给迭代器变量iter赋值容器的begin，删除begin后，iter不再指向容器的begin，指向的是内存中脏数据。**

+ **list::splice()**，将其他 list 容器存储的多个元素添加到当前 list 容器的指定位置处，有以下三种调用形似。(都是用迭代器实现)(splice拼接)

  + void splice (iterator position, list& x);此格式的 splice() 方法的功能是,将 x 容器中存储的所有元素全部移动当前 list 容器中
    position 指明的位置处。**整体拼接**
  + void splice (iterator position, list& x, iterator i);此格式的 splice() 方法的功能是将 x 容器中 i 指向的元素移动到当前容器中 position指明的位置处。**单元素拼接**
  + void splice (iterator position, list& x, iterator first, iterator last);此格式的 splice() 方法的功能是将 x 容器 [first, last)**左闭右开** 范围内所有的元素移动到当前容器 position 指明的位置处。**部分拼接**

  我们知道,list 容器底层使用的是链表存储结构,splice() 成员方法移动元素的方式是,将存储该元素的节点从 list 容器底层的链表中摘除,然后再链接到当前 list 容器底层的链表中。这意味着,当使用 splice() 成员方法将 x 容器中的元素添加到当前容器的同时,该元素会从 x 容器中删除。
  
+ **list删除操作： **list::pop_front()、list::pop_back()、list::erase()、list::clear()、list::remove()、list::unique()、list::remove_if()，接下来着重介绍区别于顺序容器的操作。

  *list::remove(val)*：删除容器中所有等于 val 的元素。*list::unique()*：删除容器中相邻的重复元素,只保留一份。通过调用无参的 unique(),仅能删除相邻重复(也就是相等)的元素,**而通过我们自定义去重的规则,可以更好的满足在不同场景下去重的需求**。

  ```c++
  list<char> t3{'a','a','a','b','a','a','c','c','d','d','d','a'};
      for(auto i:t3)cout<<i<<" ";cout<<endl;
      t3.unique();
      for(auto i:t3)cout<<i<<" ";cout<<endl;
  
  //result：
  //a a a b a a c c d d d a 
  //a b a c d a 
  ```

  *list::remove_if()*：删除容器中满足条件的元素。通过将自定义的谓词函数(不限定参数个数)传给 remove_if() 成员函数,list 容器中能使谓词函数成立的元素都会被删除。

  ```c++
  list<int> t4{15,36,7,17,20,39,4,1};
      for(auto i:t4)cout<<i<<" ";cout<<endl;
      t4.remove_if([](int value){return value<10;});
      for(auto i:t4)cout<<i<<" ";cout<<endl;
  
  //result:
  //15 36 7 17 20 39 4 1 
  //15 36 17 20 39 
  ```

#### forward_list

+ forward_list 同list，擅长插入、删除操作，而访问没有array、list效率高。forward_list只能前向迭代，无rbegin、rend。

+ forward_list相对list而言，效率高，空间利用率高。效率高是选用forward_list 而弃用 list 容器最主要的原因,换句话说,只要是 list 容器和forward_list 容器都能实现的操作,应优先选择 forward_list 容器。

+ 相对list多了几个成员函数：

  **merge()**:合并两个事先已排好序的 forward_list 容器,并且合并之后的 forward_list 容器依然是有序的。

  sort():通过更改容器中元素的位置,将它们进行排序。

  ```c++
      forward_list<int> f1{1,3,2,7,5};
      forward_list<int> f2{4,1,8,9,0};
  //    for(auto i : f1)cout<<i<<" ";cout<<endl;
      f1.sort();
      for(auto i : f1)cout<<i<<" ";cout<<endl;
  //    for(auto i : f2)cout<<i<<" ";cout<<endl;
      f2.sort();
      for(auto i : f2)cout<<i<<" ";cout<<endl;
      f1.merge(f2);
      for(auto i : f1)cout<<i<<" ";cout<<endl;
  //result:
  //1 2 3 5 7 
  //0 1 4 8 9 
  //0 1 1 2 3 4 5 7 8 9 
  //如果不先进行排序,merage的结果
  //1 3 2 4 1 7 5 8 9 0 
  ```

  reverse():反转容器中元素的顺序。

+ **forward_list**不提供size()函数，为此想要得到当前容器元素的容量就用如下方法：

  ```c++
  std::forward_list<int> my_words{1,2,3,4};
  int count = std::distance(std::begin(my_words),std::end(my_words));
  cout << count;
  ```

+ **advance()**，forward_list 容器迭代器的移动除了使用 ++ 运算符单步移动,还能使用 advance() 函数

  ```c++
  //f1 = {9 8 7 5 4 3 2 1 1 0 }  
  forward_list<int>::iterator iter = f1.begin();
      advance(iter,3);
      for(iter;iter!=f1.end();iter++){
          cout<<*iter<<" ";
      }cout<<endl;
  //result:5 4 3 2 1 1 0
  ```


### Day3

关联容器：map、set、multimap、multiset

哈希容器：unordered_map、unordered_multimap、unordered_set、unordered_multiset

#### pair

+ 初始化：

  ```c++
      pair<string,string> p1;
      pair<string,string> p2("key","value");
      pair<string,string> p3(p2);
      cout<<p1.first<<" "<<p1.second;
      cout<<p2.first<<" "<<p2.second;
      cout<<p3.first<<" "<<p3.second;
  
  //result：
  // 
  //key value
  //key value
  ```

  其他初始化方式：调用移动构造、使用右值引用参数创建pair对象。

  *make_pair(&T a, &T b)*可以生成一个pair对象，也可用此来初始化。

  

+ pair 对象重载了 <、<=、>、>=、==、!= 这 6 的运算符，其运算规则是：对于进行比较的 2 个 pair 对象,先比较 pair.first 元素的大小,如果相等则继续比较 pair.second 元素的大小。

+ pair提供了swap()成员函数。

#### map 

map是一种关联容器，其存储的都是pair对象。

+ map内默认排序是对键值的大小进行升序排序。**实际运用过程中可以自定义排序规则，**声明map对象时可以在指定下面的类compare。

  ```c++
  template < class Key,	// 指定键(key)的类型
  	class T, // 指定值(value)的类型
  	class Compare = less<Key>, // 指定排序规则
  	class Alloc = allocator<pair<const Key,T> > // 指定分配器对象的类型
  	> class map;
  //eg：
  map<string,int,greater<string>> myMap;   //降序排列
  ```

  **map使用过程中键值是不可修改的**。即map中存储的是pair<const K,T>

  ```c++
  map<string,int>m1;
  map<string,int>m2{{"k1",1},{"k2",2},{"k3",3}};
  map<string,int>m3(m2);
  map<string,int>m4{make_pair("k4",4),make_pair("k5",5)};
  map<string,int>m5{++m2.begin(),m2.end()};
  map<string,int,greater<string>> m6{{"k6",6},{"k7",7}};
  ```

+ find(key)，返回对应键值迭代器。**map中迭代器是双向迭代器**

  ```c++
  map<string,int>m2{{"k1",1},{"k2",2},{"k3",3}};
  auto iter = m2.find("k2");
  if(iter!=m2.end())cout<<iter->first<<" "<<iter->second;
  //reuslt:
  //k2 2
  ```

+ lower_bound(key)，返回一个指向当前 map 容器中第一个大于或等于 key 的键值对的双向迭代器。*upper_bound(key)、equal_range(key)有类似功能。*

  **lower_bound(key) 和 upper_bound(key) 更多用于 multimap 容器,在 map 容器中很少用到。**因为map容器中各键值对都是唯一的。

+ 查询操作：当用operator[]查询对应键值对时，若容器不含有所查询的键值则会在容器中添加该键，其值编译器自动赋值。相反at()操作则不会创建不存在的键值对，而是抛出异常。**基本目前遇见的容器，at()这个方法都自带边界检测的功能，相应的效率较低。**

+ insert()、emplace()，插入操作：当使用 insert() 方法向 map 容器的指定位置插入新键值对时,其底层会先将新键值对插入到容器的指定位置,如果其破坏了 map 容器的有序性,该容器会对新键值对的位置进行调整。**指不指定插入位置都有可能会被重新排序。**

  ```c++
  //1、引用传递一个键值对
  pair<iterator,bool> insert(const value_type& val);
  //2、以右值引用的方式传递键值对（好像时用于临时变量操作的）
  pair<iterator,bool> insert (P&& val);
  //3、将其他map指定区域插入到当前map中
template <class InputIterator>
  void insert (InputIterator first, InputIterator last);
  //4、插入多个键值对
  void insert ({val 1 , val 2 , ...});
  //emplace,其中参数时指构造新键值对的数据参赛
  pair<iterator,bool> emplace(Args&&... args);
  map<string,int> m;
  m.emplace("key",100);
  ```
  
  返回一个迭代器和一个布尔变量，若成功，迭代器指向val。若失败，迭代器指向容器中相同键值对p，且不会改变p键值对的值。
  
+ erase(),删除 map 容器指定位置、指定键(key)值或者指定区域内的键值对。即通过键值删除，或者通过迭代器删除一个或指定区域。**用迭代器操作后注意即使更新迭代器的变量。当用迭代器操作时，erase会返回删除元素后的第一个元素的迭代器。**

+ 当实现“向 map 容器中添加新键值对元素”的操作时,insert() 成员方法的执行效率更高;而在实现“更新 map 容器指定键值对的值”的操作时,operator[ ] 的效率更高。实现相同的插入操作,**无论是用 emplace() 还是emplace_hint(),都比 insert() 方法的效率高。**

#### multimap

+ 和 map 容器的区别在于,multimap 容器中可以同时**存储多(≥2)个键相同的键值对**。

+ 和 map 容器相比,multimap 未提供 at() 成员方法,也没有重载 [] 运算符。

+ 相对map，lower_bound()、upper_bound()、equal_range() 以及 count() 成员方法会经常用到。

  ```c++
      multimap<char,int>mult { {'a',10},{'b',20},{'b',15}, {'c',30} };
      pair<multimap<char,int>::iterator,multimap<char,int>::iterator> iters = mult.equal_range('b');
      cout<<iters.first->first<<" "<<iters.first->second<<endl;
      cout<<iters.second->first<<" "<<iters.second->second<<endl;
      cout<<mult.count('b');
  //result
  //b 20
  //c 30
  //2
  ```

#### set

和 map、multimap 容器不同,使用 set 容器存储的各个键值对,要求键 key 和值 value 必须相等。所以没有at()、operator[]的成员函数。

+ 与map相同，set会对容器内的元素自动排序，且因为键与值是相同的，对键进行排序等同于对值进行排序。使用 set 容器存储的各个元素的值必须各不相同。
+ C++ 标准为了防止用户修改容器中元素的值,对所有可能会实现此操作的行为做了限制,使得在正常情况下,用户是无法做到修改 set 容器中元素的值的。切勿尝试直接修改 set 容器中已存储元素的值,这很有可能破坏 set 容器中元素的有序性,最正确的修改 set 容器中元素值的做法是:先删除该元素,然后再添加一个修改后的元素。
+ set的迭代器是双向的，set的迭代器不再指向键值对，而是元素，通过*iter可以获取迭代器对应元素的值。
+ insert()、emplace()、erase()、clear()操作方式与map相同
+ multiset 容器和 set 容器唯一的差别在于,multiset 容器允许存储多个值相同的元素,而 set 容器中只能存储互不相同的元素。

+ **关联容器的排序规则**

  重点是自定义个排序规则，定义排序规则的方式可以有：函数对象（重载operator()）、函数（传参时传函数指针）、重载关系运算符实现自定义排序。

  自定义规则时注意，参数为两个容器元素类型的变量a,b，函数返回bool变量，返回true把a排b前，返回false把a排b后。

  ```c++
  set<string,compare> myset{"test","temporary","a"};
  for(auto iter=myset.begin();iter!=myset.end();iter++){
      cout<<*iter<<" ";
  }cout<<endl;
  //注意由于是以字符串的长度为准进行排序,因此其无法存储相同长度的多个字符串。
  ```

+ **修改键值对的键**

  所有关联式容器可以采用同一种解决思路,即先删除该键值对,然后再向容器中添加修改之后的新键值对。

  map 和 multimap 容器用于存储类型为 pair<**const** K, V> 的键值对。显然,只要目标键值对存储在当前容器中,键的值就无法被修改。

  总之,set 和 multiset 容器的元素类型没有用 const 修饰。所以从语法的角度分析,我们可以直接修改容器中元素的值,但一定不要修改元素的键。


### Day 4

哈希容器：unordered_map、unordered_multimap、unordered_set、unordered_multiset

关联式容器的底层实现采用的树存储结构,更确切的说是红黑树结构，无序容器的底层实现采用的是哈希表的存储结构。



