package com.sarftec.dailymotivationalquotes.domain.usecase

import kotlinx.coroutines.*

abstract class UseCase<T, S> {

    private var job: Deferred<S>? = null

    protected abstract suspend fun run(param: T): S

    suspend fun execute(param: T, callback: suspend (S) -> Unit) {
        callback(run(param))
    }
}