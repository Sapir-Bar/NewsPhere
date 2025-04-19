#ifndef HASH_FUNCTION
#define HASH_FUNCTION
#pragma once

#include <iostream>

class HashFunction { 
private:
        int bloomFilterSize;
        int numOfConcatenation;

public:
        HashFunction(int Size, int Concatenation);
        int hash(std:: string URL);
};

#endif