package mm.com.sumyat.sixt_testapp

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import mm.com.sumyat.sixt_testapp.data.SampleRemote
import mm.com.sumyat.sixt_testapp.data.executor.PostExecutionThread
import mm.com.sumyat.sixt_testapp.data.executor.ThreadExecutor
import mm.com.sumyat.sixt_testapp.data.usecase.CarsUseCase
import mm.com.sumyat.sixt_testapp.network.model.Car
import mm.com.sumyat.sixt_testapp.test.util.CarFactory
import org.junit.Test

import org.mockito.Mockito.verify

class CarsUseCaseTest {

    private val mockThreadExecutor = mock<ThreadExecutor>()
    private val mockPostExecutionThread = mock<PostExecutionThread>()
    private val mockSampleRemote = mock<SampleRemote>()

    private val carsUseCase = CarsUseCase(mockSampleRemote, mockThreadExecutor,
            mockPostExecutionThread)

    @Test
    fun buildUseCaseObservableCallsRepository() {
        carsUseCase.buildUseCaseObservable(null)
        verify(mockSampleRemote).getCars()
    }

    @Test
    fun buildUseCaseObservableCompletes() {
        stubSampleRemoteGetCars(Single.just(CarFactory.makeCarList(2)))
        val testObserver = carsUseCase.buildUseCaseObservable(null).test()
        testObserver.assertComplete()
    }

    @Test
    fun buildUseCaseObservableReturnsData() {
        val bufferoos = CarFactory.makeCarList(2)
        stubSampleRemoteGetCars(Single.just(bufferoos))
        val testObserver = carsUseCase.buildUseCaseObservable(null).test()
        testObserver.assertValue(bufferoos)
    }

    private fun stubSampleRemoteGetCars(single: Single<List<Car>>) {
        whenever(mockSampleRemote.getCars())
                .thenReturn(single)
    }

}