package com.test.androiddevelopersexample.domain.models

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class Error(val code: Int? = null, val error: ErrorResponse? = null) : ResultWrapper<Nothing>()
    object NetworkError : ResultWrapper<Nothing>()
    object Void : ResultWrapper<Nothing>()
}