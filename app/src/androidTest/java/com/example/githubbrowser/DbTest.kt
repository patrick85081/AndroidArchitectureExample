package com.example.githubbrowser

import android.support.test.runner.AndroidJUnit4
import org.junit.runner.RunWith
import com.example.githubbrowser.db.GithubDb
import android.support.test.InstrumentationRegistry
import android.arch.persistence.room.Room
import android.support.test.espresso.matcher.ViewMatchers.assertThat
import com.example.githubbrowser.services.models.Owner
import org.junit.Before
import org.junit.After
import com.example.githubbrowser.services.models.Repo
import org.hamcrest.CoreMatchers.`is`
import org.junit.Test


/**
 * Created by Patrick on 2018/8/4.
 */
@RunWith(AndroidJUnit4::class)
class DbTest
{
    lateinit var db: GithubDb;
    lateinit var repo: Repo;

    @Before
    //    @Throws(Exception::class)
    fun setUp()
    {
        db = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                GithubDb::class.java).build()

        val owner = Owner("foo", "", "")
        repo = Repo(1, "foo", "foo/bar", "desc", 50, owner, "")
    }

    @After
    //    @Throws(Exception::class)
    fun closeDb()
    {
        db.close()
    }

    @Test
    //    @Throws(InterruptedException::class)
    fun insertAndLoad()
    {
        // Insert repo
        db.repoDao().insert(repo)
        // Query repo
        val loaded = LiveDataTestUtil.getValue(db.repoDao().load("foo", "foo"));
        // Assert query result
        assertThat(loaded.owner.login, `is`("foo"))
        assertThat(loaded.name, `is`("foo"))
    }
}