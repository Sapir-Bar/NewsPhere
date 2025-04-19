#include "Headers/bloomFilterInit.h"

BloomFilterInit::BloomFilterInit() {}

bool BloomFilterInit::validateCode(char* userInput, int read_bytes) const {
    bool validInput = true;
    vector<string> input;

    for (int i = 0; i < read_bytes; ++i) {
        string word;
        while (userInput[i] != ' ' && i < read_bytes) {
            word += userInput[i];
            ++i;
        }
        input.push_back(word);
        // Convert each word to an integer using stoi
        try {
            int num = stoi(word);
        } catch (const invalid_argument& e) {
            validInput = false;
            break;
        } catch (const out_of_range& e) {
            validInput = false;
            break;
        }
    }

    return validInput && input.size() >= 2;
}

