#include<map>
#include<string>
#include <iostream>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <string.h>
#include <pthread.h>
#include "Headers/Data.h"
#include "Headers/ICommand.h"
#include "Headers/UrlAddCommand.h"
#include "Headers/UrlCheckCommand.h"
#include "Headers/runner.h"

using namespace std;

int main() {

        // Print the thread ID
        // std::cout << "Thread ID: " << std::this_thread::get_id() << std::endl;

        map<string, ICommand*> commands;
        UrlAddCommand add = UrlAddCommand();
        ICommand* UrlAddCommand = &add;
        commands["1"] = UrlAddCommand;

        UrlCheckCommand check = UrlCheckCommand();
        ICommand* UrlCheckCommand = &check;
        commands["2"] = UrlCheckCommand; 

        const int server_port = 5555;
        int sock = socket(AF_INET, SOCK_STREAM, 0);
        if (sock < 0) { perror("error creating socket"); }

        struct sockaddr_in sin;
        memset(&sin, 0, sizeof(sin));
        sin.sin_family = AF_INET;
        sin.sin_addr.s_addr = INADDR_ANY;
        sin.sin_port = htons(server_port);
        if (bind(sock, (struct sockaddr*) &sin, sizeof(sin)) < 0)  {
            perror("error binding socket");
        }
        if (listen(sock, 20) <  0)  {       
            perror("error listening to a socket");
        }
        struct sockaddr_in client_sin;
        unsigned int addr_len = sizeof(client_sin);

        // bloom filter initializtion
        BloomFilter* bloomFilter = nullptr;
        int client_sock = accept(sock,  (struct sockaddr*) &client_sin, &addr_len);
            if (client_sock <  0) {
                perror("error accepting client");
            } else {
                bloomFilter = Runner::init(client_sock);
                if (bloomFilter != nullptr) {     
                    Data* data = new Data(*bloomFilter, client_sock, commands);
                    Runner::handle_client(data); 
                }
            }

        vector<pthread_t> threads;
        if (bloomFilter != nullptr) {
            while(true) { 
                int client_sock = accept(sock,  (struct sockaddr*) &client_sin, &addr_len);
                if (client_sock <  0) {
                    perror("error accepting client"); 
                    continue; // Continue to the next iteration of the loop
                } else { 

                    // Create a Data object for each client connection
                    Data* client_data = new Data(*bloomFilter, client_sock, commands);
                    
                    // Create a thread for each client connection
                    pthread_t tid;
                    pthread_attr_t attr;
                    pthread_attr_init(&attr);
                    if (pthread_create(&tid, &attr, Runner::handle_client, client_data) != 0) {
                                perror("error creating thread");
                                delete client_data; // Clean up allocated memory
                                continue; // Continue to the next iteration of the loop
                     }           
                    pthread_attr_destroy(&attr);
                    threads.push_back(tid);
            }
        }
    }

    for (pthread_t tid : threads) {
        pthread_join(tid, NULL);
    }

    close(sock);
    return 0;
}
