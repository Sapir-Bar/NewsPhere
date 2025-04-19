#ifndef ICOMMAND_H
#define ICOMMAND_H
#pragma once

#include "bloomFilter.h"
#include <string>
#include <iostream>

using namespace std;

class ICommand {
public:
    virtual string execute(string Url, BloomFilter& bloomFilter) const = 0;
};

#endif
