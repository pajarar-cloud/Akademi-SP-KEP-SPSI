package com.example.data.model

enum class SectorCategory(val label: String, val badgeText: String) {
    ALL("Semua Sektor", "Semua"),
    UNION_BASICS("Dasar Serikat", "Organisasi"),
    LABOR_LAW("Hukum & PPHI", "Hukum"),
    COLLECTIVE_BARGAINING("Perundingan PKB", "PKB"),
    SAFETY_K3("K3 KEP Industri", "K3"),
    CHEMICAL("Sektor Kimia", "Kimia"),
    ENERGY("Sektor Energi", "Energi"),
    MINING("Sektor Tambang", "Tambang"),
    LEADERSHIP("Kepemimpinan", "Kaderisasi")
}

enum class CadetLevel(val title: String, val levelNumber: Int, val description: String) {
    CALON_KADER("Calon Anggota", 1, "Tahap pengenalan dasar-dasar serikat pekerja."),
    KADER_PRATAMA("Kader Pratama", 2, "Memahami hak normatif, K3, dan fungsi serikat di unit kerja."),
    KADER_MADYA("Kader Madya", 3, "Menguasai teknik advokasi, negosiasi PKB, dan penanganan sengketa."),
    KADER_UTAMA("Kader Utama & Negosiator", 4, "Pemimpin strategis pengorganisasian dan hubungan industrial.")
}

data class LearningModule(
    val id: String,
    val title: String,
    val subtitle: String,
    val category: SectorCategory,
    val readDurationMinutes: Int,
    val iconName: String,
    val description: String,
    val keyPoints: List<String>,
    val targetCadetLevel: CadetLevel
)

data class Chapter(
    val id: String,
    val moduleId: String,
    val chapterIndex: Int,
    val title: String,
    val subtitle: String,
    val summary: String,
    val sections: List<ChapterSection>,
    val pasalReferences: List<String>,
    val keyTakeaways: List<String>,
    val caseStudy: CaseStudy? = null
)

data class ChapterSection(
    val heading: String,
    val content: String,
    val quoteOrHighlight: String? = null,
    val bulletPoints: List<String> = emptyList()
)

data class CaseStudy(
    val title: String,
    val background: String,
    val problem: String,
    val unionSolution: String,
    val lessonLearned: String
)

data class QuizQuestion(
    val id: String,
    val moduleId: String,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
    val legalReference: String
)

data class LaborLawTerm(
    val id: String,
    val term: String,
    val category: String,
    val shortDefinition: String,
    val fullExplanation: String,
    val legalReference: String,
    val practicalExample: String
)

data class IndustrialDisputeStep(
    val stepNumber: Int,
    val title: String,
    val actor: String,
    val timelineLimit: String,
    val legalBasis: String,
    val description: String,
    val checklistDocs: List<String>,
    val keyTactics: List<String>
)
