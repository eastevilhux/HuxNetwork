package com.east.network.network.datasource

import java.lang.NullPointerException

class DataHelper {
    private var decryptType : DataType = DataType.TYPE_RSA;
    private var encryptType : DataType = DataType.TYPE_RSA;

    private var key : String? = null;
    private var iv : String? = null;

    private var rsaFactory : RSAFactory = RSAFactory();
    private var aesFactory : AESFactory = AESFactory();

    private constructor(){

    }

    companion object{
        val instance : DataHelper by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { DataHelper() }
    }

    fun setKey(key:String){
        this.key = key;
    }

    fun setRSA(){
        decryptType = DataType.TYPE_RSA;
        encryptType = DataType.TYPE_RSA;
    }

    fun setAES(){
        decryptType = DataType.TYPE_AES;
        encryptType = DataType.TYPE_AES;
    }

    fun encryptData(data:String) : String?{
        if(key == null){
            throw NullPointerException("the key not be null");
        }
        return when(decryptType){
            DataType.TYPE_AES ->{
                return aesFactory.create().encryptionData(data,key!!,iv);
            }
            DataType.TYPE_RSA ->{
                return rsaFactory.create().encryptionData(data,key!!,iv);
            }
        }
    }

    enum class DataType{
        TYPE_AES,
        TYPE_RSA;
    }
}