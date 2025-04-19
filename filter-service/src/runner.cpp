#include "Headers/runner.h"

BloomFilter* Runner::init(int client_sock) {
    BloomFilterInit B;
    InputValidator* bf_ptr = &B;
    char buffer[4096];
    int expected_data_len = sizeof(buffer);
    int read_bytes;
    bool valid = false; 

    do { 
            read_bytes = recv(client_sock, buffer, expected_data_len, 0);       
            if (read_bytes == 0) {
                close(client_sock);
                return nullptr; 
            } else if (read_bytes < 0) {
                perror("Error receiving data from client");
                continue;
            } else {
                valid = bf_ptr->validateCode(buffer, read_bytes);
                char failure[] = "Failed to create the Bloom Filter";
                char success[] = "Bloom Filter created successfully";
                if (valid) {
                    int sent_bytes = send(client_sock, success, strlen(success), 0);    
                    if (sent_bytes < 0) { perror("error sending to client"); }
                } else {
                    int sent_bytes = send(client_sock, failure, strlen(failure), 0);    
                    if (sent_bytes < 0) { perror("error sending to client"); }
                }
            }
    } while(!valid);

    vector<string> validInput = bf_ptr->extract(buffer, read_bytes); 
    BloomFilter* bloomFilter = initialization(validInput);
    return bloomFilter;
}

void* Runner::handle_client(void* param) {    

    Data* data = (Data*)param; 
    CommandInit C;
    InputValidator* ci_ptr = &C;
    char buffer[4096];
    int expected_data_len = sizeof(buffer);
    int read_bytes;
    bool valid = false;

    // Get the current time point
    // auto start_time = std::chrono::high_resolution_clock::now();
    // Print the thread ID
    // std::cout << "Thread ID: " << std::this_thread::get_id() << std::endl;
    // Print the start time in milliseconds
    // auto duration = start_time.time_since_epoch();
    // long milliseconds = std::chrono::duration_cast<std::chrono::milliseconds>(duration).count();
    // std::cout << "Start time (milliseconds): " << milliseconds << std::endl;
    // std::this_thread::sleep_for(std::chrono::seconds(1));

    while(true) { 
    do { 
        read_bytes = recv(data->client_sock, buffer, expected_data_len, 0);       
        if (read_bytes == 0) {
            close(data->client_sock);
            return nullptr; 
        } else if (read_bytes < 0) {
            perror("Error receiving data from client");
            continue;
        } else { 
            valid = ci_ptr->validateCode(buffer, read_bytes);
            if (!valid) {
                char info[] = "Input was not in a correct format";
                int sent_bytes = send(data->client_sock, info, strlen(info), 0);    
                if (sent_bytes < 0) { perror("error sending to client"); }
            }
        }
    } while (!valid);

    vector<string> validInput = ci_ptr->extract(buffer, read_bytes); 
    string output = data->commands[validInput[0]]->execute(validInput[1], data->bloomFilter);
    int output_len = strlen(output.c_str());

    int sent_bytes = send(data->client_sock, output.c_str(), output_len, 0);    
        if (sent_bytes < 0) {
            perror("error sending to client");
        }
    }
    return nullptr;
}

BloomFilter* Runner::initialization(vector<string> validInput){
    int arraySize = stoi(validInput[0]);
    list<HashFunction> functions;
    for (auto iterator = validInput.begin() + 1; iterator != validInput.end(); ++iterator) {
        int coefficient = stoi(*iterator);
        functions.push_back(HashFunction(arraySize, coefficient));
    }
    BloomFilter* bf = new BloomFilter(arraySize, functions);
    return bf;
}