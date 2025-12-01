CREATE DATABASE IF NOT EXISTS hotel_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE hotel_db;

-- users: role = ADMIN / CUSTOMER
CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  full_name VARCHAR(100),
  role ENUM('ADMIN','CUSTOMER') NOT NULL DEFAULT 'CUSTOMER',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- rooms
CREATE TABLE rooms (
  id INT AUTO_INCREMENT PRIMARY KEY,
  room_number VARCHAR(20) UNIQUE NOT NULL,
  room_type ENUM('STANDARD','DELUXE','SUITE') NOT NULL,
  price_per_day DECIMAL(10,2) NOT NULL,
  status ENUM('AVAILABLE','OCCUPIED','MAINTENANCE') DEFAULT 'AVAILABLE',
  description TEXT
);

-- reservations (check-in/check-out + payment)
CREATE TABLE reservations (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  room_id INT NOT NULL,
  check_in DATE NOT NULL,
  check_out DATE NOT NULL,
  total_price DECIMAL(12,2) NOT NULL,
  status ENUM('BOOKED','CHECKED_IN','CHECKED_OUT','CANCELLED') DEFAULT 'BOOKED',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (room_id) REFERENCES rooms(id) ON DELETE CASCADE
);

-- sample admin account
INSERT INTO users (username, password, full_name, role)
VALUES ('admin','admin123','Administrator','ADMIN');

-- sample rooms
INSERT INTO rooms (room_number, room_type, price_per_day, status)
VALUES 
('101','STANDARD',250000,'AVAILABLE'),
('102','DELUXE',500000,'AVAILABLE'),
('201','SUITE',1000000,'MAINTENANCE');
