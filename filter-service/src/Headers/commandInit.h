#ifndef COMMANDINIT_H
#define COMMANDINIT_H
#pragma once

#include "InputValidator.h"
#include<string>

class CommandInit : public InputValidator {
public:

    //Constructor
    CommandInit();
    virtual bool validateCode(char* userInput, int read_bytes) const;    
};

#endif 
