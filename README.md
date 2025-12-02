# 🏨 Hotel Management System

<div align="center">

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
![Swing](https://img.shields.io/badge/Swing-GUI-blue?style=for-the-badge)

**A comprehensive hotel management system built with Java Swing and MySQL**

[Features](#-features) • [Installation](#-installation) • [Usage](#-usage) • [Contributors](#-contributors)

</div>

---

## 📖 About

Hotel Management System adalah aplikasi desktop yang dirancang untuk memudahkan pengelolaan hotel, mulai dari manajemen kamar, reservasi, hingga laporan keuangan. Sistem ini memiliki dua jenis akses: **Admin** dan **Customer**, dengan fitur-fitur yang disesuaikan untuk masing-masing role.

## ✨ Features

### 👨‍💼 Admin Features

- 🏠 **Room Management** - Kelola kamar hotel (tambah, ubah status)
- 👥 **User Info** - Lihat dan kelola semua reservasi pelanggan
- 💰 **Finance Report** - Laporan pendapatan dari reservasi yang telah selesai
- 📊 **Dashboard** - Monitoring real-time status hotel

### 👤 Customer Features

- 🛏️ **Room Details** - Lihat daftar kamar dan ketersediaannya
- ✅ **Check In** - Booking kamar dengan perhitungan otomatis
- 📜 **Check History** - Riwayat reservasi pribadi
- ❌ **Cancel Booking** - Batalkan reservasi jika diperlukan

## 🛠️ Tech Stack

- **Language:** Java 8+
- **GUI Framework:** Java Swing
- **Database:** MySQL 8.0
- **Build Tool:** Maven 3.x
- **JDBC Driver:** MySQL Connector/J 8.0.33

## 📋 Prerequisites

Pastikan Anda telah menginstall:

- ☕ Java JDK 8 atau lebih tinggi
- 🗄️ MySQL Server 8.0+
- 📦 Apache Maven 3.x

## 🚀 Installation

### 1. Clone Repository

```bash
git clone https://github.com/username/hotel-management.git
cd hotel-management
```

### 2. Setup Database

```bash
mysql -u root -p < sql/schema.sql
```

Atau jalankan script SQL secara manual:

- Buat database `hotel_db`
- Import file `sql/schema.sql`

### 3. Konfigurasi Database

Edit file `src/main/java/com/hotel/config/DBConnection.java`:

```java
private static final String URL = "jdbc:mysql://localhost:3306/hotel_db?serverTimezone=UTC";
private static final String USER = "root";
private static final String PASS = "your_password"; // Sesuaikan password Anda
```

### 4. Build Project

```bash
mvn clean package
```

### 5. Run Application

```bash
java -jar target/hotel-management-1.0.0-jar-with-dependencies.jar
```

Atau jalankan langsung dari IDE:

```bash
mvn exec:java -Dexec.mainClass="main.java.com.hotel.App"
```

## 💻 Usage

### Default Login Credentials

**Admin Account:**

- Username: `admin`
- Password: `admin123`

**Customer:**

- Buat akun baru melalui tombol "Create Account" di halaman login

### Database Schema

**Users Table:**

- Admin dan Customer dengan role-based access
- Password (⚠️ belum di-hash, gunakan hashing untuk production)

**Rooms Table:**

- Room types: STANDARD, DELUXE, SUITE
- Status: AVAILABLE, OCCUPIED, MAINTENANCE

**Reservations Table:**

- Status: BOOKED, CHECKED_IN, CHECKED_OUT, CANCELLED
- Otomatis menghitung total harga berdasarkan durasi menginap

## 📸 Screenshots

### Login Page

Interface login dengan opsi registrasi akun baru.

### Admin Dashboard

Manajemen lengkap hotel dengan akses ke semua fitur administrasi.

### Customer Interface

Interface user-friendly untuk pelanggan melakukan booking kamar.

## 🗂️ Project Structure

```
hotel-management/
├── sql/
│   └── schema.sql              # Database schema
├── src/main/java/com/hotel/
│   ├── App.java                # Main application
│   ├── config/
│   │   └── DBConnection.java   # Database connection
│   ├── dao/                    # Data Access Objects
│   │   ├── UserDAO.java
│   │   ├── RoomDAO.java
│   │   └── ReservationDAO.java
│   ├── model/                  # Data models
│   │   ├── User.java
│   │   ├── Room.java
│   │   └── Reservation.java
│   ├── ui/
│   │   ├── login/              # Login & Register UI
│   │   ├── admin/              # Admin UI components
│   │   └── customer/           # Customer UI components
│   └── util/                   # Utility classes
└── pom.xml                     # Maven configuration
```

## 🔒 Security Notes

⚠️ **Penting untuk Production:**

- Implementasikan password hashing (BCrypt, Argon2)
- Gunakan prepared statements (sudah diimplementasikan)
- Tambahkan input validation yang lebih ketat
- Implementasikan session management
- Gunakan environment variables untuk credentials

## 🤝 Contributors

<table>
  <tr>
    <td align="center">
      <a href="https://github.com/mughni">
        <img src="https://github.com/mughni.png" width="100px;" alt="Mughni"/><br />
        <sub><b>Mughni</b></sub>
      </a><br />
      <sub>Developer</sub>
    </td>
    <td align="center">
      <a href="https://github.com/adamxyz">
        <img src="https://github.com/adamxyz.png" width="100px;" alt="Adam XYZ"/><br />
        <sub><b>Adam XYZ</b></sub>
      </a><br />
      <sub>Developer</sub>
    </td>
  </tr>
</table>

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🙏 Acknowledgments

- Java Swing Documentation
- MySQL Documentation
- Maven Central Repository

## 📞 Contact & Support

Jika Anda menemukan bug atau memiliki saran, silakan buat issue di repository ini.

---

<div align="center">

**⭐ Star this repository if you find it helpful!**

Made with ❤️ by Hotel Management Team

</div>
