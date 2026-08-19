package com.example.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "completed_chapters", primaryKeys = ["moduleId", "chapterId"])
data class CompletedChapterEntity(
    val moduleId: String,
    val chapterId: String,
    val completedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "quiz_records")
data class QuizRecordEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val moduleId: String,
    val score: Int,
    val totalQuestions: Int,
    val percentage: Int,
    val passed: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "user_notes")
data class UserNoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val moduleId: String,
    val chapterId: String,
    val chapterTitle: String,
    val title: String,
    val content: String,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey val chapterId: String,
    val moduleId: String,
    val chapterTitle: String,
    val moduleTitle: String,
    val categoryName: String,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val id: Int = 1,
    val name: String = "Budi Santoso",
    val pukName: String = "PUK SP KEP SPSI PT Petrokimia Nusantara",
    val companySector: String = "Sektor Kimia",
    val memberNumber: String = "KEP-2026-0819",
    val joinYear: String = "2021",
    val plantLocation: String = "Kawasan Industri Cilegon, Banten"
)
