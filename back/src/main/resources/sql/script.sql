-- Disable foreign key checks for safe creation
SET FOREIGN_KEY_CHECKS = 0;
-- User Table
CREATE TABLE User (
id INT AUTO_INCREMENT PRIMARY KEY,
username VARCHAR(50) UNIQUE NOT NULL,
email VARCHAR(100) UNIQUE NOT NULL,
password VARCHAR(255) NOT NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE
CURRENT_TIMESTAMP
);
-- Indexes for quick searches
CREATE INDEX idx_user_username ON User(username)
CREATE INDEX idx_user_email ON User(email)
-- Theme Table
CREATE TABLE Theme (
id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(100) UNIQUE NOT NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE
CURRENT_TIMESTAMP
);
-- Subscription Table (Many-to-Many between User and Theme)
CREATE TABLE Subscription (
user_id INT NOT NULL,
theme_id INT NOT NULL,
subscribed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (user_id, theme_id),
FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE,
FOREIGN KEY (theme_id) REFERENCES Theme(id) ON DELETE CASCADE
);
-- Article Table
CREATE TABLE Article (
id INT AUTO_INCREMENT PRIMARY KEY,
theme_id INT NOT NULL,
title VARCHAR(255) NOT NULL,
content TEXT NOT NULL,

author_id INT NOT NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE
CURRENT_TIMESTAMP,
FOREIGN KEY (theme_id) REFERENCES Theme(id) ON DELETE CASCADE,
FOREIGN KEY (author_id) REFERENCES User(id) ON DELETE CASCADE
);
-- Index for quick retrieval of articles by theme
CREATE INDEX idx_article_theme_id ON Article(theme_id);
-- Comment Table
CREATE TABLE Comment (
id INT AUTO_INCREMENT PRIMARY KEY,
article_id INT NOT NULL,
content TEXT NOT NULL,
author_id INT NOT NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE
CURRENT_TIMESTAMP,
FOREIGN KEY (article_id) REFERENCES Article(id) ON DELETE CASCADE,
FOREIGN KEY (author_id) REFERENCES User(id) ON DELETE CASCADE
);
-- Index for faster comment retrieval by article
CREATE INDEX idx_comment_article_id ON Comment(article_id);
-- Enable foreign key checks again
SET FOREIGN_KEY_CHECKS = 1;