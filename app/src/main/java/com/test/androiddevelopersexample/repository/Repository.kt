package com.test.androiddevelopersexample.repository

import javax.inject.Inject

/**
 * Created by ignaciodeandreisdenis on 7/20/20.
 */
class Repository @Inject constructor() {

    val hola = 0

    override fun toString(): String {
        return hola.toString()
    }

}