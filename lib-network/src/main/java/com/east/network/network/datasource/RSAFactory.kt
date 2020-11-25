package com.east.network.network.datasource

import com.east.network.network.datasource.Data
import com.east.network.network.datasource.DataFactory
import com.east.network.network.datasource.RSA

class RSAFactory : DataFactory {
    override fun create(): Data {
        return RSA();
    }
}