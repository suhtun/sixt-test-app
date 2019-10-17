package mm.com.sumyat.mercari.remote

import io.reactivex.Single
import mm.com.sumyat.mercari.data.SampleRemote
import mm.com.sumyat.mercari.remote.model.Car

class SampleRemoteImpl (private val service: NetworkService) : SampleRemote{
    override fun getItem(path:String): Single<List<Car>> {
        return service.getItem(path)
    }

    override fun getMaster(): Single<List<Master>> {
        return service.getCars()
    }
}