package com.east.network.network.datasource

import com.east.network.utils.RSAUtil

class RSA : Data() {
    override fun encryptionData(data: String, key: String, tag: String?): String? {
        return RSAUtil.encryptByPublicKey(data,key);
    }

    override fun decryptData(data: String, key: String, tag: String?): String? {
        return RSAUtil.decryptByPublicKey(data,key);
    }


}