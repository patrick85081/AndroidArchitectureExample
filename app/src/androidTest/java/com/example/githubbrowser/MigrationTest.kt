package com.example.githubbrowser

import android.arch.persistence.db.framework.FrameworkSQLiteOpenHelperFactory
import android.arch.persistence.room.testing.MigrationTestHelper
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.example.githubbrowser.db.GithubDb
import org.junit.Rule
import org.junit.runner.RunWith
import android.arch.persistence.room.Room
import android.content.ContentValues
import android.arch.persistence.db.SupportSQLiteDatabase
import android.database.sqlite.SQLiteDatabase
import android.support.test.espresso.matcher.ViewMatchers.assertThat
import com.example.githubbrowser.LiveDataTestUtil.getValue
import org.hamcrest.CoreMatchers.`is`
import org.junit.Test
import java.io.IOException


/**
 * Created by Patrick on 2018/8/4.
 */
@RunWith(AndroidJUnit4::class)
class MigrationTest
{
    val TEST_DB_NAME = "migration-test";

    @Rule
    val testHelper: MigrationTestHelper =
            MigrationTestHelper(
                    InstrumentationRegistry.getInstrumentation(),
                    GithubDb::class.java.canonicalName,
                    FrameworkSQLiteOpenHelperFactory());

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun migrate1To2()
    {
        var db = testHelper.createDatabase(TEST_DB_NAME, 1)

        // db has schema version 1. insert some data using SQL queries.
        // You cannot use DAO classes because they expect the latest schema.
        insertRepo("foo_name", "foo_login", db)

        // Prepare for the next version.
        db.close()

        // Re-open the database with version 2 and provide
        // MIGRATION_1_2 as the migration process.
        db = testHelper.runMigrationsAndValidate(TEST_DB_NAME, 2, true, GithubDb.MIGRATION_1_2)

        // MigrationTestHelper automatically verifies the schema changes,
        // but you need to validate that the data was migrated properly.

        // open the db with Room.
        val githubDb = getMigratedRoomDatabase()
        val loaded = getValue(githubDb.repoDao().load("foo_login", "foo_name"))
        assertThat(loaded.owner.login, `is`("foo_login"))
        assertThat(loaded.name, `is`("foo_name"))
    }

    private fun insertRepo(name: String, owner_login: String, db: SupportSQLiteDatabase)
    {
        val values = ContentValues()
        values.put("id", 1)
        values.put("name", name)
        values.put("stars", 50)
        values.put("owner_login", owner_login)

        db.insert("Repo", SQLiteDatabase.CONFLICT_REPLACE, values)
    }

    private fun getMigratedRoomDatabase(): GithubDb
    {
        val database = Room.databaseBuilder(InstrumentationRegistry.getTargetContext(),
                GithubDb::class.java, TEST_DB_NAME)
                .addMigrations(GithubDb.MIGRATION_1_2)
                .build()
        // close the database and release any stream resources when the test finishes
        testHelper.closeWhenFinished(database)
        return database
    }
}