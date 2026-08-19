package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.db.AppDatabase
import com.example.data.db.BookmarkEntity
import com.example.data.db.CompletedChapterEntity
import com.example.data.db.QuizRecordEntity
import com.example.data.db.UserNoteEntity
import com.example.data.db.UserProfileEntity
import com.example.data.model.CadetLevel
import com.example.data.model.Chapter
import com.example.data.model.IndustrialDisputeStep
import com.example.data.model.LaborLawTerm
import com.example.data.model.LearningModule
import com.example.data.model.QuizQuestion
import com.example.data.model.SectorCategory
import com.example.data.repository.LearningContentData
import com.example.data.repository.LearningRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class ScreenNav {
    object Home : ScreenNav()
    data class ModuleDetail(val moduleId: String) : ScreenNav()
    data class ChapterRead(val chapterId: String) : ScreenNav()
    data class Quiz(val moduleId: String) : ScreenNav()
    object LaborTools : ScreenNav()
    object Dictionary : ScreenNav()
    object Profile : ScreenNav()
}

data class SeveranceCalculationResult(
    val yearsOfService: Int,
    val monthlyWage: Double,
    val reason: String,
    val pesangonMultiplier: Double,
    val pesangonAmount: Double,
    val upmkMultiplier: Double,
    val upmkAmount: Double,
    val uphAmount: Double,
    val totalSeverance: Double,
    val legalBasis: String
)

data class OvertimeCalculationResult(
    val hourlyRate: Double,
    val dayType: String, // "HARI_KERJA" or "HARI_LIBUR"
    val overtimeHours: Double,
    val totalOvertimePay: Double,
    val breakdown: List<String>
)

class LearningViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: LearningRepository

    init {
        val database = AppDatabase.getDatabase(application)
        repository = LearningRepository(database.appDao())
    }

    // Navigation State
    private val _currentScreen = MutableStateFlow<ScreenNav>(ScreenNav.Home)
    val currentScreen: StateFlow<ScreenNav> = _currentScreen.asStateFlow()

    fun navigateTo(screen: ScreenNav) {
        _currentScreen.value = screen
    }

    fun navigateBack() {
        when (val screen = _currentScreen.value) {
            is ScreenNav.ChapterRead -> {
                val chapter = repository.getChapterById(screen.chapterId)
                if (chapter != null) {
                    _currentScreen.value = ScreenNav.ModuleDetail(chapter.moduleId)
                } else {
                    _currentScreen.value = ScreenNav.Home
                }
            }
            is ScreenNav.Quiz -> {
                _currentScreen.value = ScreenNav.ModuleDetail(screen.moduleId)
            }
            is ScreenNav.ModuleDetail -> {
                _currentScreen.value = ScreenNav.Home
            }
            else -> {
                _currentScreen.value = ScreenNav.Home
            }
        }
    }

    // Selected Sector Filter
    private val _selectedSector = MutableStateFlow(SectorCategory.ALL)
    val selectedSector: StateFlow<SectorCategory> = _selectedSector.asStateFlow()

    fun setSectorFilter(category: SectorCategory) {
        _selectedSector.value = category
    }

    // Dictionary search
    private val _dictionaryQuery = MutableStateFlow("")
    val dictionaryQuery: StateFlow<String> = _dictionaryQuery.asStateFlow()

    private val _selectedDictionaryCategory = MutableStateFlow("Semua")
    val selectedDictionaryCategory: StateFlow<String> = _selectedDictionaryCategory.asStateFlow()

    fun setDictionaryQuery(query: String) {
        _dictionaryQuery.value = query
    }

    fun setDictionaryCategory(category: String) {
        _selectedDictionaryCategory.value = category
    }

    // Repository content getters
    fun getAllModules(): List<LearningModule> = repository.getModules()
    fun getModule(id: String): LearningModule? = repository.getModuleById(id)
    fun getChapters(moduleId: String): List<Chapter> = repository.getChaptersForModule(moduleId)
    fun getChapter(id: String): Chapter? = repository.getChapterById(id)
    fun getNextChapter(chapter: Chapter): Chapter? = repository.getNextChapter(chapter)
    fun getPreviousChapter(chapter: Chapter): Chapter? = repository.getPreviousChapter(chapter)
    fun getQuizzes(moduleId: String): List<QuizQuestion> = repository.getQuizzesForModule(moduleId)
    fun getDisputeSteps(): List<IndustrialDisputeStep> = repository.getDisputeSteps()

    // Filtered Dictionary terms
    val filteredLaborTerms: StateFlow<List<LaborLawTerm>> = combine(
        _dictionaryQuery,
        _selectedDictionaryCategory
    ) { query, cat ->
        val all = repository.getAllLaborTerms()
        all.filter { term ->
            val matchQuery = query.isBlank() ||
                    term.term.contains(query, ignoreCase = true) ||
                    term.shortDefinition.contains(query, ignoreCase = true) ||
                    term.fullExplanation.contains(query, ignoreCase = true) ||
                    term.legalReference.contains(query, ignoreCase = true)

            val matchCat = cat == "Semua" || term.category == cat
            matchQuery && matchCat
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = repository.getAllLaborTerms()
    )

    // DB state
    val completedChapters: StateFlow<List<CompletedChapterEntity>> = repository.completedChapters
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val quizRecords: StateFlow<List<QuizRecordEntity>> = repository.quizRecords
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allNotes: StateFlow<List<UserNoteEntity>> = repository.allNotes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val bookmarks: StateFlow<List<BookmarkEntity>> = repository.bookmarks
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val userProfile: StateFlow<UserProfileEntity> = repository.userProfile
        .combine(MutableStateFlow(Unit)) { profile, _ ->
            profile ?: UserProfileEntity()
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserProfileEntity())

    // Actions
    fun toggleChapterCompleted(moduleId: String, chapterId: String, isCurrentlyCompleted: Boolean) {
        viewModelScope.launch {
            if (isCurrentlyCompleted) {
                repository.unmarkChapterCompleted(moduleId, chapterId)
            } else {
                repository.markChapterCompleted(moduleId, chapterId)
            }
        }
    }

    fun toggleBookmark(chapter: Chapter, isCurrentlyBookmarked: Boolean) {
        val module = repository.getModuleById(chapter.moduleId) ?: return
        viewModelScope.launch {
            repository.toggleBookmark(chapter, module, isCurrentlyBookmarked)
        }
    }

    fun addNote(chapterId: String, title: String, content: String) {
        val chapter = repository.getChapterById(chapterId) ?: return
        viewModelScope.launch {
            repository.saveNote(
                moduleId = chapter.moduleId,
                chapterId = chapter.id,
                chapterTitle = chapter.title,
                title = title.ifBlank { "Catatan: ${chapter.title}" },
                content = content
            )
        }
    }

    fun deleteNote(noteId: Long) {
        viewModelScope.launch {
            repository.deleteNote(noteId)
        }
    }

    fun recordQuizResult(moduleId: String, score: Int, totalQuestions: Int) {
        val passed = (score.toDouble() / totalQuestions.toDouble()) >= 0.75
        viewModelScope.launch {
            repository.recordQuizResult(moduleId, score, totalQuestions, passed)
        }
    }

    fun updateProfile(name: String, puk: String, sector: String, memberNumber: String, location: String) {
        viewModelScope.launch {
            repository.saveUserProfile(
                UserProfileEntity(
                    id = 1,
                    name = name,
                    pukName = puk,
                    companySector = sector,
                    memberNumber = memberNumber,
                    plantLocation = location
                )
            )
        }
    }

    // Calculator: Pesangon & PHK (PP 35/2021)
    fun calculateSeverance(
        yearsOfService: Int,
        monthlyWage: Double,
        reasonIndex: Int // 0: PHK Efisiensi/Rugi, 1: PHK Efisiensi Cegah Rugi, 2: Pensiun Normal, 3: Mengundurkan Diri (Resign), 4: Sakit Berkepanjangan >12 Bulan
    ): SeveranceCalculationResult {
        // Uang Pesangon (UP) based on years of service (PP 35/2021 Pasal 40 Ayat 2)
        val rawUPMonths = when {
            yearsOfService < 1 -> 1.0
            yearsOfService < 2 -> 2.0
            yearsOfService < 3 -> 3.0
            yearsOfService < 4 -> 4.0
            yearsOfService < 5 -> 5.0
            yearsOfService < 6 -> 6.0
            yearsOfService < 7 -> 7.0
            yearsOfService < 8 -> 8.0
            else -> 9.0
        }

        // Uang Penghargaan Masa Kerja (UPMK) (PP 35/2021 Pasal 40 Ayat 3)
        val rawUPMKMonths = when {
            yearsOfService < 3 -> 0.0
            yearsOfService < 6 -> 2.0
            yearsOfService < 9 -> 3.0
            yearsOfService < 12 -> 4.0
            yearsOfService < 15 -> 5.0
            yearsOfService < 18 -> 6.0
            yearsOfService < 21 -> 7.0
            yearsOfService < 24 -> 8.0
            else -> 10.0
        }

        val reasonText: String
        val upMultiplier: Double
        val upmkMultiplier: Double
        val legalBasis: String

        when (reasonIndex) {
            0 -> { // Efisiensi karena rugi / force majeure
                reasonText = "Efisiensi Karena Perusahaan Merugi / Force Majeure"
                upMultiplier = 0.5
                upmkMultiplier = 1.0
                legalBasis = "PP No. 35 Tahun 2021 Pasal 43 Ayat (1)"
            }
            1 -> { // Efisiensi mencegah kerugian
                reasonText = "Efisiensi Untuk Mencegah Terjadinya Kerugian"
                upMultiplier = 1.0
                upmkMultiplier = 1.0
                legalBasis = "PP No. 35 Tahun 2021 Pasal 43 Ayat (2)"
            }
            2 -> { // Usia Pensiun
                reasonText = "Pekerja Mencapai Usia Pensiun Normal"
                upMultiplier = 1.75
                upmkMultiplier = 1.0
                legalBasis = "PP No. 35 Tahun 2021 Pasal 56"
            }
            3 -> { // Resign / Mengundurkan diri
                reasonText = "Mengundurkan Diri Sukarela (Resign)"
                upMultiplier = 0.0
                upmkMultiplier = 0.0
                legalBasis = "PP No. 35 Tahun 2021 Pasal 50 (Hanya Uang Penggantian Hak / Uang Pisah sesuai PKB)"
            }
            4 -> { // Sakit berkepanjangan >12 bln
                reasonText = "Sakit Berkepanjangan / Cacat Akibat Kecelakaan Kerja"
                upMultiplier = 2.0
                upmkMultiplier = 1.0
                legalBasis = "PP No. 35 Tahun 2021 Pasal 47"
            }
            else -> {
                reasonText = "PHK Standar Hubungan Kerja"
                upMultiplier = 1.0
                upmkMultiplier = 1.0
                legalBasis = "PP No. 35 Tahun 2021"
            }
        }

        val pesangonAmount = rawUPMonths * upMultiplier * monthlyWage
        val upmkAmount = rawUPMKMonths * upmkMultiplier * monthlyWage
        // UPH (Uang Penggantian Hak) estimasi cuti tahunan belum gugur dll (misal estimasi 5% dari total)
        val uphAmount = (monthlyWage / 25.0) * 5.0 // contoh 5 hari cuti belum diambil
        val total = pesangonAmount + upmkAmount + uphAmount

        return SeveranceCalculationResult(
            yearsOfService = yearsOfService,
            monthlyWage = monthlyWage,
            reason = reasonText,
            pesangonMultiplier = upMultiplier,
            pesangonAmount = pesangonAmount,
            upmkMultiplier = upmkMultiplier,
            upmkAmount = upmkAmount,
            uphAmount = uphAmount,
            totalSeverance = total,
            legalBasis = legalBasis
        )
    }

    // Calculator: Lembur (Overtime PP 35/2021)
    fun calculateOvertime(
        monthlyWage: Double,
        dayType: String, // "HARI_KERJA" or "HARI_LIBUR_6_HARI" or "HARI_LIBUR_5_HARI"
        hours: Double
    ): OvertimeCalculationResult {
        // Upah per jam = 1/173 x Upah Sebulan (PP 35/2021 Pasal 32)
        val hourlyRate = monthlyWage / 173.0
        val breakdown = mutableListOf<String>()
        var totalPay = 0.0

        if (dayType == "HARI_KERJA") {
            if (hours <= 1.0) {
                val pay = hours * 1.5 * hourlyRate
                totalPay += pay
                breakdown.add("Jam ke-1: $hours jam × 1.5 × Upah/Jam = Rp %,.0f".format(pay))
            } else {
                val pay1 = 1.0 * 1.5 * hourlyRate
                val restHours = hours - 1.0
                val payRest = restHours * 2.0 * hourlyRate
                totalPay = pay1 + payRest
                breakdown.add("Jam ke-1: 1 jam × 1.5 × Upah/Jam = Rp %,.0f".format(pay1))
                breakdown.add("Jam ke-2 s.d ${hours.toInt()}: $restHours jam × 2.0 × Upah/Jam = Rp %,.0f".format(payRest))
            }
        } else { // HARI_LIBUR
            if (hours <= 7.0) {
                val pay = hours * 2.0 * hourlyRate
                totalPay += pay
                breakdown.add("Jam 1 s.d $hours: $hours jam × 2.0 × Upah/Jam = Rp %,.0f".format(pay))
            } else if (hours <= 8.0) {
                val pay7 = 7.0 * 2.0 * hourlyRate
                val pay8 = 1.0 * 3.0 * hourlyRate
                totalPay = pay7 + pay8
                breakdown.add("Jam 1 s.d 7: 7 jam × 2.0 × Upah/Jam = Rp %,.0f".format(pay7))
                breakdown.add("Jam ke-8: 1 jam × 3.0 × Upah/Jam = Rp %,.0f".format(pay8))
            } else {
                val pay7 = 7.0 * 2.0 * hourlyRate
                val pay8 = 1.0 * 3.0 * hourlyRate
                val rest = (hours - 8.0)
                val payRest = rest * 4.0 * hourlyRate
                totalPay = pay7 + pay8 + payRest
                breakdown.add("Jam 1 s.d 7: 7 jam × 2.0 × Upah/Jam = Rp %,.0f".format(pay7))
                breakdown.add("Jam ke-8: 1 jam × 3.0 × Upah/Jam = Rp %,.0f".format(pay8))
                breakdown.add("Jam ke-9 dst: $rest jam × 4.0 × Upah/Jam = Rp %,.0f".format(payRest))
            }
        }

        return OvertimeCalculationResult(
            hourlyRate = hourlyRate,
            dayType = dayType,
            overtimeHours = hours,
            totalOvertimePay = totalPay,
            breakdown = breakdown
        )
    }

    // Compute Overall Cadet Progress
    fun getProgressStats(completed: List<CompletedChapterEntity>): Pair<Int, CadetLevel> {
        val totalAllChapters = LearningContentData.chapters.size
        val completedCount = completed.size
        val percentage = if (totalAllChapters > 0) (completedCount * 100) / totalAllChapters else 0

        val level = when {
            percentage >= 80 -> CadetLevel.KADER_UTAMA
            percentage >= 50 -> CadetLevel.KADER_MADYA
            percentage >= 20 -> CadetLevel.KADER_PRATAMA
            else -> CadetLevel.CALON_KADER
        }
        return Pair(percentage, level)
    }
}
