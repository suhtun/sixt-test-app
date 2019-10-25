package mm.com.sumyat.sixt_testapp.network

import io.reactivex.Single
import mm.com.sumyat.sixt_testapp.data.SampleRemote
import mm.com.sumyat.sixt_testapp.network.model.Car

open class SampleRemoteImpl (private val service: NetworkService) : SampleRemote {
    override fun getCars(): Single<List<Car>> {
        return service.callCars()
    }

}