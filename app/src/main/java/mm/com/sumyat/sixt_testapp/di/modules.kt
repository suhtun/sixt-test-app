package mm.com.sumyat.sixt_testapp.di

import mm.com.sumyat.mercari.util.JobExecutor
import mm.com.sumyat.mercari.util.UiThread
import mm.com.sumyat.sixt_testapp.BuildConfig
import mm.com.sumyat.sixt_testapp.data.SampleRemote
import mm.com.sumyat.sixt_testapp.data.executor.PostExecutionThread
import mm.com.sumyat.sixt_testapp.data.executor.ThreadExecutor
import mm.com.sumyat.sixt_testapp.data.usecase.CarsUseCase
import mm.com.sumyat.sixt_testapp.network.NetworkServiceFactory
import mm.com.sumyat.sixt_testapp.network.SampleRemoteImpl
import mm.com.sumyat.sixt_testapp.ui.MapsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val applicationModule = module(override = true) {
    single { JobExecutor() as ThreadExecutor }
    single { UiThread() as PostExecutionThread }
    factory { NetworkServiceFactory.makeNetworkService(BuildConfig.DEBUG) }

    factory<SampleRemote>() { SampleRemoteImpl(get()) }
}

val carModule = module(override = true) {
//    factory { ItemAdapter() }
    factory { CarsUseCase(get(), get(), get()) }
//    factory { CarsUseCase(get(), get(), get()) }
    viewModel { MapsViewModel(get()) }
//    single { TabItemViewModel(get()) }
}