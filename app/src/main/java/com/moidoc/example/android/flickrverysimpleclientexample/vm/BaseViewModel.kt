package com.moidoc.example.android.flickrverysimpleclientexample.vm

import androidx.annotation.CallSuper
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import java.util.concurrent.ConcurrentHashMap

abstract class BaseViewModel<out FragmentAction> : BaseObserveMessagesViewModel() {

    /** Observable stream for navigation between screens */
    protected val _navigationAction: SingleLiveEvent<out FragmentAction> = SingleLiveEvent()
    val navigationAction: LiveData<out FragmentAction> = _navigationAction
}

abstract class BaseObserveMessagesViewModel : BaseCoroutinesViewModel() {

    /** Observable stream for throwable */
    protected val _error: SingleLiveEvent<Throwable> = SingleLiveEvent()
    val error: LiveData<Throwable> = _error

    /** Observable stream for messages. May be extend by other then [CharSequence] class to make more complex messages */
    protected val _message: SingleLiveEvent<CharSequence> = SingleLiveEvent()
    val message: LiveData<CharSequence> = _message
}

abstract class BaseCoroutinesViewModel: ViewModel() {
    protected val coroutineScopes: MutableMap<String, CoroutineScope> = ConcurrentHashMap()

    /** if [block] throws an exception, then [handler] will receive it, but a fatal exception will occur anyway */
    protected fun addDisposableTask(
        tag: String,
        coroutineDispatcher: CoroutineDispatcher,
        block: suspend (CoroutineScope) -> Unit
    ): Job {
        val coroutineContext = Job() + coroutineDispatcher
        val coroutineScope = CoroutineScope(coroutineContext)

        val job = coroutineScope.launch {
            coroutineScopes[tag] = coroutineScope
            block(this)
        }

        return job
    }

    protected fun getTask(tag: String): CoroutineScope? {
        return coroutineScopes[tag]
    }

    protected fun hasTask(tag: String): Boolean {
        return getTask(tag) != null
    }

    protected fun isTaskActive(tag: String): Boolean {
        return getTaskChildren(tag)?.firstOrNull()?.isActive == true
    }

    protected fun isTaskCompleted(tag: String): Boolean {
        return getTaskChildren(tag)?.firstOrNull()?.isCompleted == true
    }

    protected fun isTaskCanceled(tag: String): Boolean {
        return getTaskChildren(tag)?.firstOrNull()?.isCancelled == true
    }

    protected fun getTaskChildren(tag: String): Sequence<Job>? {
        return coroutineScopes[tag]?.coroutineContext?.get(Job)?.children
    }

    protected fun cancelTask(tag: String) {
        getTask(tag)?.coroutineContext?.cancelChildren()
    }

    protected fun clearTask(tag: String) {
        coroutineScopes.remove(tag)?.coroutineContext?.cancelChildren()
    }

    protected fun clearAll() {
        coroutineScopes.keys.forEach {
            clearTask(it)
        }
    }

    @CallSuper
    override fun onCleared() {
        super.onCleared()

        clearAll()
    }
}