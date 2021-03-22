package com.thomas.daumBlogCafeTest.localRoom

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "recent_search_word", indices = [Index(value = ["word"], unique = true)])
data class RecentlySearchWord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val word: String,
    val createdAt: Long = System.currentTimeMillis()
)
