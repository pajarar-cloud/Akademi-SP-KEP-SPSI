package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    // Completed chapters
    @Query("SELECT * FROM completed_chapters")
    fun getAllCompletedChapters(): Flow<List<CompletedChapterEntity>>

    @Query("SELECT * FROM completed_chapters WHERE moduleId = :moduleId")
    fun getCompletedChaptersForModule(moduleId: String): Flow<List<CompletedChapterEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun markChapterCompleted(entity: CompletedChapterEntity)

    @Query("DELETE FROM completed_chapters WHERE moduleId = :moduleId AND chapterId = :chapterId")
    suspend fun unmarkChapterCompleted(moduleId: String, chapterId: String)

    // Quiz records
    @Query("SELECT * FROM quiz_records ORDER BY timestamp DESC")
    fun getAllQuizRecords(): Flow<List<QuizRecordEntity>>

    @Query("SELECT * FROM quiz_records WHERE moduleId = :moduleId ORDER BY timestamp DESC")
    fun getQuizRecordsForModule(moduleId: String): Flow<List<QuizRecordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizRecord(record: QuizRecordEntity)

    // User Notes
    @Query("SELECT * FROM user_notes ORDER BY updatedAt DESC")
    fun getAllNotes(): Flow<List<UserNoteEntity>>

    @Query("SELECT * FROM user_notes WHERE chapterId = :chapterId")
    fun getNotesForChapter(chapterId: String): Flow<List<UserNoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: UserNoteEntity): Long

    @Query("DELETE FROM user_notes WHERE id = :id")
    suspend fun deleteNoteById(id: Long)

    // Bookmarks
    @Query("SELECT * FROM bookmarks ORDER BY createdAt DESC")
    fun getAllBookmarks(): Flow<List<BookmarkEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarks WHERE chapterId = :chapterId)")
    fun isChapterBookmarked(chapterId: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBookmark(bookmark: BookmarkEntity)

    @Query("DELETE FROM bookmarks WHERE chapterId = :chapterId")
    suspend fun removeBookmark(chapterId: String)

    // User Profile
    @Query("SELECT * FROM user_profile WHERE id = 1 LIMIT 1")
    fun getUserProfile(): Flow<UserProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUserProfile(profile: UserProfileEntity)
}
