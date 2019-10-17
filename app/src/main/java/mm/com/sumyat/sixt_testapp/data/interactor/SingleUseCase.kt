package mm.com.sumyat.sixt_testapp.data.interactor

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import mm.com.sumyat.sixt_testapp.data.executor.PostExecutionThread
import mm.com.sumyat.sixt_testapp.data.executor.ThreadExecutor

abstract class SingleUseCase<T, in Params> constructor(
    private val threadExecutor: ThreadExecutor,
    private val postExecutionThread: PostExecutionThread
) {

    protected abstract fun buildUseCaseObservable(params: Params? = null): Single<T>

    open fun execute(params: Params? = null): Single<T> {
        return this.buildUseCaseObservable(params)
            .subscribeOn(Schedulers.from(threadExecutor))
            .observeOn(postExecutionThread.scheduler) as Single<T>
    }

}