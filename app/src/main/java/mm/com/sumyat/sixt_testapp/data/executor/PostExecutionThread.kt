package mm.com.sumyat.sixt_testapp.data.executor

import io.reactivex.Scheduler

interface PostExecutionThread {
    val scheduler: Scheduler
}