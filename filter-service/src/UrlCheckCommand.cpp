#include "Headers/UrlCheckCommand.h"

    UrlCheckCommand::UrlCheckCommand() {}

    string UrlCheckCommand::execute(string Url, BloomFilter& bf) const {
        return bf.checkAddress(Url);
    }
