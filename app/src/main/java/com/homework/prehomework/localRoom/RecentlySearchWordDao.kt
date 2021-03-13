package com.homework.prehomework.localRoom

import androidx.room.*

@Dao
interface RecentlySearchWordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<RecentlySearchWord>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(word: RecentlySearchWord)

    @Delete
    fun delete(word: RecentlySearchWord)

    @Query("DELETE FROM recent_search_word WHERE id = :id")
    fun delete(id: Long)

    @Query("DELETE FROM recent_search_word")
    fun clear()

    @Query("SELECT count(*) FROM recent_search_word")
    fun getCount() : Int

    @Query("SELECT * FROM recent_search_word ORDER BY createdAt DESC LIMIT 10")
    fun getRecentSearchedWords(): List<RecentlySearchWord>

}
