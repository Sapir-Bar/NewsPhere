# Bloom Filter TCP Server

## Overview

This is a C++ TCP server that offers Bloom filter service. It utilizes a Bloom filter to efficiently check whether a given address belongs to a blacklist.

## Prerequisites

Before running the program, ensure that you have the following dependencies installed:
1. [Visual Studio Code](#for-visual-studio-code-and-git)
2. [Git](#for-visual-studio-code-and-git)
3. [Docker](#for-docker)
4. [CMake](#for-cmake-and-gtest)
5. [gtest](#for-cmake-and-gtest)

## Installation

### For Visual Studio Code and Git:
1. Install [Visual Studio](https://git-scm.com/) Code from the official website.
2. Install [Git](https://git-scm.com/) from the official website.
3. Clone this repository to your local machine:

```bash
git clone https://github.com/Tamarmic/FooBar.git
```

### For Docker:
Install Docker from the official website.
Build the Docker image:

```bash
docker build -t exl_image .
```

### For CMake and gtest:
1. Install CMake:

```bash
sudo apt-get update
sudo apt-get install cmake
```

2. Install gtest:
```bash
sudo apt-get install libgtest-dev cmake
```
## Usage

### Running the program with g++

1. Navigate to the source directory:

```bash
cd /path/to/source/directory
```

2. Compile the program using g++:

```bash
g++ ./HashFunction.cpp ./bloomFilterArray.cpp ./bloomFilter.cpp ./bloomFilterInit.cpp ./commandInit.cpp ./ICommand.cpp ./InputValidator.cpp ./runner.cpp ./UrlAddCommand.cpp ./UrlCheckCommand.cpp ./Data.cpp ./Main.cpp -o bloom_filter_server -pthread
```

3. Run the program:

```bash
./bloom_filter_server
```

### Running the program with Docker:

1. Build the Docker image (if not done already):

```bash
docker build -t exl_image .
```

2. Run the Docker container:

```bash
docker run -it -p 5555:5555 exl_image
```

### Running the tests with CMake:
1. Install gtest (if not done already, see Installation section).
2. Generate build files using CMake:
```bash
cmake -B build -S .
```
3. Navigate to the build directory:
```bash
cd build
```
4. Build the tests:
```bash
 cmake --build .
 ```
5. Run the tests:
```bash
ctest --output-on-failure
 ```
## Usage Instructions
- The server runs on port 5555 by default.
- Upon starting, the server expects the following parameters on a single line:
    1. Array size
    2. At least 1 hase function. 
- After receiving the parameters, the server expects requests in the following format:
    - 1 {address}: Add the address to the filter
    - 2 {address}: Check whether the address belongs to the blacklist




