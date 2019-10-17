package mm.com.sumyat.sixt_testapp.network

import io.reactivex.Single
import mm.com.sumyat.sixt_testapp.network.model.Car
import retrofit2.http.GET

interface NetworkService {
    @GET("cars")
    fun callCars(): Single<List<Car>>

}