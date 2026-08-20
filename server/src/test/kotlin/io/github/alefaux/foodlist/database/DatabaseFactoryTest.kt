package io.github.alefaux.foodlist.database

import kotlin.test.Test
import kotlin.test.assertEquals

class DatabaseFactoryTest {
    @Test
    fun `converts Render-style postgres URI to JDBC form`() {
        val (jdbcUrl, user, password) = DatabaseFactory.toJdbc(
            "postgresql://foodlist_dev_user:HC40MzA1Trlmwo9j8yaKQikRGLrgBmEc@dpg-da3bsjqjnfac73c68vh0-a/foodlist_dev",
            "",
            ""
        )
        assertEquals("jdbc:postgresql://dpg-da3bsjqjnfac73c68vh0-a:5432/foodlist_dev", jdbcUrl)
        assertEquals("foodlist_dev_user", user)
        assertEquals("HC40MzA1Trlmwo9j8yaKQikRGLrgBmEc", password)
    }

    @Test
    fun `leaves jdbc urls untouched`() {
        val (jdbcUrl, _, _) = DatabaseFactory.toJdbc("jdbc:h2:./data/foodlist;AUTO_SERVER=TRUE", "", "")
        assertEquals("jdbc:h2:./data/foodlist;AUTO_SERVER=TRUE", jdbcUrl)
    }
}
