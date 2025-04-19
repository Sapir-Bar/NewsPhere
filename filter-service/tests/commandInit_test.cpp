#include <gtest/gtest.h>
#include "../src/commandInit.cpp"
#include "../src/Headers/InputValidator.h"
#include<string>

TEST(InitializionTest2, Extraction) {
    char* s =  const_cast<char*>("1 https://github.com/Tamarmic/FooBar");
    CommandInit ci;
    InputValidator* ci_ptr = &ci;

    EXPECT_EQ(ci_ptr->extract(s, strlen(s)).at(0), "1");
    EXPECT_EQ(ci_ptr->extract(s, strlen(s)).at(1), "https://github.com/Tamarmic/FooBar");

}

TEST(InitializionTest2, Validation) {
    char* s1 = const_cast<char*>("1 https://github.com/Tamarmic/FooBar");
    char* s2 = const_cast<char*>("a https://github.com/Tamarmic/FooBar");
    CommandInit ci;
    InputValidator* ci_ptr = &ci;

    EXPECT_EQ(ci_ptr->validateCode(s1, strlen(s1)), true);
    EXPECT_EQ(ci_ptr->validateCode(s2, strlen(s2)), false);
}

