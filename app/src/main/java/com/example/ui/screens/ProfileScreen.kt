package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.FactCheck
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.NoteAlt
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.data.db.CompletedChapterEntity
import com.example.data.db.QuizRecordEntity
import com.example.data.model.CadetLevel
import com.example.data.model.Chapter
import com.example.data.model.LearningModule
import com.example.ui.components.CadetLevelBadge
import com.example.ui.components.CertificateDialog
import com.example.ui.components.KepTopAppBar
import com.example.ui.components.SectorCategoryBadge
import com.example.ui.viewmodel.LearningViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: LearningViewModel,
    onNavigateToModule: (String) -> Unit,
    onNavigateToChapter: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val userProfile by viewModel.userProfile.collectAsStateWithLifecycle()
    val completedChapters by viewModel.completedChapters.collectAsStateWithLifecycle()
    val quizRecords by viewModel.quizRecords.collectAsStateWithLifecycle()
    val bookmarks by viewModel.bookmarks.collectAsStateWithLifecycle()
    val allNotes by viewModel.allNotes.collectAsStateWithLifecycle()
    val allModules = viewModel.getAllModules()

    val (overallPercentage, cadetLevel) = viewModel.getProgressStats(completedChapters)

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var showEditProfileDialog by remember { mutableStateOf(false) }
    var selectedCertificateModuleTitle by remember { mutableStateOf<String?>(null) }
    var selectedCertificateScore by remember { mutableIntStateOf(80) }

    val tabs = listOf("Progres Modul", "Jenjang Kader", "Sertifikat & Catatan")

    Scaffold(
        topBar = {
            KepTopAppBar(
                title = "Profil & Progres Kader",
                subtitle = "Pelacakan Modul Pelatihan SP KEP SPSI"
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .testTag("profile_screen_list"),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Digital KTA Membership Card (SPSI Blue/Red/White)
            item {
                DigitalMembershipCard(
                    name = userProfile.name,
                    puk = userProfile.pukName,
                    sector = userProfile.companySector,
                    memberNumber = userProfile.memberNumber,
                    location = userProfile.plantLocation,
                    cadetLevel = cadetLevel,
                    onEditClick = { showEditProfileDialog = true }
                )
            }

            // 2. Navigation Tabs for Profile & Tracking
            item {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TabRow(
                        selectedTabIndex = selectedTabIndex,
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.primary,
                        indicator = { tabPositions ->
                            TabRowDefaults.SecondaryIndicator(
                                Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                                color = MaterialTheme.colorScheme.primary,
                                height = 3.dp
                            )
                        }
                    ) {
                        tabs.forEachIndexed { index, title ->
                            Tab(
                                selected = selectedTabIndex == index,
                                onClick = { selectedTabIndex = index },
                                text = {
                                    Text(
                                        text = title,
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                                            fontSize = 12.sp
                                        ),
                                        maxLines = 1
                                    )
                                },
                                modifier = Modifier.testTag("profile_tab_$index")
                            )
                        }
                    }
                }
            }

            // 3. Tab Content
            when (selectedTabIndex) {
                0 -> {
                    // TAB 1: MODUL PELATIHAN & PROGRESS TRACKING
                    item {
                        OverallTrainingProgressCard(
                            overallPercentage = overallPercentage,
                            completedChaptersCount = completedChapters.size,
                            totalChaptersCount = 8,
                            totalModulesCount = allModules.size,
                            completedModulesCount = allModules.count { module ->
                                val modChapters = viewModel.getChapters(module.id)
                                modChapters.isNotEmpty() && modChapters.all { ch ->
                                    completedChapters.any { it.chapterId == ch.id }
                                }
                            },
                            quizRecords = quizRecords
                        )
                    }

                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Daftar Modul & Visualisasi Progres",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            )
                            Text(
                                text = "${allModules.size} Modul",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                        }
                    }

                    items(allModules) { module ->
                        val moduleChapters = viewModel.getChapters(module.id)
                        ModuleProgressTrackingCard(
                            module = module,
                            chapters = moduleChapters,
                            completedChapters = completedChapters,
                            quizRecord = quizRecords.find { it.moduleId == module.id },
                            onModuleClick = { onNavigateToModule(module.id) }
                        )
                    }
                }

                1 -> {
                    // TAB 2: JENJANG KADERISASI MILESTONES
                    item {
                        CadetProgressionCard(
                            currentLevel = cadetLevel,
                            percentage = overallPercentage,
                            completedCount = completedChapters.size,
                            totalCount = 8
                        )
                    }

                    item {
                        CadetCompetencyGuidelinesCard()
                    }
                }

                2 -> {
                    // TAB 3: SERTIFIKAT & CATATAN
                    item {
                        CertificatesSection(
                            quizRecords = quizRecords,
                            viewModel = viewModel,
                            onViewCertificate = { title, score ->
                                selectedCertificateModuleTitle = title
                                selectedCertificateScore = score
                            }
                        )
                    }

                    item {
                        BookmarksSection(
                            bookmarks = bookmarks,
                            onNavigateToChapter = onNavigateToChapter
                        )
                    }

                    item {
                        NotesSection(
                            allNotes = allNotes,
                            onDeleteNote = { viewModel.deleteNote(it) }
                        )
                    }
                }
            }
        }

        // Edit Profile Dialog
        if (showEditProfileDialog) {
            EditProfileDialog(
                currentProfile = userProfile,
                onDismiss = { showEditProfileDialog = false },
                onSave = { name, puk, sector, memberNo, location ->
                    viewModel.updateProfile(name, puk, sector, memberNo, location)
                    showEditProfileDialog = false
                }
            )
        }

        // Certificate Viewer Dialog
        if (selectedCertificateModuleTitle != null) {
            CertificateDialog(
                userName = userProfile.name,
                pukName = userProfile.pukName,
                moduleTitle = selectedCertificateModuleTitle!!,
                memberNumber = userProfile.memberNumber,
                scorePercentage = selectedCertificateScore,
                onDismiss = { selectedCertificateModuleTitle = null }
            )
        }
    }
}

@Composable
fun OverallTrainingProgressCard(
    overallPercentage: Int,
    completedChaptersCount: Int,
    totalChaptersCount: Int,
    totalModulesCount: Int,
    completedModulesCount: Int,
    quizRecords: List<QuizRecordEntity>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("overall_training_progress_card"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                        modifier = Modifier.size(36.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Filled.BarChart,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Ringkasan Pembelajaran",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                        )
                        Text(
                            text = "Kurikulum Nasional SP KEP SPSI",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 11.sp
                            )
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (overallPercentage == 100) Color(0xFFDCFCE7) else MaterialTheme.colorScheme.primaryContainer
                ) {
                    Text(
                        text = "$overallPercentage% Selesai",
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = if (overallPercentage == 100) Color(0xFF15803D) else MaterialTheme.colorScheme.onPrimaryContainer,
                            fontSize = 12.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Main Progress Bar Component
            LinearProgressIndicator(
                progress = { overallPercentage / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .clip(RoundedCornerShape(5.dp)),
                color = if (overallPercentage == 100) Color(0xFF16A34A) else MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 3 Key Metrics Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ProgressMetricItem(
                    label = "Modul Tuntas",
                    value = "$completedModulesCount / $totalModulesCount",
                    icon = Icons.Filled.School,
                    accentColor = Color(0xFF0F438A)
                )

                ProgressMetricItem(
                    label = "Bab Selesai",
                    value = "$completedChaptersCount / $totalChaptersCount",
                    icon = Icons.Filled.FactCheck,
                    accentColor = Color(0xFFC01525)
                )

                val passedCount = quizRecords.count { it.passed }
                ProgressMetricItem(
                    label = "Uji Lulus",
                    value = "$passedCount Modul",
                    icon = Icons.Filled.EmojiEvents,
                    accentColor = Color(0xFFD97706)
                )
            }
        }
    }
}

@Composable
fun ProgressMetricItem(
    label: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    accentColor: Color
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = accentColor.copy(alpha = 0.08f),
        modifier = Modifier.width(100.dp)
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = accentColor,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = accentColor
                )
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}

@Composable
fun ModuleProgressTrackingCard(
    module: LearningModule,
    chapters: List<Chapter>,
    completedChapters: List<CompletedChapterEntity>,
    quizRecord: QuizRecordEntity?,
    onModuleClick: () -> Unit
) {
    val totalChapters = chapters.size
    val completedInModule = completedChapters.count { completed ->
        chapters.any { it.id == completed.chapterId }
    }
    val progressRatio = if (totalChapters > 0) completedInModule.toFloat() / totalChapters.toFloat() else 0f
    val progressPercent = (progressRatio * 100).toInt()
    val isCompleted = progressPercent == 100

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onModuleClick() }
            .testTag("module_progress_card_${module.id}"),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 1.5.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header: Category Badge + Reading Time + Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SectorCategoryBadge(category = module.category)

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Timer,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${module.readDurationMinutes} Menit",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 11.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Module Title
            Text(
                text = module.title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = module.description,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Progress Bar Visualizer Component
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isCompleted) "✓ Selesai Semua Bab" else "$completedInModule dari $totalChapters Bab Selesai",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = if (isCompleted) Color(0xFF16A34A) else MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 11.sp
                    )
                )
                Text(
                    text = "$progressPercent%",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = if (isCompleted) Color(0xFF16A34A) else MaterialTheme.colorScheme.primary,
                        fontSize = 12.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            LinearProgressIndicator(
                progress = { progressRatio },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(7.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = if (isCompleted) Color(0xFF16A34A) else MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Chapters Breakdown & Quiz Status Pill
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Quiz Status Pill
                if (quizRecord != null && quizRecord.passed) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFFDCFCE7)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Verified,
                                contentDescription = null,
                                tint = Color(0xFF15803D),
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Kuis Lulus: ${quizRecord.percentage}%",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = Color(0xFF15803D),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp
                                )
                            )
                        }
                    }
                } else if (isCompleted) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFFFEF3C7)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Quiz,
                                contentDescription = null,
                                tint = Color(0xFFB45309),
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Siap Uji Kuis",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = Color(0xFFB45309),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp
                                )
                            )
                        }
                    }
                } else {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = "${totalChapters - completedInModule} Bab Tersisa",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 10.sp
                            )
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onModuleClick() }
                ) {
                    Text(
                        text = if (isCompleted) "Pelajari Ulang" else "Lanjutkan Modul",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        )
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun DigitalMembershipCard(
    name: String,
    puk: String,
    sector: String,
    memberNumber: String,
    location: String,
    cadetLevel: CadetLevel,
    onEditClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF082046),
                        Color(0xFF0F438A),
                        Color(0xFF1E5BB4)
                    )
                )
            )
            .border(1.5.dp, Color(0xFFC01525), RoundedCornerShape(20.dp))
            .padding(18.dp)
            .testTag("digital_kta_card")
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFFC01525),
                        modifier = Modifier.size(30.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "KARTU TANDA ANGGOTA (KTA)",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = Color(0xFFFDE68A),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp,
                                    letterSpacing = 0.5.sp
                                )
                            )
                        }
                        Text(
                            text = "FSP KEP SPSI • SOLIDARITAS",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                        )
                    }
                }

                IconButton(
                    onClick = onEditClick,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Ubah Profil",
                        tint = Color(0xFFFDE68A),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 19.sp
                )
            )

            Text(
                text = puk,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFFD8E4FF),
                    fontSize = 12.sp
                )
            )

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = Color(0x33FFFFFF))
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "NO. ANGGOTA:",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Color(0xFFCBD5E1),
                            fontSize = 9.sp
                        )
                    )
                    Text(
                        text = memberNumber,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        )
                    )
                }

                Column {
                    Text(
                        text = "SEKTOR INDUSTRI:",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Color(0xFFCBD5E1),
                            fontSize = 9.sp
                        )
                    )
                    Text(
                        text = sector,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        )
                    )
                }

                CadetLevelBadge(cadetLevel = cadetLevel)
            }
        }
    }
}

@Composable
fun CadetProgressionCard(
    currentLevel: CadetLevel,
    percentage: Int,
    completedCount: Int,
    totalCount: Int
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.School,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Jenjang Kaderisasi SP KEP SPSI",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                Text(
                    text = "$percentage%",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            LinearProgressIndicator(
                progress = { percentage / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(7.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )

            Spacer(modifier = Modifier.height(14.dp))

            val levels = listOf(
                Pair(CadetLevel.CALON_KADER, "Dasar Organisasi & Pengenalan SPSI"),
                Pair(CadetLevel.KADER_PRATAMA, "Penguasaan Hukum Hubungan Industrial & Bipartit"),
                Pair(CadetLevel.KADER_MADYA, "Keahlian Negosiasi PKB & Advokasi Litigasi"),
                Pair(CadetLevel.KADER_UTAMA, "Kepemimpinan Strategis & K3 Spesialis Sektor KEP")
            )

            levels.forEach { (lvl, desc) ->
                val isCurrentOrPassed = when (currentLevel) {
                    CadetLevel.KADER_UTAMA -> true
                    CadetLevel.KADER_MADYA -> lvl != CadetLevel.KADER_UTAMA
                    CadetLevel.KADER_PRATAMA -> lvl == CadetLevel.CALON_KADER || lvl == CadetLevel.KADER_PRATAMA
                    CadetLevel.CALON_KADER -> lvl == CadetLevel.CALON_KADER
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = if (isCurrentOrPassed) Color(0xFFDCFCE7) else MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier.size(22.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            if (isCurrentOrPassed) {
                                Icon(
                                    imageVector = Icons.Filled.Verified,
                                    contentDescription = null,
                                    tint = Color(0xFF16A34A),
                                    modifier = Modifier.size(14.dp)
                                )
                            } else {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .background(Color.Gray, CircleShape)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = lvl.title,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = if (isCurrentOrPassed) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                        Text(
                            text = desc,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 11.sp
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CadetCompetencyGuidelinesCard() {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = Color(0xFFF8FAFC)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Timeline,
                    contentDescription = null,
                    tint = Color(0xFF0F438A),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Syarat Kenaikan Jenjang Kader",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F438A)
                    )
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            val guidelines = listOf(
                "• Calon Kader: Membaca minimal 1 Bab Dasar Serikat.",
                "• Kader Pratama: Menyelesaikan minimal 4 Bab & lulus 1 Kuis Bipartit/PKB.",
                "• Kader Madya: Menyelesaikan minimal 7 Bab & lulus 3 Kuis Ketenagakerjaan.",
                "• Kader Utama: Menyelesaikan 100% Seluruh Modul & lulus semua Uji Kompetensi."
            )
            guidelines.forEach { line ->
                Text(
                    text = line,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color(0xFF334155),
                        fontSize = 11.5.sp,
                        lineHeight = 16.sp
                    ),
                    modifier = Modifier.padding(vertical = 2.dp)
                )
            }
        }
    }
}

@Composable
fun CertificatesSection(
    quizRecords: List<QuizRecordEntity>,
    viewModel: LearningViewModel,
    onViewCertificate: (String, Int) -> Unit
) {
    Text(
        text = "E-Sertifikat Kelulusan Uji Kompetensi",
        style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        ),
        modifier = Modifier.padding(vertical = 4.dp)
    )

    val passedQuizzes = quizRecords.filter { it.passed }
    if (passedQuizzes.isEmpty()) {
        OutlinedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.MilitaryTech,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Selesaikan modul dan lulus kuis (nilai >= 75%) untuk membuka e-sertifikat kompetensi kader resmi.",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 12.sp
                    )
                )
            }
        }
    } else {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            passedQuizzes.forEach { record ->
                val module = viewModel.getModule(record.moduleId)
                val title = module?.title ?: "Modul SP KEP SPSI"
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .clickable { onViewCertificate(title, record.percentage) },
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = Color(0xFFFFFBEB)
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = Color(0xFFD97706),
                                modifier = Modifier.size(36.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Filled.MilitaryTech,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp
                                    )
                                )
                                Text(
                                    text = "Lulus Nilai ${record.percentage}% • Ketuk untuk lihat sertifikat",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = Color(0xFFB45309),
                                        fontSize = 11.sp
                                    )
                                )
                            }
                        }
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Buka",
                            tint = Color(0xFFB45309)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BookmarksSection(
    bookmarks: List<com.example.data.db.BookmarkEntity>,
    onNavigateToChapter: (String) -> Unit
) {
    Text(
        text = "Materi Tersimpan (${bookmarks.size})",
        style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        ),
        modifier = Modifier.padding(top = 10.dp, bottom = 4.dp)
    )

    if (bookmarks.isEmpty()) {
        Text(
            text = "Belum ada materi yang ditandai. Tekan ikon markah saat membaca bab untuk menyimpannya di sini.",
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 12.sp
            ),
            modifier = Modifier.padding(vertical = 4.dp)
        )
    } else {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            bookmarks.forEach { bookmark ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onNavigateToChapter(bookmark.chapterId) },
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Bookmark,
                            contentDescription = null,
                            tint = Color(0xFFD97706),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = bookmark.chapterTitle,
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            )
                            Text(
                                text = bookmark.moduleTitle,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 11.sp
                                )
                            )
                        }
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Buka",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NotesSection(
    allNotes: List<com.example.data.db.UserNoteEntity>,
    onDeleteNote: (Long) -> Unit
) {
    Text(
        text = "Semua Catatan Saya (${allNotes.size})",
        style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        ),
        modifier = Modifier.padding(top = 10.dp, bottom = 4.dp)
    )

    if (allNotes.isEmpty()) {
        Text(
            text = "Belum ada catatan belajar yang dibuat.",
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 12.sp
            ),
            modifier = Modifier.padding(vertical = 4.dp)
        )
    } else {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            allNotes.forEach { note ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = note.title,
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            )
                            Text(
                                text = "Bab: ${note.chapterTitle}",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = 10.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = note.content,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 12.sp
                                )
                            )
                        }
                        IconButton(
                            onClick = { onDeleteNote(note.id) },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Hapus",
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EditProfileDialog(
    currentProfile: com.example.data.db.UserProfileEntity,
    onDismiss: () -> Unit,
    onSave: (String, String, String, String, String) -> Unit
) {
    var name by remember { mutableStateOf(currentProfile.name) }
    var puk by remember { mutableStateOf(currentProfile.pukName) }
    var sector by remember { mutableStateOf(currentProfile.companySector) }
    var memberNo by remember { mutableStateOf(currentProfile.memberNumber) }
    var location by remember { mutableStateOf(currentProfile.plantLocation) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Ubah Data Kader SP KEP SPSI", fontWeight = FontWeight.Bold) },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nama Lengkap Kader") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = puk,
                    onValueChange = { puk = it },
                    label = { Text("Nama PUK / Perusahaan") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = sector,
                    onValueChange = { sector = it },
                    label = { Text("Sektor (Kimia / Energi / Pertambangan)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = memberNo,
                    onValueChange = { memberNo = it },
                    label = { Text("Nomor Anggota (KTA)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("Lokasi Plant / Kawasan Industri") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(name, puk, sector, memberNo, location) }
            ) {
                Text("Simpan Perubahan")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Batal")
            }
        }
    )
}
