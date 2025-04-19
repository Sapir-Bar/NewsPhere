FROM gcc:latest

WORKDIR /usr/src/exl

COPY ./src/ .

RUN g++ -o exl -pthread ./HashFunction.cpp ./bloomFilterArray.cpp ./bloomFilter.cpp ./bloomFilterInit.cpp ./commandInit.cpp ./ICommand.cpp ./InputValidator.cpp ./runner.cpp ./UrlAddCommand.cpp ./UrlCheckCommand.cpp ./Data.cpp ./Main.cpp
EXPOSE 5555
CMD ["./exl"]
