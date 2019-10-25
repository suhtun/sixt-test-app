package mm.com.sumyat.sixt_testapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import mm.com.sumyat.sixt_testapp.data.usecase.CarsUseCase
import mm.com.sumyat.sixt_testapp.network.model.Car
import mm.com.sumyat.sixt_testapp.test.util.CarFactory
import mm.com.sumyat.sixt_testapp.test.util.DataFactory
import mm.com.sumyat.sixt_testapp.ui.show_cars.MapsViewModel
import mm.com.sumyat.sixt_testapp.ui.util.BrowseState
import org.junit.Rule
import org.junit.Test

class MapsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    val mockCarsUseCase = mock<CarsUseCase>()

    private val viewmodel = MapsViewModel(mockCarsUseCase)

    @Test
    fun getCarsReturnsSuccess() {
        val list = CarFactory.makeCarList(2)
        stubGetCars(Single.just(list))
        viewmodel.getCars()

        assert(viewmodel.getCars().value is BrowseState.Success)
    }

    @Test
    fun getCarsReturnsDataOnSuccess() {
        val list = CarFactory.makeCarList(2)
        stubGetCars(Single.just(list))
        viewmodel.getCars()

        assert(viewmodel.getCars().value?.data == list)
    }

    @Test
    fun getCarsReturnsNoMessageOnSuccess() {
        val list = CarFactory.makeCarList(2)
        stubGetCars(Single.just(list))

        viewmodel.getCars()

        assert(viewmodel.getCars().value?.errorMessage == null)
    }

    @Test
    fun getCarsReturnsError() {
        viewmodel.getCars()
        stubGetCars(Single.error(RuntimeException()))

        assert(viewmodel.getCars().value is BrowseState.Error)
    }

    @Test
    fun getCarsFailsAndContainsNoData() {
        viewmodel.getCars()
        stubGetCars(Single.error(RuntimeException()))

        assert(viewmodel.getCars().value?.data == null)
    }

    @Test
    fun getCarsFailsAndContainsMessage() {
        val errorMessage = DataFactory.randomUuid()
        viewmodel.getCars()
        stubGetCars(Single.error(RuntimeException(errorMessage)))

        assert(viewmodel.getCars().value?.errorMessage == errorMessage)
    }

    @Test
    fun getCarsReturnsLoading() {
        viewmodel.getCars()

        assert(viewmodel.getCars().value is BrowseState.Loading)
    }

    @Test
    fun getCarsContainsNoDataWhenLoading() {
        viewmodel.getCars()

        assert(viewmodel.getCars().value?.data == null)
    }

    @Test
    fun getCarsContainsNoMessageWhenLoading() {
        viewmodel.getCars()

        assert(viewmodel.getCars().value?.data == null)
    }

    private fun stubGetCars(single: Single<List<Car>>) {
        whenever(mockCarsUseCase.execute(any()))
                .thenReturn(single)
    }
}