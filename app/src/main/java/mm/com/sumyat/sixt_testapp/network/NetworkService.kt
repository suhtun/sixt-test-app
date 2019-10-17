package mm.com.sumyat.mercari.remote

import io.reactivex.Single
import mm.com.sumyat.mercari.remote.model.Car
import retrofit2.http.GET
import retrofit2.http.Url

interface NetworkService {
    @GET("cars")
    fun getCars(): Single<List<Car>>

}