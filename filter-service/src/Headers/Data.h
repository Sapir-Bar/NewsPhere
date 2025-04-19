#ifndef DATA_H
#define DATA_H
#pragma once
#include "bloomFilter.h"
#include "ICommand.h"
#include <map>
#include <string>

using namespace std; 

class Data {
public: 
    Data(BloomFilter& bloomFilter, int client_sock, map<string, ICommand*> commands);
    ~Data();
    BloomFilter& bloomFilter; 
    int client_sock; 
    map<string, ICommand*> commands;
};

#endif 
