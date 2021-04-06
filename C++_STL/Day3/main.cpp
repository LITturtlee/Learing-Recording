#include <iostream>
#include <utility>
#include <string>
#include <map>
//#define PAIR
#define MAP

using namespace std;

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
#endif
    return 0;
}
