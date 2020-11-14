package au.com.nab.android.shared.vm

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class SharedViewModel : ViewModel(), HasDisposableManager {

    private var compositeDisposable = CompositeDisposable()

    override fun getCompositeDisposable(): CompositeDisposable {
        if (compositeDisposable.isDisposed)
            compositeDisposable = CompositeDisposable()
        return compositeDisposable
    }

    override fun addToDisposable(disposable: Disposable) {
        this.compositeDisposable.add(disposable)
    }

    override fun disposeAll() {
        getCompositeDisposable().clear()
    }

    override fun onCleared() {
        disposeAll()
        super.onCleared()
    }
}
