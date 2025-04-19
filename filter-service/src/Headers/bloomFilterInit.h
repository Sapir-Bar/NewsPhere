#ifndef BLOOMFILTERINIT_H
#define BLOOMFILTERINIT_H
#pragma once

#include "InputValidator.h"
#include<string>

class BloomFilterInit : public InputValidator {
public:
    BloomFilterInit();
    virtual bool validateCode(char* userInput, int read_bytes) const;                                                        
};

#endif 