#include "Headers/bloomFilter.h"
    
// Constructor
BloomFilter::BloomFilter(int arraySize, list<HashFunction>& functions)
: blackList(), bfArray(arraySize), myFunctions(functions) {}
    
// Destructor 
BloomFilter::~BloomFilter() {}
// delete blackList; no need to delete list<string>- is black list need to be somthing else?

string BloomFilter::addAddress(string URL){
    //update the BloomFilter Array with all the hash attacted
    for (auto& hashFunc : myFunctions) {
        bfArray.add(hashFunc.hash(URL));
    }
    blackList.push_back(URL);
    return URL;
}

string BloomFilter::checkAddress(string URL){
    string willPrint;
    //Checks whether a function is blacklisted
    //Checks for each function from the list if the value in the array is one
    bool unsafe = true;
        for (
            auto& hashFunc : this->myFunctions) {
        if (this->bfArray.get(hashFunc.hash(URL))==0)
        {
            //If there is a negative value -
            // the address is not in the list
            willPrint ="false"; 
            unsafe=false;
            break;
        }
    }
    if (unsafe) { willPrint = "true " + verifyAddress(URL); }
    return willPrint;
}

string BloomFilter::verifyAddress(string URL) {
    for (const auto& address : blackList) {
        if (address == URL) { return "true"; }
    }
    return "false";
}

