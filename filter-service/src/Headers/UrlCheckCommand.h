#ifndef URLCHECKCOMMAND_H
#define URLCHECKCOMMAND_H
#pragma once

#include<string>
#include "bloomFilter.h"
#include "ICommand.h"
#include <iostream>

class UrlCheckCommand : public ICommand{

    public:
    UrlCheckCommand();
    virtual string execute(string Url, BloomFilter& bloomFilter) const override;   

};

#endif