#include <gtest/gtest.h>
#include "../src/bloomFilterArray.cpp"

TEST(ArrayTest, BasicTest1) {
   BloomFilterArray array = BloomFilterArray(2);
    //Checks if initially Harry contains only zeros
   EXPECT_EQ(array.get(0),0);
   EXPECT_EQ(array.get(1),0);
    //Add one and check that it is only added to the right place
    array.add(1);
   EXPECT_EQ(array.get(0),0);
   EXPECT_EQ(array.get(1),1);
}