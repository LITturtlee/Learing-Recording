#include <iostream>
#include <deque>
#include <algorithm>

using namespace std;

int main() {
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




    return 0;
}
