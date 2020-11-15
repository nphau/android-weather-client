package au.com.nab.android.data.db

import androidx.arch.core.executor.testing.CountingTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class LocalDbTest {

    private lateinit var db: LocalDB

    @Rule
    @JvmField
    val countingTaskExecutorRule = CountingTaskExecutorRule()

    @Before
    @Throws(Exception::class)
    fun initDb() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            LocalDB::class.java
        ) // allowing main thread queries, just for testing
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun closeDb() {
        countingTaskExecutorRule.drainTasks(10, TimeUnit.SECONDS)
        db.close()
    }

    @Test
    fun insertAndFindTest() {

    }

    @Test
    fun findNoValues() {
        db.weatherEntityDao().findByCityName("").test().assertNoValues()
    }
}