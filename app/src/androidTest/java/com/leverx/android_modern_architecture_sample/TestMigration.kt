package com.leverx.android_modern_architecture_sample

import android.util.Log
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.leverx.android_modern_architecture_sample.data.storage.ArticleRoomDatabase
import com.leverx.android_modern_architecture_sample.data.storage.MIGRATION_7_8
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestMigration {
    private lateinit var database: SupportSQLiteDatabase

    @JvmField
    @Rule
    val migrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        ArticleRoomDatabase::class.java.canonicalName,
        FrameworkSQLiteOpenHelperFactory()
    )

    @Test
    fun migrate7to8() {
        database = migrationTestHelper.createDatabase("migration-test", 7).apply {
            execSQL(
                """
                INSERT INTO saved_article_table VALUES ('title') 
                """.trimIndent()
            )

            close()
        }
        Log.e("TAG", database.toString())

        database = migrationTestHelper.runMigrationsAndValidate("migration-test", 8, true, MIGRATION_7_8)

        val resultCursor = database.query("SELECT * FROM saved_article_table")

        // Let's make sure we can find the  age column, and that it's equal to our default.
        // We can also validate the name is the one we inserted.
        Assert.assertTrue(resultCursor.moveToFirst())
        val ageColumnIndex = resultCursor.getColumnIndex("id")
        val nameColumnIndex = resultCursor.getColumnIndex("date")

        val ageFromDatabase = resultCursor.getString(ageColumnIndex)
        val nameFromDatabase = resultCursor.getString(nameColumnIndex)

        Assert.assertEquals("title", ageFromDatabase)
        Assert.assertEquals("Date", nameFromDatabase)
    }

}
