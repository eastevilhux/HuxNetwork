package com.east.network.network.datasource

import com.east.network.network.datasource.Data

interface DataFactory {
    fun create() : Data
}