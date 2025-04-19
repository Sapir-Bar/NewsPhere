#include "Headers/UrlAddCommand.h"

    UrlAddCommand::UrlAddCommand() {}
    
    string UrlAddCommand::execute(string Url, BloomFilter& bf) const{
        return bf.addAddress(Url);
    }
