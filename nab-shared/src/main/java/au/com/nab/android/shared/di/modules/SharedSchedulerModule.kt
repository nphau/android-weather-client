package au.com.nab.android.shared.di.modules

import au.com.nab.android.shared.di.qualifier.SchedulerComputationThread
import au.com.nab.android.shared.di.qualifier.SchedulerIoThread
import au.com.nab.android.shared.di.qualifier.SchedulerMainThread
import au.com.nab.android.shared.di.qualifier.SchedulerNewThread
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Module
object SharedSchedulerModule {

    @Provides
    @Singleton
    @SchedulerComputationThread
    fun provideComputationScheduler(): Scheduler {
        return Schedulers.computation()
    }

    @Provides
    @SchedulerIoThread
    @Singleton
    fun provideIoScheduler(): Scheduler {
        return Schedulers.io()
    }

    @Provides
    @Singleton
    @SchedulerMainThread
    fun provideMainThreadScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    @Provides
    @Singleton
    @SchedulerComputationThread
    fun provideTrampolineThreadScheduler(): Scheduler {
        return Schedulers.trampoline()
    }

    @Provides
    @Singleton
    @SchedulerNewThread
    fun provideNewThreadScheduler(): Scheduler {
        return Schedulers.newThread()
    }
}