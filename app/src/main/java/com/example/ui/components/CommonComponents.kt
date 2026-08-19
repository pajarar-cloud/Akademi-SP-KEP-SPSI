package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.outlined.Calculate
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.School
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CadetLevel
import com.example.data.model.LearningModule
import com.example.data.model.SectorCategory
import com.example.ui.theme.SectorAdvocacy
import com.example.ui.theme.SectorAdvocacyBg
import com.example.ui.theme.SectorChemical
import com.example.ui.theme.SectorChemicalBg
import com.example.ui.theme.SectorEnergy
import com.example.ui.theme.SectorEnergyBg
import com.example.ui.theme.SectorK3
import com.example.ui.theme.SectorK3Bg
import com.example.ui.theme.SectorLeadership
import com.example.ui.theme.SectorLeadershipBg
import com.example.ui.theme.SectorMining
import com.example.ui.theme.SectorMiningBg
import com.example.ui.viewmodel.ScreenNav

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KepTopAppBar(
    title: String,
    subtitle: String? = null,
    showBackButton: Boolean = false,
    onBackClick: () -> Unit = {},
    actions: @Composable () -> Unit = {}
) {
    TopAppBar(
        title = {
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (!subtitle.isNullOrBlank()) {
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 12.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        },
        navigationIcon = {
            if (showBackButton) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.testTag("top_bar_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Kembali"
                    )
                }
            }
        },
        actions = { actions() },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface
        )
    )
}

@Composable
fun KepBottomNavigationBar(
    currentScreen: ScreenNav,
    onNavigate: (ScreenNav) -> Unit
) {
    NavigationBar(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.navigationBars)
            .testTag("main_bottom_nav"),
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 6.dp
    ) {
        val isHome = currentScreen is ScreenNav.Home || currentScreen is ScreenNav.ModuleDetail || currentScreen is ScreenNav.ChapterRead || currentScreen is ScreenNav.Quiz
        NavigationBarItem(
            selected = isHome,
            onClick = { onNavigate(ScreenNav.Home) },
            icon = {
                Icon(
                    imageVector = if (isHome) Icons.Filled.Home else Icons.Outlined.Home,
                    contentDescription = "Beranda"
                )
            },
            label = { Text("Belajar", fontSize = 11.sp, fontWeight = if (isHome) FontWeight.Bold else FontWeight.Normal) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                indicatorColor = MaterialTheme.colorScheme.primaryContainer
            )
        )

        val isTools = currentScreen is ScreenNav.LaborTools
        NavigationBarItem(
            selected = isTools,
            onClick = { onNavigate(ScreenNav.LaborTools) },
            icon = {
                Icon(
                    imageVector = if (isTools) Icons.Filled.Calculate else Icons.Outlined.Calculate,
                    contentDescription = "Kalkulator & Advokasi"
                )
            },
            label = { Text("Kalkulator", fontSize = 11.sp, fontWeight = if (isTools) FontWeight.Bold else FontWeight.Normal) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                indicatorColor = MaterialTheme.colorScheme.primaryContainer
            )
        )

        val isDict = currentScreen is ScreenNav.Dictionary
        NavigationBarItem(
            selected = isDict,
            onClick = { onNavigate(ScreenNav.Dictionary) },
            icon = {
                Icon(
                    imageVector = if (isDict) Icons.Filled.MenuBook else Icons.Outlined.MenuBook,
                    contentDescription = "Kamus Hukum"
                )
            },
            label = { Text("Kamus UU", fontSize = 11.sp, fontWeight = if (isDict) FontWeight.Bold else FontWeight.Normal) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                indicatorColor = MaterialTheme.colorScheme.primaryContainer
            )
        )

        val isProfile = currentScreen is ScreenNav.Profile
        NavigationBarItem(
            selected = isProfile,
            onClick = { onNavigate(ScreenNav.Profile) },
            icon = {
                Icon(
                    imageVector = if (isProfile) Icons.Filled.Person else Icons.Outlined.Person,
                    contentDescription = "Profil Kader"
                )
            },
            label = { Text("Kader", fontSize = 11.sp, fontWeight = if (isProfile) FontWeight.Bold else FontWeight.Normal) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                indicatorColor = MaterialTheme.colorScheme.primaryContainer
            )
        )
    }
}

@Composable
fun SectorCategoryBadge(
    category: SectorCategory,
    modifier: Modifier = Modifier
) {
    val (bgColor, textColor) = when (category) {
        SectorCategory.UNION_BASICS -> Pair(SectorAdvocacyBg, SectorAdvocacy)
        SectorCategory.LABOR_LAW -> Pair(SectorAdvocacyBg, SectorAdvocacy)
        SectorCategory.COLLECTIVE_BARGAINING -> Pair(SectorMiningBg, SectorMining)
        SectorCategory.SAFETY_K3 -> Pair(SectorK3Bg, SectorK3)
        SectorCategory.CHEMICAL -> Pair(SectorChemicalBg, SectorChemical)
        SectorCategory.ENERGY -> Pair(SectorEnergyBg, SectorEnergy)
        SectorCategory.MINING -> Pair(SectorMiningBg, SectorMining)
        SectorCategory.LEADERSHIP -> Pair(SectorLeadershipBg, SectorLeadership)
        SectorCategory.ALL -> Pair(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.colorScheme.onSurfaceVariant)
    }

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        color = bgColor
    ) {
        Text(
            text = category.badgeText,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                color = textColor,
                fontSize = 11.sp
            )
        )
    }
}

@Composable
fun CadetLevelBadge(
    cadetLevel: CadetLevel,
    modifier: Modifier = Modifier
) {
    val (bgColor, textColor) = when (cadetLevel) {
        CadetLevel.CALON_KADER -> Pair(Color(0xFFE2E8F0), Color(0xFF475569))
        CadetLevel.KADER_PRATAMA -> Pair(Color(0xFFDBEAFE), Color(0xFF1D4ED8))
        CadetLevel.KADER_MADYA -> Pair(Color(0xFFFEF3C7), Color(0xFFB45309))
        CadetLevel.KADER_UTAMA -> Pair(Color(0xFFDCFCE7), Color(0xFF15803D))
    }

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = bgColor
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.School,
                contentDescription = null,
                tint = textColor,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = cadetLevel.title,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    fontSize = 11.sp
                )
            )
        }
    }
}
