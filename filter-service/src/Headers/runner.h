#ifndef RUNNER_H
#define RUNNER_H
#pragma once

#include <string>
#include <vector>
#include <map>
#include <iostream>
#include <arpa/inet.h>
#include <unistd.h>
#include <string.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include "ICommand.h"
#include "InputValidator.h"
#include "bloomFilter.h"
#include "bloomFilterInit.h"
#include "commandInit.h"
#include "Data.h"

using namespace std;

class Runner {

public:
    static BloomFilter* init(int client_sock); 
    static BloomFilter* initialization(vector<string> validInput);
    static void* handle_client(void* param); 
};

#endif





