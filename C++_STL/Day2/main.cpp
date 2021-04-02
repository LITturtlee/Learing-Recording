#include <iostream>
#include <deque>
#include <list>
#include <algorithm>


using namespace std;
//#define DEQUE
#define LIST

int main() {
#ifdef DEQUE
    int array[] =  {1,2,3,4,5};
    deque<int> d1 = {66,77,88,99,100};
    deque<int> d2(d1);
    deque<int> d3(5,0);
    deque<int> d4(d1.begin(),d1.end());
    deque<int> d5(array,array+5);
    cout<<"d1 : ";for(auto i:d1)cout<<i<<" ";cout<<endl;
    cout<<"d2 : ";for(auto i:d2)cout<<i<<" ";cout<<endl;
    cout<<"d3 : ";for(auto i:d3)cout<<i<<" ";cout<<endl;
    cout<<"d4 : ";for(auto i:d4)cout<<i<<" ";cout<<endl;
    cout<<"d5 : ";for(auto i:d5)cout<<i<<" ";cout<<endl;
    cout<<"======================"<<endl;
    d1.push_back(16);d1.pop_back();
    d2.push_front(49);d2.pop_front();
    d3.emplace(d3.begin()+1,1234);
    d4.emplace_back(999);
    d5.emplace_front(1111);
    cout<<"d1 : ";for(auto i:d1)cout<<i<<" ";cout<<endl;
    cout<<"d2 : ";for(auto i:d2)cout<<i<<" ";cout<<endl;
    cout<<"d3 : ";for(auto i:d3)cout<<i<<" ";cout<<endl;
    cout<<"d4 : ";for(auto i:d4)cout<<i<<" ";cout<<endl;
    cout<<"d5 : ";for(auto i:d5)cout<<i<<" ";cout<<endl;
    d1.front() = 888;
    cout<<"d1 front and d1 back: "<<d1.front()<<" "<<d1.back()<<endl;
    cout<<"=========================="<<endl;
    d1.erase(d1.begin(),d1.begin()+2);
    swap(*(d3.begin()+1),*(d3.end()-1));
    d3.pop_back();
    cout<<"d1 : ";for(auto i:d1)cout<<i<<" ";cout<<endl;
    cout<<"d3 : ";for(auto i:d3)cout<<i<<" ";cout<<endl;
    cout<<"=========================="<<endl;
    d5.insert(d5.begin(),{1,2,3,4});
    cout<<"d5 : ";for(auto i:d5)cout<<i<<" ";cout<<endl;
#endif
#ifdef LIST
//create list
    list<int> l0 = {1,3,5,7,9};
    list<int> l1{1,2,3,4,5};
    list<int> l2(5);
    list<int> l3(5,6);
    list<int> l4(l1);
    int array[] = {0,2,4,8,10};
    list<int> l5(array,array+5);
    deque<int> d{11,12,13,14,15};
    list<int> l6(d.begin(),d.end());
    for(auto iter=l0.begin();iter!=l0.end();iter++)cout<<*iter<<" ";cout<<endl;
    for(auto iter=l1.begin();iter!=l1.end();iter++)cout<<*iter<<" ";cout<<endl;
    for(auto iter=l5.begin();iter!=l5.end();iter++)cout<<*iter<<" ";cout<<endl;
    for(auto iter=l6.begin();iter!=l6.end();iter++)cout<<*iter<<" ";cout<<endl;
    list<int>::iterator begin = l0.begin();
    list<int>::iterator end = l0.end();
    list<int>::iterator second = ++l0.begin();
    list<int>::iterator third =++(++l0.begin());
    l0.insert(second,99);
    while (begin!=end){cout<<*begin<<" ";++begin;}cout<<endl;
    begin = l0.begin();
    l0.erase(third);
    while (begin!=end){cout<<*begin<<" ";++begin;}cout<<endl;
    cout<<l6.front()<<"  "<<l6.back()<<endl;
    l6.front() = 999,l6.back() = 666;
    cout<<l6.front()<<"  "<<l6.back()<<endl;


    //==============splice()=====================
    //void splice (iterator position, list& x);
    //void splice (iterator position, list& x, iterator i);
    //void splice (iterator position, list& x,iterator first, iterator last);
    list<int> x1= {51,52,53},x2= {51,52,53},x3 = {51,52,53};
    list<int> y1= {1,2,3},y2= {1,2,3},y3 = {1,2,3};
    y1.splice(y1.begin(),x1);
    y2.splice(y2.begin(),x2,--x2.end());
    y3.splice(y3.begin(),x3,++x3.begin(),x3.end());
    for(auto i :y1){cout<<i<<" ";}cout<<endl;
    for(auto i :y2){cout<<i<<" ";}cout<<endl;
    for(auto i :y3){cout<<i<<" ";}cout<<endl;
    cout<<x1.size()<<" "<<x2.size()<<" "<<x3.size()<<endl;


#endif



    return 0;
}
