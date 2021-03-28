#include <iostream>
#include <cstring>
#include <array>

using  namespace std;

int main() {
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

        return 0;
}
