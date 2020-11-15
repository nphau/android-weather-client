package au.com.nab.android.domain.usecases

import au.com.nab.android.domain.repositories.SearchWeathersRepository
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.verify

class SearchWeathersUseCaseTest {

    private lateinit var searchWeathersUseCase: SearchWeathersUseCase
    private lateinit var searchWeathersRepository: SearchWeathersRepository

    @Before
    fun setUp() {
        searchWeathersUseCase = SearchWeathersUseCase(searchWeathersRepository)
    }

    @Test
    fun testSearchWeathersUseCase() {
        verify(searchWeathersUseCase).execute(hashMapOf("q" to "saigon"))
    }
}