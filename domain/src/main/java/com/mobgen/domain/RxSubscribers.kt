package com.mobgen.domain

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.logging.Level
import java.util.logging.Logger

fun <T> Observable<T>.subscribe(
    executor: Scheduler,
    onComplete: () -> Unit,
    onSuccess: (result: T) -> Unit,
    onError: (exception: Throwable) -> Unit
): Disposable {
    return this.observeOn(executor)
        .subscribeOn(Schedulers.io())
        .subscribe({
            Logger.getGlobal().log(Level.INFO, "OnSuccess... $it")
            onSuccess(it)
        }, {
            Logger.getGlobal().log(Level.INFO, "OnError... ${it.localizedMessage}")
            onError(it)
        }, {
            Logger.getGlobal().log(Level.INFO, "OnComplete...")
            onComplete()
        })
}

fun Completable.subscribe(
    executor: Scheduler,
    onComplete: () -> Unit,
    onError: (exception: Throwable) -> Unit
): Disposable {
    return this.observeOn(executor)
        .subscribeOn(Schedulers.io())
        .subscribe({
            Logger.getGlobal().log(Level.INFO, "OnComplete...")
            onComplete()
        }, {
            Logger.getGlobal().log(Level.INFO, "OnError... ${it.localizedMessage}")
            onError(it)
        })
}

fun <T> Single<T>.subscribe(
    executor: Scheduler,
    onSuccess: (result: T) -> Unit,
    onError: (exception: Throwable) -> Unit
): Disposable {
    return this.observeOn(executor)
        .subscribeOn(Schedulers.io())
        .subscribe({
            Logger.getGlobal().log(Level.INFO, "OnNext... $it")
            onSuccess(it)
        }, {
            Logger.getGlobal().log(Level.INFO, "OnError... ${it.localizedMessage}")
            onError(it)
        })
}