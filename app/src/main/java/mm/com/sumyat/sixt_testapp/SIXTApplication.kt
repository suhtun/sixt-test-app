package mm.com.sumyat.sixt_testapp

import android.app.Application
import mm.com.sumyat.sixt_testapp.di.applicationModule
import mm.com.sumyat.sixt_testapp.di.carModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SIXTApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SIXTApplication)
            modules(listOf(applicationModule, carModule))
        }
    }
}