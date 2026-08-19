package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.NoteAlt
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.CaseStudy
import com.example.data.model.Chapter
import com.example.data.model.ChapterSection
import com.example.ui.components.KepTopAppBar
import com.example.ui.viewmodel.LearningViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ChapterReadingScreen(
    chapterId: String,
    viewModel: LearningViewModel,
    onBackClick: () -> Unit,
    onNavigateToChapter: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val chapter = viewModel.getChapter(chapterId)
    if (chapter == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Bab tidak ditemukan.")
        }
        return
    }

    val completedChapters by viewModel.completedChapters.collectAsStateWithLifecycle()
    val bookmarks by viewModel.bookmarks.collectAsStateWithLifecycle()
    val allNotes by viewModel.allNotes.collectAsStateWithLifecycle()

    val isCompleted = completedChapters.any { it.chapterId == chapter.id }
    val isBookmarked = bookmarks.any { it.chapterId == chapter.id }
    val chapterNotes = allNotes.filter { it.chapterId == chapter.id }

    var fontScale by remember { mutableFloatStateOf(1f) }
    var showNoteInput by remember { mutableStateOf(false) }
    var noteTitleInput by remember { mutableStateOf("") }
    var noteContentInput by remember { mutableStateOf("") }

    val nextChapter = viewModel.getNextChapter(chapter)
    val prevChapter = viewModel.getPreviousChapter(chapter)

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Bab ${chapter.chapterIndex}: ${chapter.title}",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            maxLines = 1
                        )
                        Text(
                            text = chapter.subtitle,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 11.sp
                            ),
                            maxLines = 1
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.testTag("chapter_read_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali"
                        )
                    }
                },
                actions = {
                    // Font scaling buttons
                    IconButton(
                        onClick = {
                            fontScale = if (fontScale >= 1.25f) 1f else fontScale + 0.15f
                        },
                        modifier = Modifier.testTag("font_scale_button")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.FormatSize,
                            contentDescription = "Ubah Ukuran Teks",
                            tint = if (fontScale > 1f) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    }

                    // Bookmark Button
                    IconButton(
                        onClick = {
                            viewModel.toggleBookmark(chapter, isBookmarked)
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    if (isBookmarked) "Penanda bab dihapus" else "Bab berhasil disimpan ke Markah!"
                                )
                            }
                        },
                        modifier = Modifier.testTag("bookmark_toggle_button")
                    ) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                            contentDescription = "Tandai",
                            tint = if (isBookmarked) Color(0xFFD97706) else MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .testTag("chapter_reading_content"),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
        ) {
            // Chapter Summary Callout
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.Lightbulb,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Intisari Pembahasan",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = chapter.summary,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                fontSize = (13 * fontScale).sp,
                                lineHeight = (18 * fontScale).sp
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Legal References Chips
            if (chapter.pasalReferences.isNotEmpty()) {
                item {
                    Text(
                        text = "Landasan Hukum & Regulasi:",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        chapter.pasalReferences.forEach { ref ->
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Color(0xFFEFF6FF),
                                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFBFDBFE))
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Gavel,
                                        contentDescription = null,
                                        tint = Color(0xFF1D4ED8),
                                        modifier = Modifier.size(12.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = ref,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = Color(0xFF1E40AF),
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 11.sp
                                        )
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }

            // Chapter Body Sections
            items(chapter.sections) { section ->
                ChapterSectionView(section = section, fontScale = fontScale)
                Spacer(modifier = Modifier.height(18.dp))
            }

            // Case Study (Studi Kasus) if present
            if (chapter.caseStudy != null) {
                item {
                    CaseStudyCard(caseStudy = chapter.caseStudy, fontScale = fontScale)
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }

            // Key Takeaways Box
            if (chapter.keyTakeaways.isNotEmpty()) {
                item {
                    KeyTakeawaysCard(takeaways = chapter.keyTakeaways, fontScale = fontScale)
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }

            // Notes Section for this Chapter
            item {
                ChapterNotesSection(
                    chapterId = chapter.id,
                    notes = chapterNotes,
                    showInput = showNoteInput,
                    titleInput = noteTitleInput,
                    contentInput = noteContentInput,
                    onToggleShowInput = { showNoteInput = !showNoteInput },
                    onTitleChange = { noteTitleInput = it },
                    onContentChange = { noteContentInput = it },
                    onSaveNote = {
                        if (noteContentInput.isNotBlank()) {
                            viewModel.addNote(chapter.id, noteTitleInput, noteContentInput)
                            noteTitleInput = ""
                            noteContentInput = ""
                            showNoteInput = false
                            scope.launch {
                                snackbarHostState.showSnackbar("Catatan berhasil disimpan!")
                            }
                        }
                    },
                    onDeleteNote = { noteId ->
                        viewModel.deleteNote(noteId)
                        scope.launch {
                            snackbarHostState.showSnackbar("Catatan dihapus")
                        }
                    }
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Mark as Completed Button
            item {
                Button(
                    onClick = {
                        viewModel.toggleChapterCompleted(chapter.moduleId, chapter.id, isCompleted)
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                if (isCompleted) "Status bab diubah: Belum selesai" else "Selamat! Bab ini ditandai telah selesai dipelajari."
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("toggle_complete_chapter_button"),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isCompleted) Color(0xFF16A34A) else MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        imageVector = if (isCompleted) Icons.Filled.CheckCircle else Icons.Filled.Check,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isCompleted) "✓ Bab Ini Selesai Dipelajari" else "Tandai Bab Ini Selesai",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Previous & Next Navigation Buttons
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (prevChapter != null) {
                        OutlinedButton(
                            onClick = { onNavigateToChapter(prevChapter.id) },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Bab ${prevChapter.chapterIndex}", maxLines = 1)
                        }
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    if (nextChapter != null) {
                        Button(
                            onClick = { onNavigateToChapter(nextChapter.id) },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Bab ${nextChapter.chapterIndex}", maxLines = 1)
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun ChapterSectionView(section: ChapterSection, fontScale: Float) {
    Column {
        Text(
            text = section.heading,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                fontSize = (16 * fontScale).sp
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = section.content,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = (14 * fontScale).sp,
                lineHeight = (22 * fontScale).sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        )

        // Highlight/Quote Block
        if (!section.quoteOrHighlight.isNullOrBlank()) {
            Spacer(modifier = Modifier.height(10.dp))
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color(0xFFFEF3C7),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFDE68A)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "❝",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Color(0xFFB45309),
                            fontSize = 20.sp
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = section.quoteOrHighlight,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color(0xFF78350F),
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = (13 * fontScale).sp,
                            lineHeight = (18 * fontScale).sp
                        )
                    )
                }
            }
        }

        // Bullet Points
        if (section.bulletPoints.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            section.bulletPoints.forEach { point ->
                Row(
                    modifier = Modifier.padding(vertical = 3.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "•",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = point,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = (13.5f * fontScale).sp,
                            lineHeight = (20 * fontScale).sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun CaseStudyCard(caseStudy: CaseStudy, fontScale: Float) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color(0xFFF8FAFC)
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = CircleShape,
                    color = Color(0xFF2563EB),
                    modifier = Modifier.size(28.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Filled.Psychology,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "STUDI KASUS NYATA",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1D4ED8),
                            letterSpacing = 0.5.sp
                        )
                    )
                    Text(
                        text = caseStudy.title,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = (14 * fontScale).sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            CaseStudyField(label = "Konteks Lapangan:", text = caseStudy.background, fontScale = fontScale)
            CaseStudyField(label = "Masalah yang Terjadi:", text = caseStudy.problem, fontScale = fontScale)
            CaseStudyField(label = "Langkah Advokasi Serikat:", text = caseStudy.unionSolution, fontScale = fontScale, highlightColor = Color(0xFF15803D))
            CaseStudyField(label = "Pelajaran Berharga (Lesson Learned):", text = caseStudy.lessonLearned, fontScale = fontScale, highlightColor = Color(0xFFB45309))
        }
    }
}

@Composable
fun CaseStudyField(
    label: String,
    text: String,
    fontScale: Float,
    highlightColor: Color = MaterialTheme.colorScheme.onSurface
) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = (11 * fontScale).sp
            )
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall.copy(
                color = highlightColor,
                fontSize = (12.5f * fontScale).sp,
                lineHeight = (18 * fontScale).sp
            )
        )
    }
}

@Composable
fun KeyTakeawaysCard(takeaways: List<String>, fontScale: Float) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF0FDF4)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = null,
                    tint = Color(0xFF16A34A),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Poin Kunci yang Harus Diingat Kader",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF14532D)
                    )
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            takeaways.forEach { takeaway ->
                Row(
                    modifier = Modifier.padding(vertical = 3.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "✔",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color(0xFF16A34A),
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = takeaway,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color(0xFF166534),
                            fontSize = (12.5f * fontScale).sp,
                            lineHeight = (17 * fontScale).sp
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun ChapterNotesSection(
    chapterId: String,
    notes: List<com.example.data.db.UserNoteEntity>,
    showInput: Boolean,
    titleInput: String,
    contentInput: String,
    onToggleShowInput: () -> Unit,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    onSaveNote: () -> Unit,
    onDeleteNote: (Long) -> Unit
) {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.NoteAlt,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Catatan Kader (${notes.size})",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                Text(
                    text = if (showInput) "Tutup" else "+ Tulis Catatan",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { onToggleShowInput() }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }

            AnimatedVisibility(visible = showInput) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    OutlinedTextField(
                        value = titleInput,
                        onValueChange = onTitleChange,
                        label = { Text("Judul Catatan (Opsional)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = contentInput,
                        onValueChange = onContentChange,
                        label = { Text("Isi Catatan / Poin Penting") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = onSaveNote,
                        modifier = Modifier.align(Alignment.End),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Simpan Catatan")
                    }
                }
            }

            if (notes.isNotEmpty()) {
                Spacer(modifier = Modifier.height(10.dp))
                notes.forEach { note ->
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = note.title,
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp
                                    )
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = note.content,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurface
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
}
