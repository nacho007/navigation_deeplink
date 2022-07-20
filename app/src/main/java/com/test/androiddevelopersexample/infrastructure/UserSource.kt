package com.test.androiddevelopersexample.infrastructure

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.test.androiddevelopersexample.domain.User

import org.apache.http.HttpException
import java.io.IOException

/**
 * Created by ignaciodeandreisdenis on 20/7/22.
 */
class UserSource : PagingSource<Int, User>() {

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val nextPage = params.key ?: 1
            val userList = RetrofitClient.apiService.getUserList(page = nextPage)
            LoadResult.Page(
                data = userList.data,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (userList.data.isEmpty()) null else userList.page + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}
