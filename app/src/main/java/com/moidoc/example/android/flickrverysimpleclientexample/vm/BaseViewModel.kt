package com.moidoc.example.android.flickrverysimpleclientexample.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<out FragmentAction> : BaseObserveMessagesViewModel() {

    /** Observable stream for navigation between screens */
    protected val _navigationAction: SingleLiveEvent<out FragmentAction> = SingleLiveEvent()
    val navigationAction: LiveData<out FragmentAction> = _navigationAction
}

abstract class BaseObserveMessagesViewModel : ViewModel() {

    /** Observable stream for throwable */
    protected val _error: SingleLiveEvent<Throwable> = SingleLiveEvent()
    val error: LiveData<Throwable> = _error

    /** Observable stream for messages. May be extend by other then [CharSequence] class to make more complex messages */
    protected val _message: SingleLiveEvent<CharSequence> = SingleLiveEvent()
    val message: LiveData<CharSequence> = _message
}