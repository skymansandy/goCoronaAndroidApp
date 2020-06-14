package dev.skymansandy.gocorona.presentation.main.india.adapter

import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import java.util.concurrent.Executor

class CovidStatDataSource(val provider: CovidStatListProvider) :
    PageKeyedDataSource<Int, CovidStat>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, CovidStat>
    ) {
        val list = provider.getStringList(0, params.requestedLoadSize)
        callback.onResult(list, 1, 2)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, CovidStat>) {
        val list = provider.getStringList(params.key, params.requestedLoadSize)
        callback.onResult(list, params.key + 1)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, CovidStat>) {
        val list = provider.getStringList(params.key, params.requestedLoadSize)
        val nextIndex = if (params.key > 1) params.key - 1 else null
        callback.onResult(list, nextIndex)
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}

class UiThreadExecutor : Executor {
    private val handler = Handler(Looper.getMainLooper())
    override fun execute(command: Runnable) {
        handler.post(command)
    }
}

class CovidStatListProvider(val list: List<CovidStat>) {

    fun getStringList(page: Int, pageSize: Int): List<CovidStat> {
        val initialIndex = page * pageSize
        if (initialIndex > list.size) {
            return emptyList()
        }
        val finalIndex = when ((initialIndex + pageSize) > list.size) {
            true -> list.size
            else -> initialIndex + pageSize
        }
        return list.subList(initialIndex, finalIndex)
    }
}

fun getPagedList(list: List<CovidStat>, pagingConfig: PagedList.Config): PagedList<CovidStat> {
    return PagedList.Builder(
        CovidStatDataSource(CovidStatListProvider(list)),
        pagingConfig
    ).setNotifyExecutor(UiThreadExecutor())
        .setFetchExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
        .setInitialKey(0)
        .build()
}