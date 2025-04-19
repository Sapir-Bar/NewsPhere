#ifndef BLOOM_FILTER_H
#define BLOOM_FILTER_H
#pragma once

#include <string>
#include <list>
#include <iostream>
#include "HashFunction.h"
#include "bloomFilterArray.h"

using namespace std;

class BloomFilter {
private:
    list<string> blackList;
    BloomFilterArray bfArray; 
    list<HashFunction> myFunctions;

public:
     BloomFilter(int arraySize, list<HashFunction>& functions);
    ~BloomFilter();
     string addAddress(string URL);
     string checkAddress(string URL); 
     string verifyAddress(string URL);
};

#endif 