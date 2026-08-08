CREATE DATABASE IF NOT EXISTS library;
USE library;

CREATE TABLE IF NOT EXISTS LIBRARIAN (
    lib_id INT PRIMARY KEY,
    lib_name VARCHAR(100) NOT NULL,
    lib_password VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS member (
    m_id INT PRIMARY KEY,
    m_name VARCHAR(100) NOT NULL,
    m_email VARCHAR(100) NOT NULL,
    m_password VARCHAR(100) NOT NULL,
    contact_info VARCHAR(100),
    street VARCHAR(100),
    city VARCHAR(100),
    zipcode VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS author (
    a_id INT PRIMARY KEY,
    a_name VARCHAR(100) NOT NULL,
    a_email VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS publisher (
    p_id INT PRIMARY KEY,
    p_name VARCHAR(100) NOT NULL,
    p_email VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS books (
    b_id INT PRIMARY KEY,
    b_name VARCHAR(100) NOT NULL,
    genre VARCHAR(100),
    aisle INT,
    L_id INT,
    A_id INT,
    P_id INT,
    FOREIGN KEY (L_id) REFERENCES LIBRARIAN(lib_id) ON DELETE CASCADE,
    FOREIGN KEY (A_id) REFERENCES author(a_id) ON DELETE CASCADE,
    FOREIGN KEY (P_id) REFERENCES publisher(p_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS borrows (
    b_id INT,
    m_id INT,
    borrow_date DATE,
    return_date DATE,
    penalty INT DEFAULT 0,
    PRIMARY KEY (b_id, m_id),
    FOREIGN KEY (b_id) REFERENCES books(b_id) ON DELETE CASCADE,
    FOREIGN KEY (m_id) REFERENCES member(m_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS history (
    m_id INT,
    b_id INT,
    borrow_date DATE,
    return_date DATE,
    FOREIGN KEY (b_id) REFERENCES books(b_id) ON DELETE CASCADE,
    FOREIGN KEY (m_id) REFERENCES member(m_id) ON DELETE CASCADE
);

-- Insert dummy data
INSERT IGNORE INTO LIBRARIAN (lib_id, lib_name, lib_password) VALUES (1, 'admin', 'admin123');

-- Stored Procedures and Functions
DELIMITER //

DROP FUNCTION IF EXISTS check_user_inBorrows //
CREATE FUNCTION check_user_inBorrows(member_id INT) RETURNS INT
DETERMINISTIC
BEGIN
    DECLARE cnt INT;
    SELECT COUNT(*) INTO cnt FROM borrows WHERE m_id = member_id;
    IF cnt > 0 THEN
        RETURN 1;
    ELSE
        RETURN 0;
    END IF;
END //

DROP PROCEDURE IF EXISTS calc_penalty //
CREATE PROCEDURE calc_penalty(IN member_id INT)
BEGIN
    UPDATE borrows 
    SET penalty = DATEDIFF(CURDATE(), return_date) * 10 
    WHERE m_id = member_id AND return_date < CURDATE();
END //

DELIMITER ;

-- Create member user if needed
CREATE USER IF NOT EXISTS 'lib_member'@'localhost' IDENTIFIED BY 'member123';
GRANT ALL PRIVILEGES ON library.* TO 'lib_member'@'localhost';
FLUSH PRIVILEGES;
