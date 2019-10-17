package mm.com.sumyat.sixt_testapp.data

import io.reactivex.Single
import mm.com.sumyat.sixt_testapp.network.model.Car

interface SampleRemote {

    fun getCars(): Single<List<Car>>

}