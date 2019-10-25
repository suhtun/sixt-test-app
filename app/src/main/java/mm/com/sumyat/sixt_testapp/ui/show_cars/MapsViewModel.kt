package mm.com.sumyat.sixt_testapp.ui.show_cars

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.Disposable
import mm.com.sumyat.sixt_testapp.data.usecase.CarsUseCase
import mm.com.sumyat.sixt_testapp.network.model.Car
import mm.com.sumyat.sixt_testapp.ui.util.BrowseState
import mm.com.sumyat.sixt_testapp.ui.util.EspressoIdlingResource

class MapsViewModel(private val carsUseCase: CarsUseCase) : ViewModel() {

    val masterLiveData = MutableLiveData<BrowseState>()
    private var disposable: Disposable? = null

    var cars: List<Car> = arrayListOf()

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }

    fun fetchMaster() {
        masterLiveData.postValue(BrowseState.Loading)
        EspressoIdlingResource.increment()
        disposable = carsUseCase.execute()
            .subscribe({
                masterLiveData.postValue(BrowseState.Success(it))
                EspressoIdlingResource.decrement()
                cars = it
            }, {
                EspressoIdlingResource.decrement()
                masterLiveData.postValue(BrowseState.Error(it.message))
            })
    }

    fun getCars(): LiveData<BrowseState> {
        return masterLiveData
    }

    fun findPagerPosition(title: String?): Int {

        var found = false
        var position = 0

        while (!found && position <= cars.size) {
            if (cars[position].name.equals(title, true)) {
                found = true
            } else {
                position++
            }
        }
        return position
    }

}