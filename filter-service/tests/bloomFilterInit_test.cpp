#include <gtest/gtest.h>
#include "../src/bloomFilterInit.cpp"
#include "../src/InputValidator.cpp"
#include<string>

TEST(InitializionTest, Extraction) {
    char *inputString = const_cast<char*>("8 1 2");

    BloomFilterInit bf;
    InputValidator* bf_ptr = &bf;
    string s = bf_ptr->extract(inputString, strlen(inputString)).at(0);

    EXPECT_EQ(s, "8");
}

TEST(InitializionTest, Validation) {
    char *s1 = const_cast<char*>("1 2");
    char *s2 = const_cast<char*>("1 a");

    BloomFilterInit bf;
    InputValidator* bf_ptr = &bf;

    EXPECT_EQ(bf_ptr->validateCode(s1, strlen(s1)), true);
    EXPECT_EQ(bf_ptr->validateCode(s2, strlen(s2)), false);
}

