package mm.com.sumyat.mercari.util

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import mm.com.sumyat.sixt_testapp.data.executor.PostExecutionThread

/**
 * MainThread (UI Thread) implementation based on a [Scheduler]
 * which will execute actions on the Android UI thread
 */
class UiThread : PostExecutionThread {

    override val scheduler: Scheduler
        get() = AndroidSchedulers.mainThread()

}