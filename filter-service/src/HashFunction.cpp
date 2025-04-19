#include "Headers/HashFunction.h"

// Constructor
HashFunction::HashFunction(int size, int concatenation) : bloomFilterSize(size), numOfConcatenation(concatenation){}

// Destructor (no need right now).
//  ~HashFunction() {}
// Destructor implementation (if needed in the future).

int HashFunction::hash(std:: string URL){
//<std::string> = In order to make sure that the first time you get a URL that is a string.
// {} (URL) = Initialization and variable input.
// size_t = only  non-negative integers.
size_t update_hash = std::hash<std::string> {} (URL);
//if there is more then one hash
for(int i = 1 ; i < numOfConcatenation ; i++){
        size_t update_hash = std::hash<size_t> {} (update_hash);
}

return update_hash%bloomFilterSize;
}

 