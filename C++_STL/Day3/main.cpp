#include <iostream>
#include <utility>
#include <string>
#include <vector>
#include <map>
#include <algorithm>
#include <set>
//#define PAIR
//#define MAP
//#define MULTIMAP
#define SET

using namespace std;
class compare{
public:
    bool operator()(const string& a,const string& b){
        return (a.length()<b.length());
    }
};
class student{
public:
    student(string name, int id, int age):name(name),id(id),age(age){

    }
    const int& getid() const{
        return id;
    }
    void setname(const string name){
        this->name = name;
    }
    string getname() const{
        return this->name;
    }
    void setage(int age){
        this->age = age;
    }
    int getage() const{
        return  age;
    }
    void display() const{
        cout<<id<<" "<<name<<" "<<age<<endl;
    }
private:
    string name;
    int id;
    int age;
};
class stucmp{
public:
    bool operator()(const student& a,const student& b){
        return a.getid()<b.getid();
    }
};
class mystr{
    friend bool operator < (const mystr& a,const mystr& b);
public:
    mystr(string tempstr):str(tempstr){}
    string getstr() const{
        return str;
    }
private:
    string str;
};
bool operator < (const mystr& a,const mystr& b){
    return a.str.length()<b.str.length();
}

int main() {
#ifdef PAIR
//    =============init==============
    pair<string,string> p1;
    pair<string,string> p2("key","value");
    pair<string,string> p3(p2);
    cout<<p1.first<<" "<<p1.second<<endl;
    cout<<p2.first<<" "<<p2.second<<endl;
    cout<<p3.first<<" "<<p3.second<<endl;
    p1.first = "new key",p1.second = "new value";
    cout<<p1.first<<" "<<p1.second<<endl;
    p1 = make_pair("1","2");
    cout<<p1.first<<" "<<p1.second<<endl;
    p1.swap(p2);
#endif
#ifdef MAP
//    ===========init==============
    map<string,int>m1;
    map<string,int>m2{{"k1",1},{"k2",2},{"k3",3}};
    map<string,int>m3(m2);
    map<string,int>m4{make_pair("k4",4),make_pair("k5",5)};
    map<string,int>m5{++m2.begin(),m2.end()};

    map<string,int,greater<string>> m6{{"k6",6},{"k7",7}};

//    ==========find(key)===============
    auto iter = m2.find("k2");
    if(iter!=m2.end())cout<<iter->first<<" "<<iter->second<<endl;
//    cout<<m2.find("k2")->first
    for(auto i : m2){
        cout<<i.second<<" ";
    }cout<<endl;
    iter = m2.lower_bound("k2");
    cout<<iter->second<<endl;
//  ===========insert()===============
//  ===========emplace()===============
    m1.emplace("key",100);
//  ===========erase()===============
    iter = m2.begin();
    cout<<iter->first<<endl;
    m2.erase("k1");
    cout<<iter->first<<endl;
    cout<<m2.begin()->first<<endl;
    m2.erase(m2.begin());
    cout<<m2.begin()->first<<endl;
    m4.erase(++m4.begin(),m4.end());
    for(auto iter=m4.begin();iter!=m4.end();iter++){
        cout<<iter->first<<" ";
    }

#endif
#ifdef MULTIMAP
//    multimap<char,int>mult { {'a',10},{'b',20},{'b',15}, {'c',30} };
////    multimap<int,vector<int>>{{10,{1,9}}};
//    pair<multimap<char,int>::iterator,multimap<char,int>::iterator> iters = mult.equal_range('b');
//    cout<<iters.first->first<<" "<<iters.first->second<<endl;
//    cout<<iters.second->first<<" "<<iters.second->second<<endl;
//    cout<<mult.count('b');
    multimap<int,vector<int>> m{{10,{1,9}},{10,{3,7}},{10,{2,8}},{10,{2,8}},{10,{0,10}}};
    for(auto  i:m){
        cout<<i.first<<" "<<i.second[0]<<" "<<i.second[1]<<endl;
    }
    multimap<int,int> m2{{1,2},{1,5},{2,6},{2,9},{2,7}};
    multimap<int,int> u;
    for(auto  it = m2.begin();it!=m2.end();it++){
        cout<<it->first<<" "<<it->second<<endl;
        u.emplace(pair<int,int>(it->second,it->first));
    }cout<<endl;
    for(auto i:u){
        cout<<i.first<<" "<<i.second<<endl;
    }
//    sort(m.begin(),m.end(),[=](pair<int,vector<int>> a,pair<int,vector<int>> b){      error
//        return a.second[0]<b.second[0];
//    });

#endif


#ifdef SET
//    ==============init===============
    set<int> s1;
    set<int> s2{5,3,2,1,4,6};
    set<int> s3(s2);
    set<int> s4(s2.begin(),--s2.end());
    set<int,greater<int>> s5{1,2,3,4,5,6};
    cout<<*(s2.begin())<<endl;

//    std::set<std::string> myset{ "java",
//                                 "stl",
//                                 "python"
//    };
//    for(auto iter=myset.begin();iter!=myset.end();++iter){
//        cout<<*iter<<endl;
//    }
//  ===================erase()================
    set<int>::iterator iter = s2.erase(s2.begin()); //删除元素 1,myset={3,4,5}
    cout << "s2 size = " << s2.size() << endl;
    cout << "iter->" << *iter << endl;
    s2.clear();
    cout<<s2.size()<<endl;
//    ===================custom_sort==============
    set<string,compare> myset{"test","temporary","a"};
    for(auto iter=myset.begin();iter!=myset.end();iter++){
        cout<<*iter<<" ";
    }cout<<endl;
//   ================set of student =================
    set<student,stucmp> stus{{"zhangsan",2,88},{"lisi",1,100},{"wangwu",3,60}};
    set<student>::iterator it = stus.begin();
    for(;it!=stus.end();it++){
        (*it).display();
    }
//    =============== set of mystr =============
    set<mystr> mst;
    mst.emplace("ttt");
    mst.emplace("t");
    mst.emplace("taaaaaatt");
    for(auto iter=mst.begin();iter!=mst.end();iter++){
        cout<<iter->getstr()<<" ";
    }cout<<endl;

#endif
    return 0;
}
