#include "Headers/commandInit.h"

CommandInit::CommandInit() {};

bool CommandInit::validateCode(char* userInput, int read_bytes) const{
    bool validInput = true;
    vector<string> input;
    for (int i = 0; i < read_bytes; ++i) {
            string word;
            while (userInput[i] != ' ' && i < read_bytes) {
                word += userInput[i];
                ++i;
            }
            input.push_back(word);
    }

    try {
        int num = stoi(input.at(0));
    } catch (const invalid_argument& e) {
        validInput = false;
    } catch (const out_of_range& e) {
        validInput = false;
    }

    return validInput && input.size() == 2 && (input[0] == "1" || input[0] == "2");
}
