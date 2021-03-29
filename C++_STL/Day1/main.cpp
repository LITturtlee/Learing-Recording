#include <iostream>
#include <cstring>
#include <array>
#include <vector>

//#define  ARRAY
#define  VECTOR
using  namespace std;



int main() {
#ifdef ARRAY
    //array could be understood as fixed vector
    array<int,5> myarray = {1,10,3,4,5};
    for(auto it = myarray.begin();it != myarray.end(); it++){
        cout<< " " << *it ;
        *it = *it * 2;
    }
    cout <<endl;
    for(auto it = myarray.rbegin();it != myarray.rend();it++){
        cout<< " " << *it ;
    }
    cout << endl;
    for(auto it = myarray.cbegin();it != myarray.cend();it++){
        // *it = *it * 2;     error: assignment of read-only location ‘* it’
        cout<< " " << *it;
    }
    cout<< endl;


    array<int,10> myints;
    cout << "size of myints : " << myints.size() << endl;
    cout << "sizeof(myints) : " << sizeof(myints) << endl;
    cout << "maxsize of myints : " << myints.max_size() << endl;     //for array max_size() same as size()


    array<int,5> A;
    array<int,0> B;
    cout<<A.empty()<<endl;                     //empty()  return isempty
    cout<<B.empty()<<endl;


    for(int i=0; i<6; i++){
        cout << " " <<myarray[i];
    }cout<<endl;
//    for(int i=0; i<6; i++){
//        cout<< " " << myarray.at(i);      //will throw err
//    }cout<<endl;

    cout << myarray.front() << endl;
    cout << myarray.back() << endl;
    myarray.front() = 100;

    const char* cstr = "helo world";
    array<char,10> mychars;
    memcpy(mychars.data(),cstr,10);       //when n = 5  out:helo ��U
    cout<<mychars.data()<<endl;

    array<int,5> first = {19,34,66,42,1};
    array<int,5> second = {2,3,5,1,0};
    first.swap(second);              //only same size and same type
    for(int& x:first) cout<<" "<<x;cout<<endl;
    for(int& x:second) cout<<" "<<x;cout<<endl;

    cout<< "first element in myarray : " << get<0>(myarray)<<endl;

    array<int,5> a = {1,2,3,4,5};
    array<int,5> b = {1,2,3,4,5};
    array<int,5> c = {5,4,3,2,1};
    if(a==b) cout << "a == b" <<endl;
    if(a==c) cout << "a == c" <<endl;
    if(a!=c) cout << "a != c" <<endl;
    if(a<c) cout << "a < c" <<endl;


#endif

#ifdef VECTOR
    vector<int> v1;
    vector<int> v2(5,55);
    vector<int> v3;
    v3.resize(10);
    vector<int> v4;
    v4.reserve(10);
    vector<int> v5(v2.begin(),v2.end());
    cout<<"size of v1 : "<<v1.size()<<"   "<<"size of v2 : "<<v2.size()<<"   "<<"size of v3 : "<<v3.size()<<"   "<<"size of v4 : "<<v4.size()<<"   "<<endl;
    cout<<"max_size of v1 : "<<v1.max_size()<<"   "<<"max_size of v2 : "<<v2.max_size()<<"   "<<"max_size of v3 : "<<v3.max_size()<<"   "<<"max_size of v4 : "<<v4.max_size()<<"   "<<endl;
    cout<<"capacity of v1 : "<<v1.capacity()<<"   "<<"capacity of v2 : "<<v2.capacity()<<"   "<<"capacity of v3 : "<<v3.capacity()<<"   "<<"capacity of v4 : "<<v4.capacity()<<"   "<<endl;
    cout<<"v1: ";for(auto  it= v1.begin();it!=v1.end();it++)cout<<*it<<" ";cout<<endl;
    cout<<"v2: ";for(auto  it= v2.begin();it!=v2.end();it++)cout<<*it<<" ";cout<<endl;
    cout<<"v3: ";for(auto  it= v3.begin();it!=v3.end();it++)cout<<*it<<" ";cout<<endl;
    cout<<"v4: ";for(auto  it= v4.begin();it!=v4.end();it++)cout<<*it<<" ";cout<<endl;
    for(int i=0;i<5;i++){
        v1.push_back(i);v2.push_back(i);v3.push_back(i);v4.push_back(i);
    }
    cout<<"size of v1 : "<<v1.size()<<"   "<<"size of v2 : "<<v2.size()<<"   "<<"size of v3 : "<<v3.size()<<"   "<<"size of v4 : "<<v4.size()<<"   "<<endl;
    cout<<"max_size of v1 : "<<v1.max_size()<<"   "<<"max_size of v2 : "<<v2.max_size()<<"   "<<"max_size of v3 : "<<v3.max_size()<<"   "<<"max_size of v4 : "<<v4.max_size()<<"   "<<endl;
    cout<<"capacity of v1 : "<<v1.capacity()<<"   "<<"capacity of v2 : "<<v2.capacity()<<"   "<<"capacity of v3 : "<<v3.capacity()<<"   "<<"capacity of v4 : "<<v4.capacity()<<"   "<<endl;
    cout<<"v1: ";for(auto  it= v1.begin();it!=v1.end();it++)cout<<*it<<" ";cout<<endl;
    cout<<"v2: ";for(auto  it= v2.begin();it!=v2.end();it++)cout<<*it<<" ";cout<<endl;
    cout<<"v3: ";for(auto  it= v3.begin();it!=v3.end();it++)cout<<*it<<" ";cout<<endl;
    cout<<"v4: ";for(auto  it= v4.begin();it!=v4.end();it++)cout<<*it<<" ";cout<<endl;
    v1.shrink_to_fit();v2.shrink_to_fit();v3.shrink_to_fit();v4.shrink_to_fit();
    cout<<"size of v1 : "<<v1.size()<<"   "<<"size of v2 : "<<v2.size()<<"   "<<"size of v3 : "<<v3.size()<<"   "<<"size of v4 : "<<v4.size()<<"   "<<endl;
    //cout<<"max_size of v1 : "<<v1.max_size()<<"   "<<"max_size of v2 : "<<v2.max_size()<<"   "<<"max_size of v3 : "<<v3.max_size()<<"   "<<"max_size of v4 : "<<v4.max_size()<<"   "<<endl;
    cout<<"capacity of v1 : "<<v1.capacity()<<"   "<<"capacity of v2 : "<<v2.capacity()<<"   "<<"capacity of v3 : "<<v3.capacity()<<"   "<<"capacity of v4 : "<<v4.capacity()<<"   "<<endl;
    cout<<"v1: ";for(auto  it= v1.begin();it!=v1.end();it++)cout<<*it<<" ";cout<<endl;
    cout<<"v2: ";for(auto  it= v2.begin();it!=v2.end();it++)cout<<*it<<" ";cout<<endl;
    cout<<"v3: ";for(auto  it= v3.begin();it!=v3.end();it++)cout<<*it<<" ";cout<<endl;
    cout<<"v4: ";for(auto  it= v4.begin();it!=v4.end();it++)cout<<*it<<" ";cout<<endl;
    for(int i=0;i<v1.size();i++){
        cout<<*(v1.data()+i);
    }

#endif
    return 0;
}



