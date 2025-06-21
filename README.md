**Anggota SBD :**
- Shafda Afryanda Ekhanusa Persada (231401001)
- Dame Theresia Rejeki Sidauruk (231401022)
- Dini Arya Putri (231401025)
- Cikita Natasya Br Sembiring (231401121)

**SIKATU - Sistem Katalog Tugas Kuliah**

Informasi Dasar Aplikasi

Nama Aplikasi: SIKATU (Sistem Katalog Tugas Kuliah)

Tema Proyek: Produktivitas

Target Pengguna: Mahasiswa 

**Deskripsi Singkat Tentang SIKATU:**
SIKATU adalah aplikasi berbasis JavaFX yang dirancang khusus untuk membantu mahasiswa dalam mengelola dan mengorganisir tugas-tugas kuliah mereka secara efisien. Aplikasi ini menerapkan konsep manajemen tugas modern dengan antarmuka yang user-friendly, memungkinkan mahasiswa untuk tidak hanya mencatat tugas tetapi juga memantau progress, mengatur prioritas, dan mendapatkan reminder otomatis untuk deadline yang akan datang.
Dengan fitur katalog yang komprehensif, mahasiswa dapat mengkategorikan tugas berdasarkan mata kuliah, status penyelesaian, dan tingkat prioritas. SIKATU juga dilengkapi dengan visualisasi data berupa progress bar dan kalender interaktif yang memberikan gambaran jelas tentang beban kerja dan jadwal akademik.

**Tujuan dan Manfaat Aplikasi**
**Tujuan Utama:**
1. Meningkatkan produktivitas akademik mahasiswa
2. Mengurangi risiko kelupaan terhadap tugas dan deadline
3. Memberikan visualisasi yang jelas tentang progress akademik
4. Membantu mahasiswa dalam perencanaan waktu yang lebih baik

**Manfaat yang Diperoleh:**
Organisasi yang Lebih Baik: Semua tugas tersimpan rapi dalam satu sistem
Manajemen Waktu Efektif: Reminder dan kalender membantu planning yang lebih baik
Monitoring Progress: Visualisasi status tugas memotivasi penyelesaian
Aksesibilitas: Data tersimpan aman di database dan dapat diakses kapan saja

**Fitur Utama Aplikasi**
1. Login
Fitur login berfungsi sebagai gerbang awal agar pengguna dapat mengakses seluruh sistem SIKATU. Pengguna diminta untuk memasukkan email/username dan password yang valid. Setelah berhasil masuk, sistem akan menyimpan sesi pengguna dan mengarahkan langsung ke dashboard utama.
Sistem login juga berfungsi untuk menjaga keamanan data dan mencegah akses yang tidak sah. Validasi form harus lengkap.

2. Dashboard
Dashboard merupakan pusat informasi yang memberikan gambaran cepat mengenai seluruh aktivitas tugas pengguna. Di halaman ini, pengguna dapat melihat statistik penting seperti jumlah total tugas, jumlah tugas yang telah selesai, dan tugas yang mendekati deadline.
Visualisasi progres ditampilkan menggunakan progress bar, yang menunjukkan persentase tugas yang telah diselesaikan dari seluruh tugas yang ada. Warna bar bisa menyesuaikan status (misal: hijau untuk selesai, kuning untuk sedang berlangsung, merah untuk mendekati deadline).
Fitur notifikasi juga muncul di sini, khusus untuk tugas-tugas yang deadline-nya sudah dekat (misalnya H-3 dan H-1). Notifikasi ini bisa berupa popup kecil atau penanda visual seperti warna merah atau kuning pada daftar tugas.
Dari dashboard, tersedia tombol navigasi cepat seperti Tambah Tugas, Buka Kalender, dan Export Tugas, untuk memudahkan pengguna mengakses fitur penting hanya dengan satu klik.

3. Tugas
Fitur ini adalah inti utama dari aplikasi SIKATU. Di dalamnya terdapat daftar semua tugas yang dimiliki pengguna. Setiap tugas ditampilkan dalam bentuk list atau card dengan informasi singkat seperti nama tugas, mata kuliah terkait, tanggal deadline, dan status tugas (misalnya: Belum, Proses, Selesai), dengan badge warna sebagai indikator status.
Pengguna dapat melakukan berbagai aksi terhadap setiap tugas, yaitu:
Edit tugas untuk mengubah nama, deadline, catatan, atau status.
Hapus tugas secara permanen (dengan konfirmasi terlebih dahulu).
Lihat Detail tugas yang menampilkan informasi lengkap dan memberikan opsi untuk memperbarui status tugas secara manual.
Ada juga form khusus untuk Tambah Tugas, yang berisi input seperti nama tugas, mata kuliah (dropdown), deadline (date picker), catatan tambahan (textarea), dan status tugas.
Dengan fitur ini, seluruh siklus pengelolaan tugas — dari pembuatan hingga penyelesaian — dapat dilakukan dengan efisien dan mudah.

4. Mata Kuliah
Fitur ini memungkinkan pengguna untuk mengelompokkan dan memfilter tugas berdasarkan mata kuliah tertentu. Saat pengguna memilih salah satu mata kuliah dari dropdown atau tab yang tersedia, daftar tugas secara otomatis akan difilter hanya menampilkan tugas untuk mata kuliah tersebut.
Fitur ini sangat membantu dalam kondisi di mana jumlah tugas sangat banyak dan pengguna ingin fokus pada satu mata kuliah saja. Pengelompokkan seperti ini juga membuat pengguna lebih terorganisir dan dapat menyusun prioritas pengerjaan tugas dengan lebih baik.

5. Kalender
Tampilan kalender akademik memberikan cara visual untuk melihat dan mengelola tugas berdasarkan tanggal deadline. Kalender ini bisa ditampilkan dalam mode mingguan atau bulanan, dengan toggle switch yang memudahkan pengguna berpindah antar tampilan.
Setiap tugas yang memiliki deadline akan muncul sebagai item atau event di tanggal tersebut. Pengguna bisa mengetuk tugas di dalam kalender untuk langsung melihat detailnya atau mengedit.

6. Pengaturan
Fitur pengaturan digunakan untuk menyesuaikan preferensi penggunaan aplikasi sesuai kebutuhan masing-masing pengguna. Beberapa hal yang bisa diatur antara lain:
Data akun pengguna seperti nama, email, dan foto profil.
Tema tampilan, misalnya mengaktifkan mode gelap (dark mode) atau terang.
Notifikasi, yaitu mengatur apakah ingin menampilkan pengingat tugas mendekati deadline.
Kelola Mata Kuliah, yaitu menambah atau menghapus daftar mata kuliah yang ditampilkan di aplikasi.
Logout, untuk keluar dari aplikasi dengan aman.
Dengan adanya pengaturan, pengguna memiliki kontrol lebih terhadap pengalaman menggunakan SIKATU secara personal.

**Implementasi Pilar PBO**
1. Enkapsulasi (Encapsulation)
Penerapan dalam SIKATU:
a. Data User dan Login
- Informasi sensitif seperti password, email, dan data pribadi pengguna disembunyikan dalam class User dengan akses private
- Validasi login dilakukan secara internal dalam class Authentication
- Session management di-enkapsulasi untuk menjaga keamanan akses sistem
b. Data Tugas
- Detail tugas seperti nama, deadline, status, dan catatan dilindungi dalam class Tugas
- Akses data tugas hanya melalui method khusus yang sudah divalidasi
-Logic penghitungan progress bar dan status deadline disembunyikan dari interface pengguna
c. Pengaturan Aplikasi
- Preferensi pengguna (tema, notifikasi, dll) di-enkapsulasi dalam class Settings
- Konfigurasi database dan koneksi dilindungi dari akses langsung
Manfaat: Melindungi integritas data, mencegah perubahan tidak sah, dan memudahkan maintenance kode.

2. Pewarisan (Inheritance)
Penerapan dalam SIKATU:
a. Hierarki Entity Base
- Class BaseEntity sebagai parent yang berisi properti umum seperti ID, tanggal dibuat, tanggal diupdate
- Class Tugas, MataKuliah, dan User inherit dari BaseEntity
- Method umum seperti validasi data dan update timestamp diturunkan ke semua child class
b. Sistem Notifikasi
- Class Notification sebagai parent untuk berbagai jenis pemberitahuan
- DeadlineNotification (H-3, H-1) inherit dari Notification
- CompletionNotification untuk notifikasi tugas selesai
- Setiap jenis notifikasi memiliki cara tampil dan kondisi yang berbeda
c. Tampilan Interface
- BaseView sebagai parent untuk semua tampilan (Dashboard, TugasView, KalenderView)
- Method umum seperti refreshData(), showLoading(), hideLoading() diturunkan
- Setiap view memiliki implementasi spesifik untuk menampilkan data
Manfaat: Mengurangi duplikasi kode, konsistensi struktur, dan memudahkan penambahan fitur baru.

3. Polimorfisme (Polymorphism)
Penerapan dalam SIKATU:
a. Data Access Layer
- Interface DataManager untuk operasi database (create, read, update, delete)
-TugasDataManager, UserDataManager, MataKuliahDataManager mengimplementasi interface yang sama
- Method yang sama dapat dipanggil untuk berbagai jenis data dengan behavior yang berbeda
b. Export Functionality
- Interface Exporter untuk berbagai format export tugas
- PDFExporter, ExcelExporter, CSVExporter mengimplementasi method export() yang sama
- Pengguna dapat memilih format export tanpa mengubah logic pemanggilan
c. Sistem Filter dan Sort
- Interface FilterStrategy untuk berbagai cara filter tugas
- FilterByMataKuliah, FilterByStatus, FilterByDeadline memiliki implementasi berbeda
- SortByDeadline, SortByNama, SortByStatus untuk pengurutan dengan kriteria berbeda
d. Notification Handler
- Method showNotification() dapat menangani berbagai jenis notifikasi
- DeadlineNotification, ReminderNotification, CompletionNotification diperlakukan sama saat ditampilkan
- Behavior menampilkan notifikasi berbeda sesuai jenisnya
e. View Management
- Method navigateTo() dapat menangani perpindahan ke berbagai halaman
- DashboardView, TugasView, KalenderView, SettingsView diperlakukan sama saat navigasi
- Setiap view memiliki cara render dan initialize yang berbeda

Manfaat: Fleksibilitas sistem, mudah ditambahkan fitur baru, kode lebih modular dan maintainable.
