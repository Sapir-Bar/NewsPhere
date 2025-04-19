#include <gtest/gtest.h>
#include "../src/bloomFilter.cpp"
#include "../src/HashFunction.cpp"
#include <list>

TEST(BloomFilter_Test, BasicTest) {
  list<HashFunction> hashFunctionsList;
  hashFunctionsList.push_back(HashFunction(8, 1));
  BloomFilter b(8, hashFunctionsList);
  EXPECT_EQ(b.checkAddress("TestC"), "false");
  b.addAddress("TestC");
  EXPECT_EQ(b.checkAddress("TestA"), "false");
  EXPECT_EQ(b.checkAddress("TestB"), "true false");
  EXPECT_EQ(b.checkAddress("TestC"), "true true");
}

