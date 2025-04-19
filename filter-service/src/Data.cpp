#include "Headers/Data.h"

// Constructor
Data::Data(BloomFilter& bloomFilter, int client_sock, map<string, ICommand*> commands)
: bloomFilter(bloomFilter), client_sock(client_sock), commands(commands) {}
    
// Destructor 
Data::~Data() {}