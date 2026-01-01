package com.example.newsfuse.viewmodel

import com.example.newsfuse.datasource.data.News
import com.example.newsfuse.datasource.repository.NewsRepository
import com.example.newsfuse.view.newslist.NewsListViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class NewsListViewModelTest {
    @Mock
    private lateinit var newsRepository: NewsRepository
    private lateinit var newsListViewModel: NewsListViewModel

    private var dummyNewsSet = mutableSetOf<News>()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        dummyNewsSet.addAll(
            setOf(
                News(
                    datePosted = "01.01.2026, 00:00",
                    newsTitle = "Title1",
                    newsDescription = "Description1",
                    newsLink = "https://www.newlink1/",
                    newsImageLink = "https://www.newlink1/image1"
                ),
                News(
                    datePosted = "01.01.2026, 00:01",
                    newsTitle = "Title2",
                    newsDescription = "Description2",
                    newsLink = "https://www.newlink2/",
                    newsImageLink = "https://www.newlink2/image2"
                )
            )
        )
        val dummyFlow = MutableStateFlow(dummyNewsSet)
        Mockito.`when`(newsRepository.getLatestNews).thenReturn(dummyFlow)
        newsListViewModel = NewsListViewModel(newsRepository)
    }

    @Test
    fun getLatestNewsSuccessTest() {
        runBlocking {
            val newsSet = newsListViewModel.getLatestNewsSet.first()
            assertEquals(dummyNewsSet, newsSet)
        }
    }
}
