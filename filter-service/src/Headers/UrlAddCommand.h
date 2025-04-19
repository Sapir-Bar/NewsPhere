#ifndef URLADDCOMMAND_H
#define URLADDCOMMAND_H
#pragma once

#include<string>
#include "bloomFilter.h"
#include "ICommand.h"
#include <iostream>

using namespace std;

class UrlAddCommand : public ICommand{

    public:
    UrlAddCommand();
    virtual string execute(string Url, BloomFilter& bloomFilter) const override;   

};

#endif // URLADDCOMMAND_H