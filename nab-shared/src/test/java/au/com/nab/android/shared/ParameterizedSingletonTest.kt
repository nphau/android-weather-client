package au.com.nab.android.shared

import au.com.nab.android.shared.common.ParameterizedSingleton
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ParameterizedSingletonTest {

    private lateinit var createdObject: ParameterizedSingleton<String, Int>
    private val testInt = 10

    @Before
    fun initSingleton() {
        createdObject = ParameterizedSingleton { testInt.toString() }
    }

    @Test
    fun checkParameterizedSingletonSingletonValidity() {
        val instance = createdObject.getInstance(testInt)
        Assert.assertEquals(instance, testInt.toString())
    }
}
