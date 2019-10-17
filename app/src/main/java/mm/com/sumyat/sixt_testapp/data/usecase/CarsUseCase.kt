package mm.com.sumyat.sixt_testapp.data.usecase

import io.reactivex.Single
import mm.com.sumyat.sixt_testapp.data.SampleRemote
import mm.com.sumyat.sixt_testapp.data.executor.PostExecutionThread
import mm.com.sumyat.sixt_testapp.data.executor.ThreadExecutor
import mm.com.sumyat.sixt_testapp.data.interactor.SingleUseCase
import mm.com.sumyat.sixt_testapp.network.model.Car

open class CarsUseCase (
    val repo: SampleRemote,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<List<Car>, Void?>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(params: Void?): Single<List<Car>> {
        return repo.getCars()
    }

}