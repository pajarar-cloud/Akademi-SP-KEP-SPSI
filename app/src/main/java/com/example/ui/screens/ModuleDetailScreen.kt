package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.Chapter
import com.example.data.model.LearningModule
import com.example.ui.components.CadetLevelBadge
import com.example.ui.components.KepTopAppBar
import com.example.ui.components.SectorCategoryBadge
import com.example.ui.viewmodel.LearningViewModel
import com.example.ui.viewmodel.ScreenNav

@Composable
fun ModuleDetailScreen(
    moduleId: String,
    viewModel: LearningViewModel,
    onBackClick: () -> Unit,
    onChapterClick: (String) -> Unit,
    onQuizClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val module = viewModel.getModule(moduleId)
    if (module == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Modul tidak ditemukan.")
        }
        return
    }

    val chapters = viewModel.getChapters(moduleId)
    val completedChapters by viewModel.completedChapters.collectAsStateWithLifecycle()
    val quizRecords by viewModel.quizRecords.collectAsStateWithLifecycle()

    val completedCount = chapters.count { ch -> completedChapters.any { it.chapterId == ch.id } }
    val progressPercent = if (chapters.isNotEmpty()) (completedCount * 100) / chapters.size else 0

    val moduleQuizRecords = quizRecords.filter { it.moduleId == moduleId }
    val bestQuiz = moduleQuizRecords.maxByOrNull { it.percentage }

    Scaffold(
        topBar = {
            KepTopAppBar(
                title = "Detail Modul",
                subtitle = module.category.label,
                showBackButton = true,
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .testTag("module_detail_list"),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            // Module Hero Banner
            item {
                ModuleHeroCard(
                    module = module,
                    completedCount = completedCount,
                    totalCount = chapters.size,
                    progressPercent = progressPercent
                )
            }

            // Learning Objectives
            item {
                LearningObjectivesCard(keyPoints = module.keyPoints)
            }

            // Chapters List Header
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Daftar Bab Pembelajaran",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    )
                    Text(
                        text = "$completedCount dari ${chapters.size} Selesai",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }

            // Chapter Items
            items(chapters) { chapter ->
                val isCompleted = completedChapters.any { it.chapterId == chapter.id }
                ChapterListItem(
                    chapter = chapter,
                    isCompleted = isCompleted,
                    onClick = { onChapterClick(chapter.id) }
                )
            }

            // Quiz Assessment Section
            item {
                Spacer(modifier = Modifier.height(16.dp))
                ModuleQuizBanner(
                    moduleId = moduleId,
                    bestScore = bestQuiz?.percentage,
                    hasPassed = bestQuiz?.passed == true,
                    onStartQuiz = { onQuizClick(moduleId) }
                )
            }
        }
    }
}

@Composable
fun ModuleHeroCard(
    module: LearningModule,
    completedCount: Int,
    totalCount: Int,
    progressPercent: Int
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF0F3675),
                        Color(0xFF1E3A8A)
                    )
                )
            )
            .padding(20.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SectorCategoryBadge(category = module.category)
                CadetLevelBadge(cadetLevel = module.targetCadetLevel)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = module.title,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 20.sp
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = module.subtitle,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFFDBEAFE),
                    fontSize = 13.sp
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = module.description,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color(0xFFE2E8F0),
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Progress bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Timer,
                        contentDescription = null,
                        tint = Color(0xFFFDE68A),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Estimasi: ${module.readDurationMinutes} Menit",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Color(0xFFFDE68A),
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
                Text(
                    text = "$progressPercent% Selesai",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Spacer(modifier = Modifier.height(6.dp))
            LinearProgressIndicator(
                progress = { progressPercent / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = Color(0xFFF59E0B),
                trackColor = Color(0x44FFFFFF)
            )
        }
    }
}

@Composable
fun LearningObjectivesCard(keyPoints: List<String>) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Verified,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Tujuan Pembelajaran Pokok",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            keyPoints.forEach { point ->
                Row(
                    modifier = Modifier.padding(vertical = 3.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "✓",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF16A34A)
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = point,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 12.sp,
                            lineHeight = 16.sp
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun ChapterListItem(
    chapter: Chapter,
    isCompleted: Boolean,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .testTag("chapter_item_${chapter.id}"),
        colors = CardDefaults.elevatedCardColors(
            containerColor = if (isCompleted) Color(0xFFF0FDF4) else MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = if (isCompleted) Color(0xFFDCFCE7) else MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    if (isCompleted) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = "Selesai",
                            tint = Color(0xFF16A34A),
                            modifier = Modifier.size(22.dp)
                        )
                    } else {
                        Text(
                            text = "${chapter.chapterIndex}",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Bab ${chapter.chapterIndex}: ${chapter.title}",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = chapter.subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 11.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (isCompleted) "Sudah Dipelajari" else "Belum Selesai",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = if (isCompleted) Color(0xFF16A34A) else MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 10.sp
                    )
                )
            }

            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.size(32.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Baca",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ModuleQuizBanner(
    moduleId: String,
    bestScore: Int?,
    hasPassed: Boolean,
    onStartQuiz: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(20.dp))
            .testTag("module_quiz_banner"),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFEDE9FE)
        )
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFF7C3AED),
                        modifier = Modifier.size(36.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Filled.Quiz,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Uji Pemahaman Modul",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF4C1D95)
                            )
                        )
                        Text(
                            text = "Standar Kelulusan: Nilai >= 75%",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color(0xFF6D28D9),
                                fontSize = 11.sp
                            )
                        )
                    }
                }

                if (bestScore != null) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (hasPassed) Color(0xFFDCFCE7) else Color(0xFFFEE2E2)
                    ) {
                        Text(
                            text = if (hasPassed) "Lulus: $bestScore%" else "Skor: $bestScore%",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = if (hasPassed) Color(0xFF15803D) else Color(0xFFB91C1C)
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Ikuti kuis pilihan ganda berbasis kasus hukum nyata untuk menguji penguasaan materi dan membuka sertifikat kompetensi kader.",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color(0xFF5B21B6),
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onStartQuiz,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("start_quiz_button"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF7C3AED)
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (bestScore != null) "Ulangi Kuis Uji Kompetensi" else "Mulai Kuis Uji Kompetensi",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
