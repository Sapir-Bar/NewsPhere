#include <gtest/gtest.h>
#include "../src/Headers/HashFunction.h"

TEST(hash_functionTest, BasicTest) {
   HashFunction myHashFunction(8, 3); 
   EXPECT_EQ(myHashFunction.hash("xxx"), 2);
}