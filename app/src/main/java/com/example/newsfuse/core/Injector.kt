package com.example.newsfuse.core

import android.content.Context
import com.example.newsfuse.datasource.local.db.NewsDatabase
import com.example.newsfuse.datasource.local.db.dao.NewsDao
import com.example.newsfuse.datasource.repository.NewsRepository
import com.example.newsfuse.view.newsdetail.NewsDetailViewModel
import com.example.newsfuse.view.newslist.NewsListViewModel

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

        fun getNewsDetailViewModel(context: Context): NewsDetailViewModel {
            return NewsDetailViewModel(getNewsRepository(context))
        }
    }
}