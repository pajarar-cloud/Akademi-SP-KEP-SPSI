package com.example.data.repository

import com.example.data.db.AppDao
import com.example.data.db.BookmarkEntity
import com.example.data.db.CompletedChapterEntity
import com.example.data.db.QuizRecordEntity
import com.example.data.db.UserNoteEntity
import com.example.data.db.UserProfileEntity
import com.example.data.model.Chapter
import com.example.data.model.IndustrialDisputeStep
import com.example.data.model.LaborLawTerm
import com.example.data.model.LearningModule
import com.example.data.model.QuizQuestion
import com.example.data.model.SectorCategory
import kotlinx.coroutines.flow.Flow

class LearningRepository(private val appDao: AppDao) {

    fun getModules(): List<LearningModule> = LearningContentData.modules

    fun getModuleById(moduleId: String): LearningModule? =
        LearningContentData.modules.find { it.id == moduleId }

    fun getChaptersForModule(moduleId: String): List<Chapter> =
        LearningContentData.chapters.filter { it.moduleId == moduleId }
            .sortedBy { it.chapterIndex }

    fun getChapterById(chapterId: String): Chapter? =
        LearningContentData.chapters.find { it.id == chapterId }

    fun getNextChapter(currentChapter: Chapter): Chapter? {
        val allChapters = getChaptersForModule(currentChapter.moduleId)
        val currentIndex = allChapters.indexOfFirst { it.id == currentChapter.id }
        return if (currentIndex in 0 until allChapters.size - 1) {
            allChapters[currentIndex + 1]
        } else {
            null
        }
    }

    fun getPreviousChapter(currentChapter: Chapter): Chapter? {
        val allChapters = getChaptersForModule(currentChapter.moduleId)
        val currentIndex = allChapters.indexOfFirst { it.id == currentChapter.id }
        return if (currentIndex > 0) {
            allChapters[currentIndex - 1]
        } else {
            null
        }
    }

    fun getQuizzesForModule(moduleId: String): List<QuizQuestion> =
        LearningContentData.quizzes[moduleId] ?: emptyList()

    fun getAllLaborTerms(): List<LaborLawTerm> = LearningContentData.laborLawTerms

    fun getDisputeSteps(): List<IndustrialDisputeStep> = LearningContentData.disputeSteps

    // Flow from Room DB
    val completedChapters: Flow<List<CompletedChapterEntity>> = appDao.getAllCompletedChapters()

    fun getCompletedChaptersForModule(moduleId: String): Flow<List<CompletedChapterEntity>> =
        appDao.getCompletedChaptersForModule(moduleId)

    suspend fun markChapterCompleted(moduleId: String, chapterId: String) {
        appDao.markChapterCompleted(CompletedChapterEntity(moduleId = moduleId, chapterId = chapterId))
    }

    suspend fun unmarkChapterCompleted(moduleId: String, chapterId: String) {
        appDao.unmarkChapterCompleted(moduleId, chapterId)
    }

    val quizRecords: Flow<List<QuizRecordEntity>> = appDao.getAllQuizRecords()

    suspend fun recordQuizResult(moduleId: String, score: Int, totalQuestions: Int, passed: Boolean) {
        val percentage = if (totalQuestions > 0) (score * 100) / totalQuestions else 0
        appDao.insertQuizRecord(
            QuizRecordEntity(
                moduleId = moduleId,
                score = score,
                totalQuestions = totalQuestions,
                percentage = percentage,
                passed = passed
            )
        )
    }

    val allNotes: Flow<List<UserNoteEntity>> = appDao.getAllNotes()

    fun getNotesForChapter(chapterId: String): Flow<List<UserNoteEntity>> =
        appDao.getNotesForChapter(chapterId)

    suspend fun saveNote(moduleId: String, chapterId: String, chapterTitle: String, title: String, content: String): Long {
        return appDao.insertNote(
            UserNoteEntity(
                moduleId = moduleId,
                chapterId = chapterId,
                chapterTitle = chapterTitle,
                title = title,
                content = content
            )
        )
    }

    suspend fun deleteNote(noteId: Long) {
        appDao.deleteNoteById(noteId)
    }

    val bookmarks: Flow<List<BookmarkEntity>> = appDao.getAllBookmarks()

    fun isBookmarked(chapterId: String): Flow<Boolean> = appDao.isChapterBookmarked(chapterId)

    suspend fun toggleBookmark(chapter: Chapter, module: LearningModule, isCurrentlyBookmarked: Boolean) {
        if (isCurrentlyBookmarked) {
            appDao.removeBookmark(chapter.id)
        } else {
            appDao.addBookmark(
                BookmarkEntity(
                    chapterId = chapter.id,
                    moduleId = module.id,
                    chapterTitle = chapter.title,
                    moduleTitle = module.title,
                    categoryName = module.category.badgeText
                )
            )
        }
    }

    val userProfile: Flow<UserProfileEntity?> = appDao.getUserProfile()

    suspend fun saveUserProfile(profile: UserProfileEntity) {
        appDao.saveUserProfile(profile)
    }
}
