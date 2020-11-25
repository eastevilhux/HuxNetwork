package com.east.network.network

import java.nio.charset.Charset

class HttpConfig private constructor(builder: Builder) {
    //请求服务器地址URL
    private var SERVICE_URL = "http://localhost:8080/appservice/"

    //请求Token地址URL
    private var TOKEN_URL = "http://localhost:8080/token"

    private var CODE_SUCCESS = 66;
    private var CODE_ERROR = -1;
    private var CODE_LOGIN = -2;
    private var CODE_NETWORK = 404;
    private var CODE_EMPTY = 44;
    private var CODE_SERVICE_ERROR = -4;
    private var charset : String = "UTF-8";
    private var TIME_OUT : Long = 20L;

    //Retrofit缓存时间为1小时
    private var MAX_AGE : Int = 60;

    private var HTTP_CHARSET = Charset.forName(charset);


    init {
        SERVICE_URL = builder.SERVICE_URL
        TOKEN_URL = builder.TOKEN_URL
        CODE_SUCCESS = builder.CODE_SUCCESS
        CODE_ERROR = builder.CODE_ERROR
        CODE_EMPTY = builder.CODE_EMPTY
        CODE_LOGIN = builder.CODE_LOGIN
        CODE_NETWORK = builder.CODE_NETWORK
        CODE_SERVICE_ERROR = builder.CODE_SERVICE_ERROR
        TIME_OUT = builder.TIME_OUT
        MAX_AGE = builder.MAX_AGE
        charset = builder.charset
        HTTP_CHARSET = Charset.forName(builder.charset)
    }

    companion object{

    }

    class Builder(serviceUrl: String) {
        //请求服务器地址URL
        internal var SERVICE_URL = serviceUrl

        //请求Token地址URL
        internal var TOKEN_URL = "http://localhost:8080/token"

        internal var CODE_SUCCESS = 66;
        internal var CODE_ERROR = -1;
        internal var CODE_LOGIN = -2;
        internal var CODE_NETWORK = 404;
        internal var CODE_EMPTY = 44;
        internal var CODE_SERVICE_ERROR = -4;
        internal var charset : String = "UTF-8";
        internal var TIME_OUT : Long = 20L;

        //Retrofit缓存时间为1小时
        internal var MAX_AGE : Int = 60;

        fun tokenUrl(tokenUrl:String) : Builder {
            TOKEN_URL = tokenUrl;
            return this;
        }

        fun successCode(code:Int): Builder {
            this.CODE_SUCCESS = code;
            return this;
        }

        fun loginErrorCode(code:Int): Builder {
            CODE_LOGIN = code;
            return this;
        }

        fun networkErrorCode(code:Int): Builder {
            CODE_NETWORK = code;
            return this;
        }

        fun serviceErrorCode(code:Int): Builder {
            CODE_ERROR = code;
            return this;
        }

        fun emptyErrorCode(code:Int): Builder {
            CODE_EMPTY = code;
            return this;
        }

        fun systemErrorCode(code:Int): Builder {
            CODE_SERVICE_ERROR = code;
            return this;
        }

        fun charset(charset:String): Builder {
            this.charset = charset;
            return this;
        }

        fun timeOut(time:Long): Builder {
            TIME_OUT = time;
            return this;
        }

        fun maxAge(maxAge:Int): Builder {
            MAX_AGE = maxAge;
            return this;
        }


        fun builder() : HttpConfig {
            return HttpConfig(this);
        }
    }

    fun baseUrl(): String {
        return SERVICE_URL;
    }

    fun successCode(): Int {
        return CODE_SUCCESS;
    }

    fun loginErrorCode(): Int {
        return CODE_LOGIN;
    }

    fun emptyCode(): Int {
        return CODE_EMPTY;
    }

    fun networkErrorCode(): Int {
        return CODE_NETWORK;
    }

    fun serviceErrorCode(): Int {
        return CODE_SERVICE_ERROR;
    }

    fun httpCharset(): Charset{
        return HTTP_CHARSET;
    }

    fun charset(): String {
        return charset;
    }

    fun timeOut(): Long {
        return TIME_OUT;
    }

    fun maxAge(): Int {
        return MAX_AGE;
    }

}