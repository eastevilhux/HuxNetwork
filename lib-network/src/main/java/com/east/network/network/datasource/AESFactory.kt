package com.east.network.network.datasource

class AESFactory : DataFactory {

    override fun create(): Data {
        return AES();
    }

}