#ifndef INPUTVALIDATOR_H
#define INPUTVALIDATOR_H
#pragma once

#include <string>
#include <iostream>
#include <sstream>
#include <vector>

using namespace std;

class InputValidator {

public:
    virtual vector<string> extract(char* userInput, int read_bytes) const;
    virtual bool validateCode(char* userInput, int read_bytes) const = 0;
};
#endif 