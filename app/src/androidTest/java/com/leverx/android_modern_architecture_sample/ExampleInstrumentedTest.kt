package com.leverx.android_modern_architecture_sample

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.leverx.android_modern_architecture_sample.data.storage.ArticleRoomDatabase
import com.leverx.android_modern_architecture_sample.data.storage.dao.ArticleDao
import com.leverx.android_modern_architecture_sample.data.storage.dao.FavoriteArticleDao
import com.leverx.android_modern_architecture_sample.data.storage.dao.SourceDao
import com.leverx.android_modern_architecture_sample.data.storage.entity.ArticleDB
import com.leverx.android_modern_architecture_sample.data.storage.entity.ArticleToSource
import com.leverx.android_modern_architecture_sample.data.storage.entity.FavoriteArticle
import com.leverx.android_modern_architecture_sample.data.storage.entity.SourceDB
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.leverx.android_modern_architecture_sample", appContext.packageName)
    }

    private lateinit var daoFavoriteArticle: FavoriteArticleDao
    private lateinit var daoArticle: ArticleDao
    private lateinit var daoSource: SourceDao
    private lateinit var db: ArticleRoomDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, ArticleRoomDatabase::class.java
        ).build()
        daoFavoriteArticle = db.favoriteArticleDao()
        daoArticle = db.articleDao()
        daoSource = db.sourceDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeFavoriteArticleToTheDbAndCheckingReading() {

        daoFavoriteArticle.insert(listOf(FavoriteArticle("SimpleID")))
        val favoriteArticle = daoFavoriteArticle.isFavoriteById("SimpleID")
        assertEquals("SimpleID", favoriteArticle!!.id)
    }

    @Test
    @Throws(Exception::class)
    fun writeArticleSourceToTheDbAndCheckingReadingArticle() {
        val articleExample = ArticleDB(
            1,
            "author",
            "content",
            "description",
            "publisherAt",
            "source",
            "title",
            "url",
            "img"
        )
        daoArticle.insert(
            listOf(
                articleExample
            )
        )
        daoSource.insert(
            listOf(
                SourceDB(
                    "source",
                    "source"
                )
            )
        )

        val article: ArticleToSource? = daoArticle.getById(1)

        assertEquals(articleExample, article!!.article)
    }
    @Test
    @Throws(Exception::class)
    fun writeArticleSourceToTheDbAndCheckingReadingSource() {
        val sourceExample =
            SourceDB(
                "source",
                "source"
            )
        daoArticle.insert(
            listOf(
                ArticleDB(
                    1,
                    "author",
                    "content",
                    "description",
                    "publisherAt",
                    "source",
                    "title",
                    "url",
                    "img"
                )
            )
        )
        daoSource.insert(
            listOf(
                sourceExample
            )
        )

        val article: ArticleToSource? = daoArticle.getById(1)

        assertEquals(sourceExample, article!!.source)
    }
}
