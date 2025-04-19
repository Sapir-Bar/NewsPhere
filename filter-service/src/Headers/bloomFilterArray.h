#ifndef BLOOMFILTERARRAY_H
#define BLOOMFILTERARRAY_H

#include<iostream>

class BloomFilterArray{
    private:
       int* array;
       int size;
       
    public:
    BloomFilterArray(int size);
    ~BloomFilterArray();
    void initializeArray();
    void add(int place);
    int get(int place);
};

#endif 