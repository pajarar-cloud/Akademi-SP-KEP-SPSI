package com.example.data.repository

import com.example.data.model.CadetLevel
import com.example.data.model.CaseStudy
import com.example.data.model.Chapter
import com.example.data.model.ChapterSection
import com.example.data.model.IndustrialDisputeStep
import com.example.data.model.LaborLawTerm
import com.example.data.model.LearningModule
import com.example.data.model.QuizQuestion
import com.example.data.model.SectorCategory

object LearningContentData {

    val modules: List<LearningModule> = listOf(
        LearningModule(
            id = "mod_dasar_organisasi",
            title = "Falsafah & Dasar Serikat Pekerja",
            subtitle = "Prinsip dasar, AD/ART, dan 6 Penguatan SP KEP SPSI",
            category = SectorCategory.UNION_BASICS,
            readDurationMinutes = 25,
            iconName = "groups",
            description = "Memahami jati diri, sejarah perjuangan kaum buruh sektor KEP, landasan hukum berserikat di Indonesia, dan struktur organisasi dari tingkat PUK hingga DPP.",
            keyPoints = listOf(
                "Hak Berserikat & Konvensi ILO No. 87 & 98",
                "UU No. 21 Tahun 2000 & Perlindungan Anti-Union Busting",
                "Struktur PUK, PC, PD, hingga DPP SP KEP SPSI",
                "Doktrin 6 Penguatan Organisasi SP KEP SPSI"
            ),
            targetCadetLevel = CadetLevel.CALON_KADER
        ),
        LearningModule(
            id = "mod_hukum_advokasi",
            title = "Hukum Ketenagakerjaan & PPHI",
            subtitle = "Penyelesaian Perselisihan Hubungan Industrial & Advokasi",
            category = SectorCategory.LABOR_LAW,
            readDurationMinutes = 35,
            iconName = "gavel",
            description = "Kajian mendalam UU 13/2003, UU 2/2004, dan PP 35/2021. Panduan menangani perselisihan hak, kepentingan, PHK, dan antar serikat secara yuridis.",
            keyPoints = listOf(
                "Jenis-jenis Perselisihan Hubungan Industrial",
                "Tahapan Bipartit Wajib (Maksimal 30 Hari Kerja)",
                "Proses Tripartit (Mediasi / Konsiliasi)",
                "Gugatan ke Pengadilan Hubungan Industrial (PHI) & Kasasi MA",
                "Aturan Mogok Kerja Sah & Hak Normatif Buruh"
            ),
            targetCadetLevel = CadetLevel.KADER_PRATAMA
        ),
        LearningModule(
            id = "mod_pkb_negosiasi",
            title = "Teknik Perundingan & Pembuatan PKB",
            subtitle = "Menyusun Perjanjian Kerja Bersama yang Pro-Pekerja",
            category = SectorCategory.COLLECTIVE_BARGAINING,
            readDurationMinutes = 30,
            iconName = "handshake",
            description = "Strategi menyusun klausul PKB di atas standar normatif undang-undang, teknik negosiasi tim perunding PUK, dan analisis laporan keuangan perusahaan.",
            keyPoints = listOf(
                "Prinsip PKB: Tidak Boleh Lebih Rendah dari Undang-Undang",
                "Tahapan Pembentukan Tim & Pembuatan Draf Tandingan",
                "Klausul Kesejahteraan: Upah Sundulan & Skala Upah",
                "Taktik Deadlock & Komunikasi dengan Anggota Basis"
            ),
            targetCadetLevel = CadetLevel.KADER_MADYA
        ),
        LearningModule(
            id = "mod_k3_kep",
            title = "K3 Sektor Kimia, Energi & Pertambangan",
            subtitle = "Keselamatan Kerja, B3, LOTO, dan Hak Menolak Bekerja Tidak Aman",
            category = SectorCategory.SAFETY_K3,
            readDurationMinutes = 28,
            iconName = "health_and_safety",
            description = "Spesialisasi K3 pada industri berisiko tinggi. Identifikasi bahaya B3, keselamatan kilang minyak & gas, tambang terbuka/bawah tanah, dan regulasi SMK3.",
            keyPoints = listOf(
                "UU No. 1 Tahun 1970 & PP 50/2012 (SMK3)",
                "Manajemen B3 (Bahan Berbahaya & Beracun) & MSDS",
                "Prosedur Lockout/Tagout (LOTO) & Ruang Terbatas (Confined Space)",
                "Peran Panitia Pembina K3 (P2K3) di Tempat Kerja",
                "Hak Buruh Menghentikan Pekerjaan Jika Mengancam Jiwa"
            ),
            targetCadetLevel = CadetLevel.KADER_PRATAMA
        ),
        LearningModule(
            id = "mod_kepemimpinan_organisasi",
            title = "Kepemimpinan & Pengorganisasian Basis",
            subtitle = "Manajemen PUK, Kaderisasi, dan Tata Kelola Keuangan COS",
            category = SectorCategory.LEADERSHIP,
            readDurationMinutes = 25,
            iconName = "campaign",
            description = "Membentuk kepemimpinan yang amanah, berani, dan cerdas. Manajemen keanggotaan, iuran Check-Off System (COS), dan komunikasi publik serikat.",
            keyPoints = listOf(
                "Karakteristik Pemimpin Serikat Progresif",
                "Teknik Komunikasi Massa & Rapat Akbar Anggota",
                "Sistem Iuran Otomatis (COS) & Akuntabilitas Keuangan",
                "Regenerasi & Pelatihan Kader Berjenjang"
            ),
            targetCadetLevel = CadetLevel.KADER_UTAMA
        )
    )

    val chapters: List<Chapter> = listOf(
        // CHAPTERS FOR MODUL 1
        Chapter(
            id = "chap_dasar_1",
            moduleId = "mod_dasar_organisasi",
            chapterIndex = 1,
            title = "Hakikat, Sejarah & Jati Diri SP KEP SPSI",
            subtitle = "Perjalanan gerakan buruh sektor Kimia, Energi dan Pertambangan",
            summary = "Serikat Pekerja bukan sekadar wadah berkumpul, melainkan alat perjuangan terorganisir untuk membela, melindungi, dan meningkatkan kesejahteraan pekerja beserta keluarganya.",
            sections = listOf(
                ChapterSection(
                    heading = "1. Pengertian dan Tujuan Pokok Serikat Pekerja",
                    content = "Berdasarkan Pasal 1 angka 17 UU No. 13 Tahun 2003 jo. UU No. 21 Tahun 2000, Serikat Pekerja/Serikat Buruh adalah organisasi yang dibentuk dari, oleh, dan untuk pekerja/buruh baik di perusahaan maupun di luar perusahaan, yang bersifat bebas, terbuka, mandiri, demokratis, dan bertanggung jawab guna memperjuangkan, membela serta meningkatkan kesejahteraan pekerja/buruh beserta keluarganya.",
                    quoteOrHighlight = "Serikat pekerja hadir untuk menyeimbangkan relasi kuasa antara buruh dan pemilik modal agar tercipta keadilan sosial.",
                    bulletPoints = listOf(
                        "Bebas: Menentukan arah perjuangan tanpa intervensi pihak luar.",
                        "Terbuka: Menerima anggota tanpa diskriminasi suku, agama, ras, maupun gender.",
                        "Mandiri: Membiayai operasional melalui iuran anggota sendiri.",
                        "Demokratis: Keputusan tertinggi berada di tangan musyawarah anggota."
                    )
                ),
                ChapterSection(
                    heading = "2. Kekhasan Sektor Kimia, Energi, dan Pertambangan",
                    content = "Pekerja di sektor KEP berhadapan langsung dengan industri padat modal (capital intensive) berisiko tinggi. Kilang minyak, pabrik petrokimia, pembangkit listrik, dan area pertambangan menghasilkan nilai tambah devisa yang sangat besar bagi negara. Oleh karena itu, pekerja berhak atas kompensasi upah layak dan jaminan keselamatan kerja yang paling ketat.",
                    quoteOrHighlight = "Industri KEP berisiko tinggi menuntut serikat pekerja yang berpengetahuan tinggi dan berdisiplin baja."
                ),
                ChapterSection(
                    heading = "3. Doktrin 6 Penguatan SP KEP SPSI",
                    content = "Untuk menghadapi dinamika revolusi industri dan regulasi ketenagakerjaan, SP KEP SPSI memegang teguh 6 Penguatan Organisasi:",
                    bulletPoints = listOf(
                        "1. Penguatan Sumber Daya Manusia (Pendidikan & Pelatihan Terus-menerus)",
                        "2. Penguatan Organisasi & Keanggotaan (Solidaritas Basis)",
                        "3. Penguatan Keuangan & Iuran COS (Kemandirian Finansial)",
                        "4. Penguatan Advokasi & Pembelaan Hukum",
                        "5. Penguatan Hubungan Industrial & PKB Berkualitas",
                        "6. Penguatan Opini Publik & Teknologi Informasi"
                    )
                )
            ),
            pasalReferences = listOf("UU No. 21 Tahun 2000 Pasal 1 & 4", "Konvensi ILO No. 87 & 98", "UUD 1945 Pasal 28E Ayat (3)"),
            keyTakeaways = listOf(
                "Serikat adalah subjek hukum mandiri yang dijamin konstitusi.",
                "Sektor KEP memerlukan advokasi berbasis data dan penguasaan teknis industri.",
                "Kemandirian keuangan melalui COS adalah kunci kedaulatan serikat."
            ),
            caseStudy = CaseStudy(
                title = "Pembentukan PUK di Pabrik Kimia Multinasional",
                background = "Sebuah pabrik bahan kimia di Cilegon dengan 400 pekerja belum memiliki serikat pekerja. Pekerja sering mengalami pemotongan upah sepihak dan ketiadaan APD berstandar.",
                problem = "Manajemen menolak pendaftaran serikat dengan alasan 'perusahaan menganut sistem kekeluargaan'.",
                unionSolution = "Kader membentuk panitia inisiator, mengumpulkan minimal 10 pekerja, mencatatkan kepengurusan ke Disnaker sesuai UU 21/2000 Pasal 14, dan menyerahkan nomor bukti pencatatan resmi kepada manajemen.",
                lessonLearned = "Pemberitahuan pencatatan serikat adalah deklarasi hak konstitusional yang tidak memerlukan 'izin' manajemen perusahaan."
            )
        ),
        Chapter(
            id = "chap_dasar_2",
            moduleId = "mod_dasar_organisasi",
            chapterIndex = 2,
            title = "Perlindungan Hak Berserikat & Anti-Union Busting",
            subtitle = "Memahami sanksi pidana bagi penghalang hak berserikat (Pasal 28 & 43 UU 21/2000)",
            summary = "Siapapun dilarang menghalang-halangi atau memaksa pekerja untuk membentuk atau tidak membentuk serikat pekerja. Pelanggaran atas hak ini diancam pidana penjara hingga 5 tahun.",
            sections = listOf(
                ChapterSection(
                    heading = "1. Bentuk-Bentuk Union Busting (Pemberangusan Serikat)",
                    content = "Union busting adalah praktik intimidasi baik halus maupun terang-terangan yang dilakukan pihak manajemen untuk melemahkan, membubarkan, atau mematikan serikat pekerja.",
                    bulletPoints = listOf(
                        "Melakukan PHK, skorsing, atau mutasi demotif terhadap pengurus/aktivis serikat.",
                        "Menolak membayar upah pekerja yang sedang menjalankan dispensasi kegiatan serikat yang sah.",
                        "Melakukan intimidasi verbal, surat peringatan (SP) rekayasa, atau tawaran uang agar keluar dari serikat.",
                        "Membentuk serikat boneka (yellow union) untuk memecah belah suara anggota."
                    )
                ),
                ChapterSection(
                    heading = "2. Sanksi Pidana Pasal 43 UU No. 21 Tahun 2000",
                    content = "Barang siapa yang menghalang-halangi hak berserikat sebagaimana dimaksud dalam Pasal 28 dikenakan sanksi pidana penjara paling singkat 1 (satu) tahun dan paling lama 5 (lima) tahun dan/atau denda paling sedikit Rp 100.000.000,- dan paling banyak Rp 500.000.000,-.",
                    quoteOrHighlight = "Tindak pidana pemberangusan serikat pekerja (Union Busting) adalah tindak pidana kejahatan murni, bukan delik aduan semata."
                ),
                ChapterSection(
                    heading = "3. Langkah Dokumentasi dan Pelaporan Hukum",
                    content = "Jika terjadi tindakan union busting di unit kerja:",
                    bulletPoints = listOf(
                        "1. Simpan dan amankan bukti tertulis (SK Mutasi mendadak, surat peringatan tanpa alasan sah, rekaman percakapan intimidasi).",
                        "2. Buat kronologi tertulis resmi yang ditandatangani Ketua & Sekretaris PUK.",
                        "3. Koordinasikan dengan Pimpinan Cabang (PC) dan Pimpinan Daerah (PD) SP KEP SPSI.",
                        "4. Buat laporan resmi ke Pengawas Ketenagakerjaan Disnaker dan Kepolisian RI (Polres/Polda)."
                    )
                )
            ),
            pasalReferences = listOf("UU No. 21 Tahun 2000 Pasal 28 & 43", "UU No. 13 Tahun 2003 Pasal 153 Ayat (1) Huruf g"),
            keyTakeaways = listOf(
                "Mutasi atau PHK karena aktif di serikat adalah batal demi hukum.",
                "Pemberangusan serikat memiliki sanksi pidana penjara 1 hingga 5 tahun.",
                "Alat bukti kronologis yang rapi adalah senjata ampuh dalam advokasi."
            )
        ),

        // CHAPTERS FOR MODUL 2
        Chapter(
            id = "chap_hukum_1",
            moduleId = "mod_hukum_advokasi",
            chapterIndex = 1,
            title = "Status Kerja: PKWT, PKWTT, dan Outsourcing",
            subtitle = "Kajian yuridis perlindungan kepastian kerja pekerja tambang, energi dan kimia",
            summary = "Pekerja harus cermat membedakan status Perjanjian Kerja Waktu Tertentu (Kontrak) dan Waktu Tidak Tertentu (Tetap) sesuai batasan PP No. 35 Tahun 2021.",
            sections = listOf(
                ChapterSection(
                    heading = "1. Batasan dan Syarat Sah PKWT",
                    content = "PKWT hanya boleh diadakan untuk pekerjaan tertentu yang menurut jenis dan sifat atau kegiatan pekerjaannya akan selesai dalam waktu tertentu. PKWT tidak dapat diadakan untuk pekerjaan yang bersifat tetap.",
                    bulletPoints = listOf(
                        "Pekerjaan yang sekali selesai atau sementara sifatnya (maksimal 5 tahun termasuk perpanjangan).",
                        "Pekerjaan musiman atau pekerjaan yang berhubungan dengan produk baru.",
                        "Kompensasi PKWT: Pengusaha wajib memberikan uang kompensasi kepada pekerja PKWT yang masa kerjanya minimal 1 bulan saat masa kontrak berakhir."
                    ),
                    quoteOrHighlight = "Uang kompensasi PKWT wajib dibayarkan pengusaha terlepas dari apakah kontrak diperpanjang atau tidak."
                ),
                ChapterSection(
                    heading = "2. Konversi PKWT Menjadi PKWTT (Pekerja Tetap)",
                    content = "Jika PKWT mempekerjakan buruh pada jenis pekerjaan inti produksi yang bersifat terus menerus dan tidak memenuhi syarat jangka waktu, maka demi hukum status pekerja berubah menjadi PKWTT (Pekerja Tetap) sejak adanya hubungan kerja.",
                    quoteOrHighlight = "Pelanggaran administratif dan substansi PKWT mengakibatkan perubahan status otomatis menjadi PKWTT."
                )
            ),
            pasalReferences = listOf("UU No. 13 Tahun 2003 Pasal 56-66", "PP No. 35 Tahun 2021 Pasal 4-17", "Putusan Mahkamah Konstitusi No. 168/PUU-XXI/2023"),
            keyTakeaways = listOf(
                "PKWT maksimal 5 tahun total termasuk perpanjangan.",
                "Pekerja PKWT berhak atas uang kompensasi saat kontrak selesai.",
                "Pekerjaan inti di pabrik/tambang harus diisi pekerja tetap PKWTT."
            )
        ),
        Chapter(
            id = "chap_hukum_2",
            moduleId = "mod_hukum_advokasi",
            chapterIndex = 2,
            title = "Mekanisme Bipartit & Penyelesaian Perselisihan (UU 2/2004)",
            subtitle = "Langkah taktis perundingan internal sebelum melangkah ke Tripartit dan PHI",
            summary = "Perundingan Bipartit adalah pintu pertama penyelesaian setiap sengketa ketenagakerjaan. Wajib diselesaikan secara musyawarah mufakat dalam waktu maksimal 30 hari kerja.",
            sections = listOf(
                ChapterSection(
                    heading = "1. Empat Jenis Perselisihan Hubungan Industrial",
                    content = "Berdasarkan UU No. 2 Tahun 2004, terdapat 4 jenis perselisihan:",
                    bulletPoints = listOf(
                        "1. Perselisihan Hak: Tidak dipenuhinya hak normatif UU/PKB (Contoh: Lembur tidak dibayar, K3 diabaikan).",
                        "2. Perselisihan Kepentingan: Ketidaksesuaian pendapat mengenai pembuatan/perubahan syarat kerja (Contoh: Kenaikan upah tahunan).",
                        "3. Perselisihan PHK: Ketidaksepakatan pengakhiran hubungan kerja atau besaran pesangon.",
                        "4. Perselisihan Antar Serikat Pekerja: Perselisihan kepengurusan/keterwakilan dalam satu perusahaan."
                    )
                ),
                ChapterSection(
                    heading = "2. Prosedur Bipartit yang Sah dan Tervalidasi",
                    content = "Setiap perundingan Bipartit harus dibuatkan Risalah Perundingan yang ditandatangani kedua belah pihak. Risalah harus memuat: nama para pihak, tanggal & tempat, pokok masalah, pendirian masing-masing pihak, dan kesimpulan/kesepakatan.",
                    quoteOrHighlight = "Jika perundingan mencapai mufakat, buat Perjanjian Bersama (PB) dan daftarkan ke Pengadilan Hubungan Industrial agar berkekuatan eksekutorial."
                ),
                ChapterSection(
                    heading = "3. Melangkah ke Tripartit Jika Bipartit Gagal (Deadlock)",
                    content = "Apabila dalam tempo 30 hari kerja perundingan bipartit gagal atau salah satu pihak menolak berunding, serikat pekerja membuat surat pencatatan perselisihan ke Dinas Tenaga Kerja setempat dengan melampirkan bukti risalah bipartit."
                )
            ),
            pasalReferences = listOf("UU No. 2 Tahun 2004 Pasal 3, 6, 7 & 13", "Permenakertrans No. PER.31/MEN/XII/2008"),
            keyTakeaways = listOf(
                "Bipartit maksimal berlangsung 30 hari kerja sejak perundingan pertama.",
                "Risalah bipartit dan daftar hadir adalah syarat mutlak pendaftaran ke Disnaker.",
                "Perjanjian Bersama (PB) yang didaftarkan ke PHI memiliki kekuatan hukum pasti."
            ),
            caseStudy = CaseStudy(
                title = "Sengketa Pemotongan Bonus Produksi Sektor Pertambangan",
                background = "Manajemen memotong bonus tahunan pekerja tambang batubara sebesar 40% secara sepihak dengan alasan harga komoditas turun, padahal tercantum dalam PKB.",
                problem = "Pekerja merasa hak yang telah disepakati dalam PKB dilanggar oleh perusahaan.",
                unionSolution = "PUK melayangkan surat undangan Bipartit resmi. Dalam forum Bipartit, PUK membawa laporan audit keuangan publik perusahaan yang menunjukkan laba bersih tetap positif. Karena manajemen berkukuh, PUK mendaftarkan Perselisihan Hak ke Disnaker dan memenangkan Anjuran Mediator.",
                lessonLearned = "Klausul PKB adalah undang-undang bagi kedua pihak. Membawa data keuangan yang solid memaksa manajemen mematuhi perjanjian."
            )
        ),

        // CHAPTERS FOR MODUL 3
        Chapter(
            id = "chap_pkb_1",
            moduleId = "mod_pkb_negosiasi",
            chapterIndex = 1,
            title = "Prinsip Dasar & Penyusunan Draf PKB Berkualitas",
            subtitle = "Mahakarya serikat pekerja untuk menjamin kesejahteraan jangka panjang",
            summary = "Perjanjian Kerja Bersama (PKB) adalah instrumen tertinggi perlindungan pekerja di perusahaan. PKB yang baik harus memuat syarat kerja di atas standar normatif perundang-undangan.",
            sections = listOf(
                ChapterSection(
                    heading = "1. Kedudukan Hierarki Hukum Ketenagakerjaan",
                    content = "Hierarki ketentuan kerja di perusahaan berlaku asas favorability (kondisi yang paling menguntungkan buruh yang dipakai):",
                    bulletPoints = listOf(
                        "1. Konstitusi & Undang-Undang (Batas Minimum Normatif)",
                        "2. Peraturan Pemerintah (PP)",
                        "3. Perjanjian Kerja Bersama (PKB) — Wajib SAMA atau LEBIH BAIK dari UU",
                        "4. Peraturan Perusahaan (PP Perusahaan) — Gugur jika sudah ada PKB",
                        "5. Perjanjian Kerja Individu"
                    ),
                    quoteOrHighlight = "Jika ada pasal PKB yang nilainya lebih rendah dari undang-undang, maka pasal tersebut batal demi hukum dan yang berlaku adalah undang-undang."
                ),
                ChapterSection(
                    heading = "2. Langkah Persiapan Draf PKB oleh PUK",
                    content = "Sebelum memasuki meja perundingan, Tim Perunding PUK wajib melakukan:",
                    bulletPoints = listOf(
                        "Kuesioner Aspirasi Anggota: Menampung kebutuhan riil buruh di lini pabrik/site tambang.",
                        "Benchmarking Industri Sejenis: Membandingkan benefit di perusahaan kompetitor sektor KEP.",
                        "Kalkulasi Dampak Finansial: Menghitung secara teliti biaya kenaikan tunjangan bagi perusahaan.",
                        "Penyusunan Redaksi Hukum: Menghindari kata multitafsir seperti 'dapat diberikan' atau 'sesuai kemampuan'."
                    )
                )
            ),
            pasalReferences = listOf("UU No. 13 Tahun 2003 Pasal 116-135", "Permenaker No. 28 Tahun 2014"),
            keyTakeaways = listOf(
                "Masa berlaku PKB adalah paling lama 2 tahun dan dapat diperpanjang 1 tahun.",
                "Hanya serikat pekerja yang beranggotakan lebih dari 50% atau mendapat dukungan mayoritas yang berhak mewakili perundingan.",
                "Redaksi klausul PKB harus tegas, jelas hak dan kewajibannya, serta bebas kata rancu."
            )
        ),
        Chapter(
            id = "chap_pkb_2",
            moduleId = "mod_pkb_negosiasi",
            chapterIndex = 2,
            title = "Taktik Negosiasi & Menghadapi Kebuntuan (Deadlock)",
            subtitle = "Keahlian komunikasi persuasif, manajemen tekanan, dan eskalasi terukur",
            summary = "Negosiasi bukan sekadar debat kusir, melainkan seni mencapai kesepakatan terbaik tanpa mengorbankan martabat dan hak dasar anggota serikat.",
            sections = listOf(
                ChapterSection(
                    heading = "1. Taktik Meja Perundingan (Negotiation Skills)",
                    content = "Tim perunding serikat harus memiliki pembagian peran yang rapi:",
                    bulletPoints = listOf(
                        "Ketua Negosiator (Lead Negotiator): Pengendali arah pembicaraan dan pembawa argumentasi utama.",
                        "Pencatat & Risalah (Record Keeper): Mencatat secara cermat setiap konsesi dan kata-kata pihak manajemen.",
                        "Pakar Teknis/Finansial: Mematahkan klaim data biaya atau laba rugi manajemen.",
                        "Timekeeper & Pengatur Irama: Meminta waktu jeda (Caucus) saat tensi memanas."
                    )
                ),
                ChapterSection(
                    heading = "2. Penggunaan Hak Caucus (Rapat Tertutup Tim Serikat)",
                    content = "Setiap kali manajemen memberikan tawaran baru atau suasana menjadi buntu, Lead Negotiator berhak meminta waktu jeda (Caucus) selama 15-30 menit untuk menyamakan sikap sebelum memberi jawaban resmi.",
                    quoteOrHighlight = "Jangan pernah menyetujui tawaran baru manajemen secara spontan di depan meja tanpa rapat internal tim terlebih dahulu."
                )
            ),
            pasalReferences = listOf("UU No. 13 Tahun 2003 Pasal 123", "UU No. 2 Tahun 2004"),
            keyTakeaways = listOf(
                "Bagi peran tim negosiator secara profesional.",
                "Gunakan teknik Caucus saat menghadapi manuver mengejutkan dari manajemen.",
                "Jaga komunikasi transparan dengan anggota basis agar dukungan tetap solid."
            )
        ),

        // CHAPTERS FOR MODUL 4
        Chapter(
            id = "chap_k3_1",
            moduleId = "mod_k3_kep",
            chapterIndex = 1,
            title = "Keselamatan B3 & MSDS di Industri Kimia & Energi",
            subtitle = "Mengendalikan risiko paparan zat berbahaya, ledakan, dan kebocoran gas beracun",
            summary = "Pekerja sektor kimia dan energi terpapar risiko fatalitas tinggi akibat bahan mudah terbakar, meledak, korosif, dan karsinogenik. Penerapan K3 adalah hak hidup asasi pekerja.",
            sections = listOf(
                ChapterSection(
                    heading = "1. Klasifikasi B3 & Lembar Data Keselamatan (MSDS/LDKB)",
                    content = "Setiap bahan kimia yang masuk ke tempat kerja wajib dilengkapi dengan Material Safety Data Sheet (MSDS) dalam Bahasa Indonesia yang mudah diakses oleh seluruh pekerja di area kerja.",
                    bulletPoints = listOf(
                        "Identifikasi Bahaya & Piktogram Simbol Bahaya GHS.",
                        "Tata Cara Pertolongan Pertama (First Aid Measures).",
                        "Penanganan Kebakaran dan Tumpahan Kimia Darurat.",
                        "Alat Pelindung Diri (APD) Khusus Kimia (Respirator Gas, Sarung Tangan Nitrile, Wearpack Tahan Api)."
                    ),
                    quoteOrHighlight = "Buruh berhak menolak menuangkan atau memproses zat kimia jika perusahaan tidak menyediakan MSDS dan APD yang sesuai standar!"
                ),
                ChapterSection(
                    heading = "2. Prosedur LOTO (Lockout / Tagout)",
                    content = "Prosedur isolasi sumber energi berbahaya (listrik, tekanan hidrolik, uap panas, gas kimia) saat perbaikan mesin wajib dilakukan dengan memasang gembok fisik (Lock) dan label peringatan (Tag). Kunci gembok hanya boleh dipegang oleh teknisi yang bersangkutan."
                )
            ),
            pasalReferences = listOf("UU No. 1 Tahun 1970 Pasal 3, 9 & 12", "Permenaker No. 187/MEN/1999 tentang Pengendalian B3", "PP No. 50 Tahun 2012 (SMK3)"),
            keyTakeaways = listOf(
                "MSDS wajib tersedia di setiap workstation dan terbaca oleh operator.",
                "LOTO adalah hukum mati dalam pemeliharaan instalasi berenergi.",
                "Serikat pekerja melalui P2K3 berwenang menginspeksi kelaikan APD."
            )
        ),
        Chapter(
            id = "chap_k3_2",
            moduleId = "mod_k3_kep",
            chapterIndex = 2,
            title = "K3 Sektor Pertambangan & Hak Menolak Bekerja Tidak Aman",
            subtitle = "Regulasi keselamatan tambang (Kepmen ESDM 1827/2018) dan perlindungan jiwa",
            summary = "Dalam industri tambang terbuka maupun bawah tanah, keselamatan berada di atas target produksi. Pekerja dilindungi undang-undang untuk menghentikan pekerjaan jika terjadi bahaya mengancam.",
            sections = listOf(
                ChapterSection(
                    heading = "1. Hak Menolak Bekerja yang Membahayakan Jiwa (Stop Work Authority)",
                    content = "Berdasarkan Pasal 12 huruf d UU No. 1 Tahun 1970 dan regulasi keselamatan pertambangan, pekerja berhak menyatakan keberatan kerja pada pekerjaan di mana syarat keselamatan dan kesehatan kerja serta alat-alat perlindungan diri yang diwajibkan diragukan olehnya.",
                    quoteOrHighlight = "Tidak ada satu pun tonase batubara, liter minyak, atau senyawa kimia yang sebanding dengan nyawa seorang pekerja!"
                ),
                ChapterSection(
                    heading = "2. Investigasi Kecelakaan Kerja oleh Serikat",
                    content = "Jika terjadi insiden fatalitas atau cidera berat di tempat kerja, PUK SP KEP SPSI membentuk tim investigasi independen untuk memastikan akar masalah (root cause) bukan ditimpakan semata sebagai kelalaian buruh (human error), melainkan kelemahan sistem manajemen K3 perusahaan."
                )
            ),
            pasalReferences = listOf("UU No. 1 Tahun 1970 Pasal 12", "Kepmen ESDM No. 1827 K/30/MEM/2018", "PP No. 50 Tahun 2012"),
            keyTakeaways = listOf(
                "Setiap pekerja tambang memegang kewenangan Stop Work Authority.",
                "Perusahaan dilarang menghukum pekerja yang menolak bekerja karena alasan keselamatan yang nyata.",
                "Investigasi serikat fokus pada perbaikan sistem dan perlindungan korban."
            )
        ),

        // CHAPTERS FOR MODUL 5
        Chapter(
            id = "chap_lead_1",
            moduleId = "mod_kepemimpinan_organisasi",
            chapterIndex = 1,
            title = "Manajemen PUK & Pengelolaan Iuran Anggota (COS)",
            subtitle = "Transparansi keuangan dan akuntabilitas organisasi sebagai fondasi kedaulatan",
            summary = "Organisasi serikat yang kuat dibangun di atas kemandirian finansial anggota melalui Check-Off System (COS) dan tata kelola administrasi yang profesional.",
            sections = listOf(
                ChapterSection(
                    heading = "1. Sistem Pemotongan Iuran Otomatis (Check-Off System / COS)",
                    content = "Berdasarkan AD/ART SP KEP SPSI dan regulasi ketenagakerjaan, iuran serikat dipotong langsung dari gaji pekerja oleh perusahaan atas dasar surat kuasa tertulis dari masing-masing anggota serikat dan disetorkan ke rekening resmi organisasi.",
                    bulletPoints = listOf(
                        "Iuran Anggota adalah darah organisasi untuk membiayai advokasi, pendidikan kader, dan solidaritas pemogokan.",
                        "Distribusi iuran dibagi secara proporsional sesuai AD/ART: PUK, PC, PD, dan DPP.",
                        "Laporan keuangan PUK wajib diaudit dan dilaporkan secara berkala kepada seluruh anggota dalam rapat anggota tahunan."
                    )
                ),
                ChapterSection(
                    heading = "2. Regenerasi dan Pelatihan Kader Muda (Millennial & Gen-Z Workers)",
                    content = "PUK wajib menyelenggarakan kaderisasi berjenjang agar estafet kepemimpinan serikat di pabrik/site terus berlanjut menghadapi digitalisasi dan otomatisasi industri."
                )
            ),
            pasalReferences = listOf("UU No. 21 Tahun 2000 Pasal 30", "AD/ART Federasi SP KEP SPSI"),
            keyTakeaways = listOf(
                "Kemandirian keuangan serikat mencegah intervensi pemodal.",
                "Transparansi laporan keuangan menjaga kepercayaan penuh anggota.",
                "Kaderisasi berkesinambungan memastikan serikat tetap awet dan disegani."
            )
        )
    )

    val quizzes: Map<String, List<QuizQuestion>> = mapOf(
        "mod_dasar_organisasi" to listOf(
            QuizQuestion(
                id = "q_dasar_1",
                moduleId = "mod_dasar_organisasi",
                question = "Undang-Undang Republik Indonesia yang secara khusus mengatur tentang Serikat Pekerja / Serikat Buruh adalah...",
                options = listOf(
                    "UU No. 21 Tahun 2000",
                    "UU No. 13 Tahun 2003",
                    "UU No. 2 Tahun 2004",
                    "UU No. 1 Tahun 1970"
                ),
                correctIndex = 0,
                explanation = "UU No. 21 Tahun 2000 adalah undang-undang khusus (lex specialis) yang mengatur hak berserikat, pembentukan, pendaftaran, dan perlindungan serikat pekerja di Indonesia.",
                legalReference = "UU No. 21 Tahun 2000"
            ),
            QuizQuestion(
                id = "q_dasar_2",
                moduleId = "mod_dasar_organisasi",
                question = "Berapakah ancaman hukuman pidana penjara bagi pihak yang terbukti melakukan tindak pidana pemberangusan serikat (Union Busting) sesuai Pasal 43 UU No. 21 Tahun 2000?",
                options = listOf(
                    "Paling lama 6 bulan",
                    "Paling singkat 1 tahun dan paling lama 5 tahun",
                    "Paling lama 2 tahun",
                    "Hanya sanksi teguran tertulis"
                ),
                correctIndex = 1,
                explanation = "Pasal 43 UU No. 21/2000 menegaskan sanksi pidana penjara paling singkat 1 tahun dan paling lama 5 tahun dan/atau denda Rp 100 juta s.d Rp 500 juta bagi pelaku union busting.",
                legalReference = "Pasal 43 UU No. 21 Tahun 2000"
            ),
            QuizQuestion(
                id = "q_dasar_3",
                moduleId = "mod_dasar_organisasi",
                question = "Berapa jumlah pekerja minimal yang disyaratkan oleh undang-undang untuk dapat membentuk 1 (satu) unit serikat pekerja di perusahaan?",
                options = listOf(
                    "Minimal 5 orang pekerja",
                    "Minimal 10 orang pekerja",
                    "Minimal 20 orang pekerja",
                    "Minimal 50% dari total karyawan"
                ),
                correctIndex = 1,
                explanation = "Sesuai Pasal 5 ayat (2) UU No. 21 Tahun 2000, serikat pekerja dapat dibentuk oleh sekurang-kurangnya 10 (sepuluh) orang pekerja/buruh.",
                legalReference = "Pasal 5 Ayat (2) UU 21/2000"
            ),
            QuizQuestion(
                id = "q_dasar_4",
                moduleId = "mod_dasar_organisasi",
                question = "Manakah yang BUKAN merupakan bagian dari Doktrin 6 Penguatan SP KEP SPSI?",
                options = listOf(
                    "Penguatan Keuangan & Iuran COS",
                    "Penguatan Kepasrahan Terhadap Kebijakan Pemilik Modal",
                    "Penguatan Sumber Daya Manusia & Pendidikan",
                    "Penguatan Advokasi & Pembelaan Hukum"
                ),
                correctIndex = 1,
                explanation = "Doktrin 6 Penguatan SP KEP SPSI berfokus pada SDM, Organisasi, Keuangan, Advokasi, Hubungan Industrial/PKB, dan Opini Publik/TI demi kemandirian dan martabat buruh.",
                legalReference = "Doktrin 6 Penguatan SP KEP SPSI"
            )
        ),
        "mod_hukum_advokasi" to listOf(
            QuizQuestion(
                id = "q_hukum_1",
                moduleId = "mod_hukum_advokasi",
                question = "Berapa lama batas waktu maksimal penyelesaian perundingan Bipartit menurut UU No. 2 Tahun 2004?",
                options = listOf(
                    "14 hari kalender",
                    "30 hari kerja sejak perundingan pertama",
                    "60 hari kerja",
                    "Tidak terbatas sampai tercapai kesepakatan"
                ),
                correctIndex = 1,
                explanation = "Pasal 3 ayat (2) UU No. 2 Tahun 2004 menyatakan bahwa perundingan bipartit harus diselesaikan paling lama 30 (tiga puluh) hari kerja sejak tanggal dimulainya perundingan.",
                legalReference = "UU No. 2 Tahun 2004 Pasal 3 Ayat (2)"
            ),
            QuizQuestion(
                id = "q_hukum_2",
                moduleId = "mod_hukum_advokasi",
                question = "Berdasarkan PP No. 35 Tahun 2021, pekerja dengan status PKWT (Kontrak) yang masa kerjanya telah berlangsung minimal 1 bulan berhak mendapatkan...",
                options = listOf(
                    "Uang Pesangon 2 kali lipat",
                    "Uang Kompensasi PKWT saat berakhirnya masa kontrak",
                    "Saham kepemilikan perusahaan",
                    "Jaminan pensiun hari tua sekaligus"
                ),
                correctIndex = 1,
                explanation = "Pasal 15 PP No. 35 Tahun 2021 mewajibkan pengusaha memberikan uang kompensasi kepada pekerja PKWT yang mempunyai masa kerja minimal 1 bulan terus menerus saat masa kontrak berakhir.",
                legalReference = "PP No. 35 Tahun 2021 Pasal 15"
            ),
            QuizQuestion(
                id = "q_hukum_3",
                moduleId = "mod_hukum_advokasi",
                question = "Dokumen apa yang wajib dihasilkan dan ditandatangani jika perundingan Bipartit mencapai kesepakatan damai?",
                options = listOf(
                    "Surat Peringatan Ketiga (SP3)",
                    "Perjanjian Bersama (PB) yang didaftarkan ke PHI",
                    "Akta Notaris Jual Beli",
                    "Surat Keputusan Direksi sepihak"
                ),
                correctIndex = 1,
                explanation = "Kesepakatan damai bipartit dituangkan dalam Perjanjian Bersama (PB) yang ditandatangani kedua belah pihak dan didaftarkan pada Pengadilan Hubungan Industrial.",
                legalReference = "UU No. 2 Tahun 2004 Pasal 7"
            ),
            QuizQuestion(
                id = "q_hukum_4",
                moduleId = "mod_hukum_advokasi",
                question = "Dalam penyelesaian perselisihan di Pengadilan Hubungan Industrial (PHI), putusan atas 'Perselisihan Hak' dan 'Perselisihan PHK' dapat diajukan upaya hukum lanjutan berupa...",
                options = listOf(
                    "Banding ke Pengadilan Tinggi",
                    "Kasasi langsung ke Mahkamah Agung (MA)",
                    "Gugatan ulang ke Disnaker",
                    "Musyawarah keluarga direksi"
                ),
                correctIndex = 1,
                explanation = "Putusan PHI mengenai Perselisihan Hak dan Perselisihan PHK tidak ada upaya banding, melainkan langsung dapat dimohonkan Kasasi ke Mahkamah Agung dalam tenggang waktu 14 hari kerja.",
                legalReference = "UU No. 2 Tahun 2004 Pasal 115"
            )
        ),
        "mod_pkb_negosiasi" to listOf(
            QuizQuestion(
                id = "q_pkb_1",
                moduleId = "mod_pkb_negosiasi",
                question = "Berapa lama jangka waktu masa berlaku Perjanjian Kerja Bersama (PKB) menurut undang-undang ketenagakerjaan?",
                options = listOf(
                    "Paling lama 1 tahun tanpa perpanjangan",
                    "Paling lama 2 tahun dan dapat diperpanjang maksimal 1 tahun",
                    "Paling lama 5 tahun",
                    "Berlaku selamanya sampai perusahaan tutup"
                ),
                correctIndex = 1,
                explanation = "Pasal 123 UU No. 13 Tahun 2003 mengatur masa berlaku PKB paling lama 2 tahun dan dapat diperpanjang paling lama 1 tahun berdasarkan kesepakatan tertulis kedua pihak.",
                legalReference = "UU No. 13 Tahun 2003 Pasal 123"
            ),
            QuizQuestion(
                id = "q_pkb_2",
                moduleId = "mod_pkb_negosiasi",
                question = "Apabila dalam suatu pasal PKB tercantum syarat kerja yang nilainya lebih rendah daripada undang-undang, maka hukumnya adalah...",
                options = listOf(
                    "Tetap berlaku sah karena sudah disepakati bersama",
                    "Batal demi hukum dan yang berlaku adalah ketentuan undang-undang",
                    "Perusahaan didenda Rp 1 miliar",
                    "Seluruh isi PKB dari awal sampai akhir langsung hangus"
                ),
                correctIndex = 1,
                explanation = "Pasal 124 ayat (2) UU No. 13/2003 menegaskan ketentuan dalam PKB tidak boleh bertentangan dengan peraturan perundang-undangan. Ketentuan yang bertentangan batal demi hukum dan digantikan oleh undang-undang.",
                legalReference = "UU No. 13 Tahun 2003 Pasal 124 Ayat (2)"
            ),
            QuizQuestion(
                id = "q_pkb_3",
                moduleId = "mod_pkb_negosiasi",
                question = "Apa fungsi utama teknik 'Caucus' dalam perundingan PKB?",
                options = listOf(
                    "Menyerang manajemen secara fisik",
                    "Meminta jeda waktu rapat tertutup internal tim serikat untuk menyelaraskan sikap",
                    "Membatalkan seluruh perundingan secara sepihak",
                    "Menandatangani draft tanpa dibaca"
                ),
                correctIndex = 1,
                explanation = "Caucus adalah pertemuan tertutup singkat antar anggota delegasi tim serikat di tengah perundingan untuk mengevaluasi manuver manajemen sebelum memberi keputusan.",
                legalReference = "Tata Tertib Perundingan PKB"
            )
        ),
        "mod_k3_kep" to listOf(
            QuizQuestion(
                id = "q_k3_1",
                moduleId = "mod_k3_kep",
                question = "Undang-undang payung hukum utama yang mengatur Keselamatan Kerja di seluruh tempat kerja di Indonesia adalah...",
                options = listOf(
                    "UU No. 1 Tahun 1970",
                    "UU No. 21 Tahun 2000",
                    "UU No. 24 Tahun 2011",
                    "UU No. 40 Tahun 2004"
                ),
                correctIndex = 0,
                explanation = "UU No. 1 Tahun 1970 tentang Keselamatan Kerja adalah undang-undang pokok K3 di Indonesia yang mewajibkan syarat keselamatan kerja di darat, dalam tanah, permukaan air, maupun udara.",
                legalReference = "UU No. 1 Tahun 1970"
            ),
            QuizQuestion(
                id = "q_k3_2",
                moduleId = "mod_k3_kep",
                question = "Dokumen apakah yang wajib disediakan di tempat kerja kimia yang memuat informasi bahaya, sifat fisik, toksisitas, dan pertolongan pertama bahan kimia?",
                options = listOf(
                    "Kuitansi Pembelian Zat Kimia",
                    "Material Safety Data Sheet (MSDS / Lembar Data Keselamatan Bahan)",
                    "Kartu Tanda Anggota (KTA)",
                    "Surat Izin Mengemudi Alat Berat"
                ),
                correctIndex = 1,
                explanation = "MSDS (LDKB) adalah lembar petunjuk keselamatan bahan kimia yang wajib disediakan perusahaan kimia agar pekerja memahami bahaya dan penanganan darurat.",
                legalReference = "Permenaker No. 187/MEN/1999"
            ),
            QuizQuestion(
                id = "q_k3_3",
                moduleId = "mod_k3_kep",
                question = "Apa makna dari hak 'Stop Work Authority' (SWA) bagi pekerja tambang dan kilang energi?",
                options = listOf(
                    "Hak pulang cepat setiap hari Jumat",
                    "Hak dan kewajiban menghentikan pekerjaan jika melihat kondisi tidak aman yang mengancam keselamatan jiwa",
                    "Hak mogok tanpa pemberitahuan resmi",
                    "Hak menolak perintah atasan untuk memakai APD"
                ),
                correctIndex = 1,
                explanation = "Stop Work Authority memberi wewenang penuh kepada setiap pekerja untuk menghentikan operasi kerja seketika jika terdapat bahaya keselamatan yang nyata tanpa takut sanksi disipliner.",
                legalReference = "UU No. 1/1970 & Kepmen ESDM 1827/2018"
            )
        ),
        "mod_kepemimpinan_organisasi" to listOf(
            QuizQuestion(
                id = "q_lead_1",
                moduleId = "mod_kepemimpinan_organisasi",
                question = "Apa kepanjangan dan tujuan dari sistem COS dalam tata kelola serikat pekerja?",
                options = listOf(
                    "Chief Officer Security untuk pengamanan pabrik",
                    "Check-Off System, yaitu sistem pemotongan iuran anggota secara otomatis melalui payroll untuk kemandirian serikat",
                    "Collective Operation Strike untuk persiapan mogok kerja",
                    "Coaching Officer Staff untuk pelatihan manajemen"
                ),
                correctIndex = 1,
                explanation = "Check-Off System (COS) adalah mekanisme resmi pemotongan iuran serikat pekerja langsung dari gaji dengan surat kuasa anggota guna menjamin stabilitas keuangan organisasi.",
                legalReference = "AD/ART SP KEP SPSI & UU 21/2000"
            ),
            QuizQuestion(
                id = "q_lead_2",
                moduleId = "mod_kepemimpinan_organisasi",
                question = "Tingkatan struktur kepengurusan SP KEP SPSI di tingkat perusahaan/pabrik disebut...",
                options = listOf(
                    "DPP (Dewan Pimpinan Pusat)",
                    "PD (Pimpinan Daerah)",
                    "PC (Pimpinan Cabang)",
                    "PUK (Pengurus Unit Kerja)"
                ),
                correctIndex = 3,
                explanation = "PUK (Pengurus Unit Kerja) adalah basis terdepan organisasi SP KEP SPSI yang berada langsung di tingkat perusahaan/pabrik/site kerja.",
                legalReference = "AD/ART Federasi SP KEP SPSI"
            )
        )
    )

    val laborLawTerms: List<LaborLawTerm> = listOf(
        LaborLawTerm(
            id = "term_1",
            term = "PKB (Perjanjian Kerja Bersama)",
            category = "Hubungan Industrial",
            shortDefinition = "Perjanjian hasil perundingan antara Serikat Pekerja dan Pengusaha yang memuat syarat kerja, hak, dan kewajiban kedua belah pihak.",
            fullExplanation = "PKB merupakan hukum tertinggi di tingkat perusahaan yang sifatnya mengikat kedua belah pihak. Ketentuan dalam PKB tidak boleh lebih rendah dari peraturan perundang-undangan. Masa berlaku PKB maksimal 2 tahun dan dapat diperpanjang 1 tahun.",
            legalReference = "UU No. 13 Tahun 2003 Pasal 116-135",
            practicalExample = "PKB PT Petrokimia mengatur tunjangan risiko kimia sebesar 20% gaji pokok, melampaui ketentuan standar UU."
        ),
        LaborLawTerm(
            id = "term_2",
            term = "Bipartit",
            category = "Penyelesaian Sengketa",
            shortDefinition = "Perundingan langsung antara pekerja/serikat pekerja dengan pengusaha untuk menyelesaikan perselisihan hubungan industrial.",
            fullExplanation = "Bipartit adalah forum musyawarah wajib sebelum sengketa dapat dibawa ke mediasi Disnaker atau Pengadilan Hubungan Industrial. Waktu maksimal perundingan Bipartit adalah 30 hari kerja.",
            legalReference = "UU No. 2 Tahun 2004 Pasal 3",
            practicalExample = "PUK SP KEP mengadakan perundingan Bipartit terkait keterlambatan pembayaran uang lembur operator kilang."
        ),
        LaborLawTerm(
            id = "term_3",
            term = "Union Busting",
            category = "Hak Berserikat",
            shortDefinition = "Tindakan menghalang-halangi, mengintimidasi, atau memberangus hak pekerja untuk membentuk dan menjalankan kegiatan serikat pekerja.",
            fullExplanation = "Tindakan union busting merupakan tindak pidana kejahatan yang diancam hukuman penjara 1 sampai 5 tahun dan/atau denda Rp 100 juta hingga Rp 500 juta.",
            legalReference = "UU No. 21 Tahun 2000 Pasal 28 & Pasal 43",
            practicalExample = "Manajemen memutasi Ketua PUK ke pulau terpencil tanpa alasan jelas setelah serikat mengajukan perundingan kenaikan upah."
        ),
        LaborLawTerm(
            id = "term_4",
            term = "LOTO (Lockout / Tagout)",
            category = "K3 Sektor KEP",
            shortDefinition = "Prosedur keselamatan fisik untuk mengisolasi sumber energi berbahaya selama pemeliharaan mesin/instalasi.",
            fullExplanation = "LOTO memastikan peralatan tidak dapat dihidupkan secara tidak sengaja saat teknisi sedang berada di dalam ruang mesin, pipa gas bertekanan, atau reaktor kimia.",
            legalReference = "UU No. 1 Tahun 1970 & Standar OSHA",
            practicalExample = "Pemasangan gembok pengaman dan tag bahaya pada panel listrik utama sebelum pembersihan tangki reaktor polimer."
        ),
        LaborLawTerm(
            id = "term_5",
            term = "Uang Kompensasi PKWT",
            category = "Kompensasi Kerja",
            shortDefinition = "Uang penggantian yang wajib dibayarkan pengusaha kepada pekerja kontrak saat masa kerja kontrak berakhir.",
            fullExplanation = "Diberikan kepada pekerja PKWT dengan masa kerja minimal 1 bulan. Jika masa kerja 12 bulan terus menerus, kompensasi adalah 1 bulan upah. Jika kurang/lebih dihitung secara proporsional (Masa Kerja / 12 x 1 Bulan Upah).",
            legalReference = "PP No. 35 Tahun 2021 Pasal 15 & Pasal 16",
            practicalExample = "Pekerja kontrak di site tambang selama 6 bulan dengan upah Rp 6.000.000 berhak atas kompensasi Rp 3.000.000 saat kontrak selesai."
        ),
        LaborLawTerm(
            id = "term_6",
            term = "Mogok Kerja Sah",
            category = "Aksi & Advokasi",
            shortDefinition = "Tindakan pekerja yang direncanakan dan dilaksanakan secara bersama-sama untuk menghentikan pekerjaan sebagai akibat gagalnya perundingan.",
            fullExplanation = "Mogok kerja sah harus didahului perundingan yang gagal (deadlock) dan pemberitahuan tertulis kepada pengusaha serta Disnaker sekurang-kurangnya 7 hari kerja sebelum aksi mogok dimulai.",
            legalReference = "UU No. 13 Tahun 2003 Pasal 137-145",
            practicalExample = "Pekerja pabrik pupuk menggelar mogok kerja damai setelah perundingan PKB menemui jalan buntu selama 60 hari dan telah mengirim surat pemberitahuan 7 hari sebelumnya."
        ),
        LaborLawTerm(
            id = "term_7",
            term = "MSDS / LDKB",
            category = "K3 Sektor KEP",
            shortDefinition = "Material Safety Data Sheet (Lembar Data Keselamatan Bahan) yang memuat rincian sifat kimia, bahaya, dan penanganan darurat.",
            fullExplanation = "Wajib disediakan oleh pengusaha di setiap titik penyimpanan dan penggunaan zat kimia berbahaya, ditulis dalam Bahasa Indonesia yang mudah dipahami buruh.",
            legalReference = "Permenaker No. 187/MEN/1999",
            practicalExample = "MSDS Asam Sulfat ditempelkan di dekat tangki penyimpanan asam sebagai petunjuk tindakan darurat bila terkena kulit."
        ),
        LaborLawTerm(
            id = "term_8",
            term = "Check-Off System (COS)",
            category = "Organisasi",
            shortDefinition = "Mekanisme pemotongan iuran serikat langsung melalui slip gaji atas surat kuasa tertulis dari anggota.",
            fullExplanation = "COS adalah fondasi kemandirian finansial serikat pekerja, memastikan operasional advokasi, bantuan hukum anggota, dan kaderisasi berjalan tanpa bergantung pada donatur luar.",
            legalReference = "UU No. 21 Tahun 2000 Pasal 30",
            practicalExample = "Iuran rutin sebesar 1% dari gaji pokok dipotong otomatis setiap tanggal 25 dan disetorkan ke rekening PUK SP KEP."
        )
    )

    val disputeSteps: List<IndustrialDisputeStep> = listOf(
        IndustrialDisputeStep(
            stepNumber = 1,
            title = "Perundingan Bipartit Internal",
            actor = "PUK SP KEP SPSI vs Manajemen Perusahaan",
            timelineLimit = "Maksimal 30 Hari Kerja",
            legalBasis = "UU No. 2 Tahun 2004 Pasal 3 & 6",
            description = "Perundingan musyawarah langsung antara pengurus serikat pekerja dan pimpinan perusahaan di tingkat unit kerja.",
            checklistDocs = listOf(
                "Surat permohonan/undangan Bipartit ke-1 dan ke-2",
                "Daftar hadir pertemuan Bipartit",
                "Notulensi & Risalah Bipartit lengkap",
                "Perjanjian Bersama (PB) jika sepakat / Risalah Bipartit Deadlock jika gagal"
            ),
            keyTactics = listOf(
                "Fokus pada pasal PKB atau pasal undang-undang yang dilanggar.",
                "Jangan menandatangani risalah kosong atau risalah yang tidak mencerminkan fakta perundingan.",
                "Gunakan hak Caucus saat manajemen mendesak jawaban spontan."
            )
        ),
        IndustrialDisputeStep(
            stepNumber = 2,
            title = "Pencatatan Perselisihan ke Disnaker (Tripartit)",
            actor = "PUK / PC SP KEP SPSI ke Dinas Tenaga Kerja Setempat",
            timelineLimit = "7 Hari Kerja Setelah Pencatatan",
            legalBasis = "UU No. 2 Tahun 2004 Pasal 4 & 8",
            description = "Jika Bipartit gagal, salah satu atau kedua pihak mencatatkan perselisihan secara tertulis ke Disnaker Kabupaten/Kota.",
            checklistDocs = listOf(
                "Surat Permohonan Pencatatan Perselisihan",
                "Bukti Risalah Perundingan Bipartit yang gagal",
                "Kronologis kasus dan bukti-bukti pendukung (Slip gaji, PKB, SK Mutasi/PHK)"
            ),
            keyTactics = listOf(
                "Pastikan tanda terima pencatatan berkas dari Disnaker tersimpan rapi.",
                "Pilih mekanisme Mediasi Ketenagakerjaan oleh Pejabat Fungsional Mediator Disnaker."
            )
        ),
        IndustrialDisputeStep(
            stepNumber = 3,
            title = "Proses Mediasi Tripartit & Penerbitan Anjuran",
            actor = "Mediator Disnaker, Serikat Pekerja, Pengusaha",
            timelineLimit = "Maksimal 30 Hari Kerja",
            legalBasis = "UU No. 2 Tahun 2004 Pasal 13 & 14",
            description = "Mediator memanggil para pihak untuk sidang mediasi klarifikasi dan musyawarah mencari titik temu.",
            checklistDocs = listOf(
                "Surat Panggilan Sidang Mediasi I, II, dan III",
                "Keterangan tertulis pendirian serikat pekerja",
                "Anjuran Tertulis Resmi dari Mediator Disnaker",
                "Surat Jawaban Menyetujui atau Menolak Anjuran"
            ),
            keyTactics = listOf(
                "Hadir tepat waktu dengan delegasi resmi yang memegang surat tugas PUK/PC.",
                "Jika Anjuran menguntungkan buruh, kirim surat menerima Anjuran dalam tempo 10 hari kerja."
            )
        ),
        IndustrialDisputeStep(
            stepNumber = 4,
            title = "Gugatan ke Pengadilan Hubungan Industrial (PHI)",
            actor = "Penggugat (Serikat / Pekerja) vs Tergugat di Pengadilan Negeri",
            timelineLimit = "Maksimal 50 Hari Kerja Sidang PHI",
            legalBasis = "UU No. 2 Tahun 2004 Pasal 55-112",
            description = "Jika salah satu pihak menolak Anjuran Tertulis Mediator, pihak yang berkeberatan mendaftarkan gugatan ke PHI pada Pengadilan Negeri setempat.",
            checklistDocs = listOf(
                "Surat Gugatan PHI disertai Surat Kuasa Khusus",
                "Anjuran Asli Mediator dan Risalah Bipartit",
                "Bundel Alat Bukti Surat P1 s.d P-terakhir",
                "Daftar Saksi Fakta dan Saksi Ahli Ketenagakerjaan"
            ),
            keyTactics = listOf(
                "Advokasi didampingi Lembaga Bantuan Hukum (LBH) SP KEP SPSI.",
                "Gugat ganti rugi upah selama proses perselisihan berjalan (Upah Proses)."
            )
        ),
        IndustrialDisputeStep(
            stepNumber = 5,
            title = "Upaya Hukum Kasasi ke Mahkamah Agung (MA)",
            actor = "Pemohon Kasasi ke Mahkamah Agung RI",
            timelineLimit = "14 Hari Kerja Setelah Putusan PHI Dibacakan",
            legalBasis = "UU No. 2 Tahun 2004 Pasal 115",
            description = "Khusus untuk Perselisihan Hak dan Perselisihan PHK, putusan PHI dapat diajukan kasasi ke Mahkamah Agung.",
            checklistDocs = listOf(
                "Akta Permohonan Kasasi di Kepaniteraan PHI",
                "Memori Kasasi Resmi dalam tempo 14 hari",
                "Salinan Putusan PHI yang dimohonkan kasasi"
            ),
            keyTactics = listOf(
                "Kawal penyerahan memori kasasi tidak melebihi tenggat 14 hari kerja.",
                "Periksa apakah Hakim PHI salah menerapkan hukum pembuktian."
            )
        )
    )
}
