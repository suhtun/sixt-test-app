package mm.com.sumyat.sixt_testapp.ui.show_cars

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.Disposable
import mm.com.sumyat.sixt_testapp.data.usecase.CarsUseCase
import mm.com.sumyat.sixt_testapp.ui.util.BrowseState

class MapsViewModel(private val carsUseCase: CarsUseCase) : ViewModel(){
    val masterLiveData = MutableLiveData<BrowseState>()
    private var disposable: Disposable? = null

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }

    fun fetchMaster() {
        masterLiveData.postValue(BrowseState.Loading)
        disposable = carsUseCase.execute()
            .subscribe({
                masterLiveData.postValue(BrowseState.Success(it))
            }, {
                masterLiveData.postValue(BrowseState.Error (it.message))
            })
    }
}