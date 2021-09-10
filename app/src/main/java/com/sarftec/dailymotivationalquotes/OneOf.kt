package com.sarftec.dailymotivationalquotes


sealed class OneOf<out S, out E> {
    class Success<S>(val value: S) : OneOf<S, Nothing>()
    class Failure<E>(val exception: E) : OneOf<Nothing, E>()

    suspend fun onSuccess(callback: suspend (S) -> Unit) : OneOf<S, E> {
       if(this is Success) callback(value)
        return this
    }

    suspend fun onFailure(callback: suspend (E) -> Unit) : OneOf<S, E> {
        if(this is Failure) callback(exception)
        return this
    }

    companion object {
        fun <S> success(value: S) : Success<S> {
            return Success(value)
        }

        fun <E> failure(value: E) : Failure<E> {
            return Failure(value)
        }
    }
}