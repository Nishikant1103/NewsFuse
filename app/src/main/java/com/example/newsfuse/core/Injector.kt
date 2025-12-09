package com.example.newsfuse.core

import android.content.Context
import androidx.work.WorkerParameters
import com.example.newsfuse.datasource.local.db.NewsDatabase
import com.example.newsfuse.datasource.local.db.dao.NewsDao
import com.example.newsfuse.datasource.remote.NewsDataSource
import com.example.newsfuse.datasource.repository.NewsRepository
import com.example.newsfuse.view.newslist.NewsListViewModel
import com.example.newsfuse.workers.NewsProviderWorker

class Injector {
    companion object {

        private fun getNewsDatabase(context: Context): NewsDatabase {
            return NewsDatabase.getDatabase(context)
        }

        private fun getNewsDao(context: Context): NewsDao {
            return getNewsDatabase(context).newsDao()
        }

        private fun getNewsRepository(context: Context): NewsRepository {
            return NewsRepository(
                newsDao = getNewsDao(context)
            )
        }

        fun getNewsListViewModel(context: Context): NewsListViewModel {
            return NewsListViewModel(getNewsRepository(context))
        }
    }
}