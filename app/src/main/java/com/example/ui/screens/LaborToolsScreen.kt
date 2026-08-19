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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.IndustrialDisputeStep
import com.example.ui.components.KepTopAppBar
import com.example.ui.viewmodel.LearningViewModel
import com.example.ui.viewmodel.OvertimeCalculationResult
import com.example.ui.viewmodel.SeveranceCalculationResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaborToolsScreen(
    viewModel: LearningViewModel,
    modifier: Modifier = Modifier
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("Kalkulator Pesangon", "Kalkulator Lembur", "Alur Sengketa PPHI", "Checklist PKB")

    Column(modifier = modifier.fillMaxSize()) {
        KepTopAppBar(
            title = "Kalkulator & Advokasi",
            subtitle = "Alat Bantu Yuridis & Perhitungan Ketenagakerjaan"
        )

        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            edgePadding = 16.dp,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 13.sp
                        )
                    }
                )
            }
        }

        when (selectedTabIndex) {
            0 -> SeveranceCalculatorTab(viewModel = viewModel)
            1 -> OvertimeCalculatorTab(viewModel = viewModel)
            2 -> IndustrialDisputeFlowTab(viewModel = viewModel)
            3 -> NegotiationChecklistTab()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeveranceCalculatorTab(viewModel: LearningViewModel) {
    var serviceYearsInput by remember { mutableStateOf("5") }
    var monthlyWageInput by remember { mutableStateOf("7500000") }
    var selectedReasonIndex by remember { mutableIntStateOf(0) }
    var isDropdownExpanded by remember { mutableStateOf(false) }
    var calculationResult by remember { mutableStateOf<SeveranceCalculationResult?>(null) }

    val reasons = listOf(
        "Efisiensi Karena Perusahaan Merugi (UP 0.5x, UPMK 1x)",
        "Efisiensi Mencegah Kerugian (UP 1x, UPMK 1x)",
        "Pekerja Mencapai Usia Pensiun (UP 1.75x, UPMK 1x)",
        "Mengundurkan Diri Sukarela / Resign (Uang Pisah/UPH)",
        "Sakit Berkepanjangan >12 Bulan / Cacat Kerja (UP 2x, UPMK 1x)"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("severance_calc_tab"),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Simulasi Perhitungan Pesangon & PHK",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    Text(
                        text = "Berdasarkan Peraturan Pemerintah (PP) No. 35 Tahun 2021",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 11.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Input Masa Kerja
                    OutlinedTextField(
                        value = serviceYearsInput,
                        onValueChange = { serviceYearsInput = it },
                        label = { Text("Masa Kerja (Tahun Penuh)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        supportingText = { Text("Contoh: 5 tahun") }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Input Upah Bulanan
                    OutlinedTextField(
                        value = monthlyWageInput,
                        onValueChange = { monthlyWageInput = it },
                        label = { Text("Upah Pokok + Tunjangan Tetap (Rp/Bulan)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        supportingText = { Text("Komponen upah tetap bulanan") }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Dropdown Alasan PHK
                    ExposedDropdownMenuBox(
                        expanded = isDropdownExpanded,
                        onExpandedChange = { isDropdownExpanded = !isDropdownExpanded }
                    ) {
                        OutlinedTextField(
                            value = reasons[selectedReasonIndex],
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Alasan Pengakhiran Hubungan Kerja (PHK)") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropdownExpanded) },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                        )

                        ExposedDropdownMenu(
                            expanded = isDropdownExpanded,
                            onDismissRequest = { isDropdownExpanded = false }
                        ) {
                            reasons.forEachIndexed { index, reason ->
                                DropdownMenuItem(
                                    text = { Text(reason, fontSize = 12.sp) },
                                    onClick = {
                                        selectedReasonIndex = index
                                        isDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Button(
                        onClick = {
                            val years = serviceYearsInput.toIntOrNull() ?: 1
                            val wage = monthlyWageInput.toDoubleOrNull() ?: 5000000.0
                            calculationResult = viewModel.calculateSeverance(years, wage, selectedReasonIndex)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("btn_calculate_severance"),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(imageVector = Icons.Filled.Calculate, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Hitung Hak Kompensasi PHK", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Calculation Result View
        if (calculationResult != null) {
            val res = calculationResult!!
            item {
                Spacer(modifier = Modifier.height(16.dp))
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
                                color = Color(0xFF16A34A),
                                modifier = Modifier.size(28.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Filled.Paid,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Rincian Estimasi Hak Kompensasi",
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                                )
                                Text(
                                    text = res.reason,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        fontSize = 11.sp
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))
                        HorizontalDivider()
                        Spacer(modifier = Modifier.height(10.dp))

                        SeveranceDetailRow(
                            label = "1. Uang Pesangon (UP)",
                            multiplier = "${res.pesangonMultiplier}x Upah",
                            amount = res.pesangonAmount
                        )
                        SeveranceDetailRow(
                            label = "2. Uang Penghargaan Masa Kerja (UPMK)",
                            multiplier = "${res.upmkMultiplier}x Upah",
                            amount = res.upmkAmount
                        )
                        SeveranceDetailRow(
                            label = "3. Uang Penggantian Hak (UPH - Est. Cuti)",
                            multiplier = "Estimasi Hak",
                            amount = res.uphAmount
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                        HorizontalDivider()
                        Spacer(modifier = Modifier.height(10.dp))

                        // Total Row Highlight
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFFDCFCE7),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "TOTAL KOMPENSASI:",
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF14532D)
                                    )
                                )
                                Text(
                                    text = "Rp %,.0f".format(res.totalSeverance),
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF15803D),
                                        fontSize = 17.sp
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.Gavel,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Dasar Hukum: ${res.legalBasis}",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SeveranceDetailRow(label: String, multiplier: String, amount: Double) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp
                )
            )
            Text(
                text = multiplier,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 10.sp
                )
            )
        }
        Text(
            text = "Rp %,.0f".format(amount),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            )
        )
    }
}

@Composable
fun OvertimeCalculatorTab(viewModel: LearningViewModel) {
    var monthlyWageInput by remember { mutableStateOf("6500000") }
    var overtimeHoursInput by remember { mutableStateOf("4") }
    var isWorkDay by remember { mutableStateOf(true) }
    var overtimeResult by remember { mutableStateOf<OvertimeCalculationResult?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("overtime_calc_tab"),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Kalkulator Upah Kerja Lembur",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    Text(
                        text = "Formula: Upah 1 Jam = 1/173 × Upah Sebulan (PP 35/2021)",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 11.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = monthlyWageInput,
                        onValueChange = { monthlyWageInput = it },
                        label = { Text("Upah Sebulan (Gaji Pokok + Tunjangan Tetap)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Jenis Hari Lembur:",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clickable { isWorkDay = true }
                                .padding(vertical = 4.dp)
                        ) {
                            RadioButton(selected = isWorkDay, onClick = { isWorkDay = true })
                            Text("Hari Kerja Biasa", fontSize = 13.sp)
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clickable { isWorkDay = false }
                                .padding(vertical = 4.dp)
                        ) {
                            RadioButton(selected = !isWorkDay, onClick = { isWorkDay = false })
                            Text("Hari Libur / Mingguan", fontSize = 13.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = overtimeHoursInput,
                        onValueChange = { overtimeHoursInput = it },
                        label = { Text("Jumlah Jam Lembur") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Button(
                        onClick = {
                            val wage = monthlyWageInput.toDoubleOrNull() ?: 5000000.0
                            val hours = overtimeHoursInput.toDoubleOrNull() ?: 1.0
                            val dayType = if (isWorkDay) "HARI_KERJA" else "HARI_LIBUR"
                            overtimeResult = viewModel.calculateOvertime(wage, dayType, hours)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("btn_calculate_overtime"),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(imageVector = Icons.Filled.Timer, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Hitung Upah Lembur", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        if (overtimeResult != null) {
            val res = overtimeResult!!
            item {
                Spacer(modifier = Modifier.height(16.dp))
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.elevatedCardColors(containerColor = Color(0xFFF8FAFC))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Hasil Perhitungan Lembur",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = "Upah/Jam: Rp %,.0f".format(res.hourlyRate),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        HorizontalDivider()
                        Spacer(modifier = Modifier.height(10.dp))

                        res.breakdown.forEach { item ->
                            Text(
                                text = "• $item",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 12.sp,
                                    lineHeight = 18.sp
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFFDBEAFE),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "TOTAL UPAH LEMBUR:",
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF1E3A8A)
                                    )
                                )
                                Text(
                                    text = "Rp %,.0f".format(res.totalOvertimePay),
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF1D4ED8),
                                        fontSize = 17.sp
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun IndustrialDisputeFlowTab(viewModel: LearningViewModel) {
    val steps = viewModel.getDisputeSteps()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("dispute_flow_tab"),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f))
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Gavel,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Alur Penyelesaian Sengketa (UU No. 2/2004)",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )
                        Text(
                            text = "Panduan taktis tahapan advokasi hubungan industrial dari Bipartit hingga Mahkamah Agung.",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                fontSize = 11.sp
                            )
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
        }

        items(steps) { step ->
            var isExpanded by remember { mutableStateOf(false) }

            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { isExpanded = !isExpanded }
                    .testTag("dispute_step_${step.stepNumber}"),
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
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "${step.stepNumber}",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = step.title,
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            )
                            Text(
                                text = "Batas Waktu: ${step.timelineLimit}",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = Color(0xFFD97706),
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 11.sp
                                )
                            )
                        }

                        Icon(
                            imageVector = if (isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = step.description,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 12.sp
                        )
                    )

                    AnimatedVisibility(visible = isExpanded) {
                        Column(modifier = Modifier.padding(top = 12.dp)) {
                            HorizontalDivider()
                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = "Pihak Terkait: ${step.actor}",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 12.sp
                                )
                            )
                            Text(
                                text = "Dasar Hukum: ${step.legalBasis}",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp
                                )
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = "Dokumen Wajib (Checklist):",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF15803D)
                                )
                            )
                            step.checklistDocs.forEach { doc ->
                                Text(
                                    text = "  ☑ $doc",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontSize = 11.sp,
                                        lineHeight = 16.sp
                                    )
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Taktik & Strategi Serikat:",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFB45309)
                                )
                            )
                            step.keyTactics.forEach { tactic ->
                                Text(
                                    text = "  ⚡ $tactic",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontSize = 11.sp,
                                        lineHeight = 16.sp
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NegotiationChecklistTab() {
    val checklistItems = remember {
        mutableStateListOf(
            Pair("Penyebaran Kuesioner Aspirasi Upah & Fasilitas ke Anggota Basis", true),
            Pair("Benchmarking Paket Remunerasi Sektor KEP di Kawasan Industri", true),
            Pair("Analisis Laporan Keuangan Publik & Neraca Laba Rugi Perusahaan", false),
            Pair("Penyusunan Draf Tandingan PKB oleh Tim Perunding PUK", true),
            Pair("Penerbitan Surat Keputusan (SK) Tim Perunding PUK SP KEP SPSI", false),
            Pair("Penyusunan Tata Tertib Perundingan Bipartit Bersama Manajemen", false),
            Pair("Simulasi & Roleplay Negosiasi Meja Bipartit dengan Pakar/PC", false),
            Pair("Rencana Komunikasi Berkala Perkembangan Perundingan ke Anggota", true)
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("negotiation_checklist_tab"),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF3C7))
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Handshake,
                        contentDescription = null,
                        tint = Color(0xFFB45309),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Kesiapan Tim Perundingan PKB",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF78350F)
                            )
                        )
                        Text(
                            text = "Checklist pra-perundingan untuk memastikan posisi tawar serikat pekerja maksimal.",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color(0xFF92400E),
                                fontSize = 11.sp
                            )
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
        }

        items(checklistItems.size) { index ->
            val (task, isChecked) = checklistItems[index]
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable {
                        checklistItems[index] = Pair(task, !isChecked)
                    },
                colors = CardDefaults.cardColors(
                    containerColor = if (isChecked) Color(0xFFF0FDF4) else MaterialTheme.colorScheme.surface
                )
            ) {
                Row(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = isChecked,
                        onCheckedChange = { checked ->
                            checklistItems[index] = Pair(task, checked)
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = task,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = if (isChecked) FontWeight.SemiBold else FontWeight.Normal,
                            color = if (isChecked) Color(0xFF14532D) else MaterialTheme.colorScheme.onSurface,
                            fontSize = 13.sp
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}
