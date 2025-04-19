#include "Headers/InputValidator.h"

vector<string> InputValidator::extract(char* userInput, int read_bytes) const {
    vector<string> input;
    for (int i = 0; i < read_bytes; ++i) {
        string word;
        while (userInput[i] != ' ' && i < read_bytes) {
            word += userInput[i];
            ++i;
        }
        input.push_back(word);
    }
    return input;
}





