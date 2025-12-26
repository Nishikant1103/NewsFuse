package com.example.newsfuse.core

import android.content.Context
import com.example.newsfuse.core.utility.TimeFormatter
import com.example.newsfuse.datasource.local.db.NewsDatabase
import com.example.newsfuse.datasource.local.db.dao.FeedsDao
import com.example.newsfuse.datasource.local.db.dao.NewsDao
import com.example.newsfuse.datasource.remote.NewsDataSource
import com.example.newsfuse.datasource.repository.AddFeedRepository
import com.example.newsfuse.datasource.repository.NewsRepository
import com.example.newsfuse.view.addfeed.AddFeedViewModel
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
                newsDao = getNewsDao(context),
                timeFormatter = TimeFormatter()
            )
        }

        fun getNewsListViewModel(context: Context): NewsListViewModel {
            return NewsListViewModel(getNewsRepository(context))
        }

        fun getNewsDetailViewModel(context: Context): NewsDetailViewModel {
            return NewsDetailViewModel(getNewsRepository(context))
        }

        private fun getFeedsDao(context: Context): FeedsDao {
            return getNewsDatabase(context).feedsDao()
        }

        private fun getNewsDataSource(): NewsDataSource {
            return NewsDataSource()
        }

        private fun getAddFeedRepository(context: Context): AddFeedRepository {
            return AddFeedRepository(
                getNewsDataSource(),
                getFeedsDao(context)
            )
        }

        fun getAddFeedViewModel(context: Context): AddFeedViewModel {
            return AddFeedViewModel(getAddFeedRepository(context))
        }
    }
}