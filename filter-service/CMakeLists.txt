cmake_minimum_required(VERSION 3.14)
project(FooBar)

set(CMAKE_CXX_STANDARD 14)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

#cmake_policy(SET CMP0135 NEW)
include(FetchContent)
FetchContent_Declare(
    googletest
    URL
https://github.com/google/googletest/archive/03597a01ee50ed
33e9dfd640b249b4be3799d395.zip
)

set(FetchContent_DOWNLOAD_EXTRACT_TIMESAMP true)
enable_testing()

include_directories(src/Headers)

set(SOURCE_FILES
src/bloomFilter.cpp
src/bloomFilterArray.cpp
src/runner.cpp
src/HashFunction.cpp
src/ICommand.cpp
src/InputValidator.cpp
src/Main.cpp
src/UrlAddCommand.cpp
src/UrlCheckCommand.cpp
src/bloomFilterInit.cpp
src/commandInit.cpp
src/Data.cpp
)

set(TEST_FILES
tests/test_main.cpp 
tests/array_test.cpp 
tests/bloomfilter_test.cpp 
tests/bloomFilterInit_test.cpp
tests/hash_test.cpp
tests/commandInit_test.cpp
)

add_executable(BloomFilterTests ${TEST_FILES})
#add_executable(myprogram ${SOURCE_FILES})

# Link Google Test and pthread
target_link_libraries(BloomFilterTests PRIVATE gtest gtest_main pthread)

# Include the source directory for tests
target_include_directories(BloomFilterTests PRIVATE src)

include(GoogleTest)
add_test(NAME BloomFilterTests COMMAND BloomFilterTests)
