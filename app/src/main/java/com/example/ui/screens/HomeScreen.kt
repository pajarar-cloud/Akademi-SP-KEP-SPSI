package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.GroupWork
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.db.CompletedChapterEntity
import com.example.data.model.LearningModule
import com.example.data.model.SectorCategory
import com.example.ui.components.CadetLevelBadge
import com.example.ui.components.SectorCategoryBadge
import com.example.ui.viewmodel.LearningViewModel
import com.example.ui.viewmodel.ScreenNav

@Composable
fun HomeScreen(
    viewModel: LearningViewModel,
    onNavigateToModule: (String) -> Unit,
    onNavigateToTools: () -> Unit,
    onNavigateToDictionary: () -> Unit,
    onNavigateToProfile: () -> Unit,
    modifier: Modifier = Modifier
) {
    val userProfile by viewModel.userProfile.collectAsStateWithLifecycle()
    val completedChapters by viewModel.completedChapters.collectAsStateWithLifecycle()
    val selectedSector by viewModel.selectedSector.collectAsStateWithLifecycle()
    val allModules = viewModel.getAllModules()

    val (overallPercentage, cadetLevel) = viewModel.getProgressStats(completedChapters)

    val filteredModules = if (selectedSector == SectorCategory.ALL) {
        allModules
    } else {
        allModules.filter { it.category == selectedSector }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("home_screen_list"),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        // Hero Header Card
        item {
            HomeHeroHeader(
                userName = userProfile.name,
                pukName = userProfile.pukName,
                sectorName = userProfile.companySector,
                cadetLevel = cadetLevel,
                progressPercentage = overallPercentage,
                completedChaptersCount = completedChapters.size,
                totalChaptersCount = 8, // total chapters in curriculum
                onProfileClick = onNavigateToProfile
            )
        }

        // Quick Tools Bar
        item {
            Text(
                text = "Akses Cepat & Simulasi",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                ),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            QuickActionsSection(
                onCalculatorClick = onNavigateToTools,
                onDictionaryClick = onNavigateToDictionary,
                onDisputeGuideClick = onNavigateToTools,
                onQuizClick = {
                    val firstModule = allModules.firstOrNull()
                    if (firstModule != null) {
                        viewModel.navigateTo(ScreenNav.Quiz(firstModule.id))
                    }
                }
            )
        }

        // Sector Filter Chips
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Kurikulum & Modul Belajar",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                )
                Text(
                    text = "${filteredModules.size} Modul",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            SectorFilterRow(
                selectedSector = selectedSector,
                onSelectSector = { viewModel.setSectorFilter(it) }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        // List of Learning Modules
        items(filteredModules) { module ->
            val chapters = viewModel.getChapters(module.id)
            val moduleCompletedCount = completedChapters.count { it.moduleId == module.id }
            val moduleProgress = if (chapters.isNotEmpty()) (moduleCompletedCount * 100) / chapters.size else 0

            ModuleCardItem(
                module = module,
                completedCount = moduleCompletedCount,
                totalCount = chapters.size,
                progressPercentage = moduleProgress,
                onClick = { onNavigateToModule(module.id) }
            )
        }

        // Labor Law Spotlight of the Day (Pasal Sorotan Hari Ini)
        item {
            DailyLawSpotlightCard(
                onExploreDictionary = onNavigateToDictionary
            )
        }

        // 6 Doktrin Penguatan SP KEP SPSI
        item {
            SixPillarsDoctrineCard()
        }
    }
}

@Composable
fun HomeHeroHeader(
    userName: String,
    pukName: String,
    sectorName: String,
    cadetLevel: com.example.data.model.CadetLevel,
    progressPercentage: Int,
    completedChaptersCount: Int,
    totalChaptersCount: Int,
    onProfileClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF082046),
                        Color(0xFF0F438A),
                        Color(0xFF1E5BB4)
                    )
                )
            )
            .clickable { onProfileClick() }
            .padding(20.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFC01525)
                        ) {
                            Box(
                                modifier = Modifier.size(24.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Star,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color(0xFFC01525)
                        ) {
                            Text(
                                text = "SPSI • KEP",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 9.sp,
                                    letterSpacing = 0.5.sp
                                )
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "SISTEM KADERISASI",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = Color(0xFFFDE68A),
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.8.sp,
                                fontSize = 10.sp
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = userName,
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = pukName,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color(0xFFD8E4FF),
                            fontSize = 12.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                CadetLevelBadge(cadetLevel = cadetLevel)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Progress bar
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0x33000000),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Progres Kaderisasi: $progressPercentage%",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        Text(
                            text = "$completedChaptersCount dari $totalChaptersCount Bab Selesai",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color(0xFFCBD5E1),
                                fontSize = 11.sp
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = { progressPercentage / 100f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = Color(0xFFF59E0B),
                        trackColor = Color(0x44FFFFFF),
                    )
                }
            }
        }
    }
}

@Composable
fun QuickActionsSection(
    onCalculatorClick: () -> Unit,
    onDictionaryClick: () -> Unit,
    onDisputeGuideClick: () -> Unit,
    onQuizClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        QuickActionItem(
            title = "Kalkulator Pesangon",
            subtitle = "PP 35/2021",
            icon = Icons.Filled.Calculate,
            accentColor = Color(0xFFC01525),
            bgColor = Color(0xFFFEE2E2),
            modifier = Modifier.weight(1f),
            onClick = onCalculatorClick
        )
        QuickActionItem(
            title = "Kamus Hukum",
            subtitle = "Istilah & Regulasi",
            icon = Icons.Filled.MenuBook,
            accentColor = Color(0xFF0F438A),
            bgColor = Color(0xFFDBEAFE),
            modifier = Modifier.weight(1f),
            onClick = onDictionaryClick
        )
        QuickActionItem(
            title = "Alur Sengketa",
            subtitle = "Bipartit & PHI",
            icon = Icons.Filled.Gavel,
            accentColor = Color(0xFF082046),
            bgColor = Color(0xFFE2E8F0),
            modifier = Modifier.weight(1f),
            onClick = onDisputeGuideClick
        )
        QuickActionItem(
            title = "Uji Kompetensi",
            subtitle = "Kuis & Sertifikat",
            icon = Icons.Filled.Quiz,
            accentColor = Color(0xFFD97706),
            bgColor = Color(0xFFFEF3C7),
            modifier = Modifier.weight(1f),
            onClick = onQuizClick
        )
    }
}

@Composable
fun QuickActionItem(
    title: String,
    subtitle: String,
    icon: ImageVector,
    accentColor: Color,
    bgColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .testTag("quick_action_${title.lowercase().replace(" ", "_")}"),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                shape = CircleShape,
                color = bgColor,
                modifier = Modifier.size(38.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = accentColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 9.sp
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun SectorFilterRow(
    selectedSector: SectorCategory,
    onSelectSector: (SectorCategory) -> Unit
) {
    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SectorCategory.values().forEach { category ->
            FilterChip(
                selected = selectedSector == category,
                onClick = { onSelectSector(category) },
                label = {
                    Text(
                        text = category.label,
                        style = MaterialTheme.typography.labelMedium.copy(fontSize = 12.sp)
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    }
}

@Composable
fun ModuleCardItem(
    module: LearningModule,
    completedCount: Int,
    totalCount: Int,
    progressPercentage: Int,
    onClick: () -> Unit
) {
    val icon = when (module.iconName) {
        "groups" -> Icons.Filled.Groups
        "gavel" -> Icons.Filled.Gavel
        "handshake" -> Icons.Filled.Handshake
        "health_and_safety" -> Icons.Filled.HealthAndSafety
        "campaign" -> Icons.Filled.Campaign
        else -> Icons.Filled.School
    }

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .testTag("module_card_${module.id}"),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier.size(46.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
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
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = "${module.readDurationMinutes} mnt",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = module.title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

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

            // Progress & Action Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = if (progressPercentage == 100) "Selesai" else "$completedCount/$totalCount Bab",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = if (progressPercentage == 100) Color(0xFF16A34A) else MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 11.sp
                            )
                        )
                        Text(
                            text = "$progressPercentage%",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 11.sp
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    LinearProgressIndicator(
                        progress = { progressPercentage / 100f },
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = if (progressPercentage == 100) Color(0xFF16A34A) else MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                }

                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                    modifier = Modifier.size(32.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Buka Modul",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DailyLawSpotlightCard(
    onExploreDictionary: () -> Unit
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = Color(0xFFF0FDF4)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = CircleShape,
                    color = Color(0xFF16A34A),
                    modifier = Modifier.size(24.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Filled.Verified,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "PASAL HARI INI",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF15803D),
                        letterSpacing = 0.5.sp
                    )
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Pasal 28 UU No. 21 Tahun 2000 (Hak Berserikat)",
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF14532D)
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "\"Siapapun dilarang menghalang-halangi atau memaksa pekerja/buruh untuk membentuk atau tidak membentuk, menjadi pengurus atau tidak menjadi pengurus, menjadi anggota atau tidak menjadi anggota dan/atau menjalankan atau tidak menjalankan kegiatan serikat pekerja/serikat buruh.\"",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color(0xFF166534),
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Buka Kamus Hukum Lengkap →",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = Color(0xFF15803D),
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .clickable { onExploreDictionary() }
                        .padding(vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
fun SixPillarsDoctrineCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.AccountBalance,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "6 Penguatan Organisasi SP KEP SPSI",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            val pillars = listOf(
                "1. Penguatan SDM (Pendidikan & Kaderisasi)",
                "2. Penguatan Organisasi (Solidaritas Basis Unit Kerja)",
                "3. Penguatan Keuangan & COS (Kemandirian Finansial)",
                "4. Penguatan Advokasi & Pembelaan Hak Hukum",
                "5. Penguatan Hubungan Industrial & PKB Berkualitas",
                "6. Penguatan Opini Publik & Teknologi Informasi"
            )
            pillars.forEach { pillar ->
                Row(
                    modifier = Modifier.padding(vertical = 2.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "•",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = pillar,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 12.sp
                        )
                    )
                }
            }
        }
    }
}
