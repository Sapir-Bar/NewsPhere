#include <iostream>
#include "Headers/bloomFilterArray.h"

/*
intended to be an array of zeros and ones for the bloom filter,
 initialized with zeros,
 and is changed by entering a "bad" address.
*/
    
// Constructor
BloomFilterArray::BloomFilterArray(int size) {
    // Initialize the array with zeros
    this->size = size;
    this->array = new int[size];
    initializeArray();
}

// Destructor to free memory
BloomFilterArray::~BloomFilterArray() {
    delete[] this->array;
}

// Function to initialize the array with zeros
void BloomFilterArray::initializeArray() {
    for (int i = 0; i < this->size; ++i) {
        this->array[i] = 0;
    }
}

//when adding a "bad" url
void BloomFilterArray::add(int place) {
        if (place >= 0 && place < size) {
        this->array[place] = 1;
    } else {
        //todo we want that? Replace it with a test?
        std::cerr << "Index out of bounds!" << std::endl;
    }
}

int BloomFilterArray::get(int place){
        return this->array[place];
}
