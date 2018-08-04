package com.example.githubbrowser

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.githubbrowser.services.GithubService
import com.example.githubbrowser.utils.LiveDataCallAdapterFactory
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Rule
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.githubbrowser.services.models.RepoSearchResponse
import com.example.githubbrowser.services.models.ApiResponse
import okhttp3.mockwebserver.MockResponse
import okio.BufferedSource
import okio.Okio
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.internal.Classes.getClass
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException
import java.io.InputStream
import java.nio.charset.StandardCharsets


/**
 * Created by Patrick on 2018/8/4.
 */
//@RunWith(JUnit4.class)
class GithubServiceTest
{
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule();
    val mockWebService = MockWebServer();
    val githubService = Retrofit.Builder()
            .baseUrl(mockWebService.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(GithubService::class.java);

    @After
    fun stopService()
    {
        mockWebService.shutdown();
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun search()
    {
        enqueueResponse("search.json")
        val response = LiveDataTestUtil.getValue(
                githubService.searchRepos("foo"))

        assertThat(response, notNullValue())
        assertThat(response.body!!.total, `is`(76))
        assertThat(response.body!!.items.size, `is`(30))
    }

    @Throws(IOException::class)
    private fun enqueueResponse(fileName: String)
    {
        val inputStream: InputStream = this.javaClass.classLoader
                .getResourceAsStream("api-response/$fileName");
        val source: BufferedSource = Okio.buffer(Okio.source(inputStream));
        val mockResponse: MockResponse = MockResponse();
        mockWebService.enqueue(mockResponse
                .setBody(source.readString(StandardCharsets.UTF_8)));
    }
}