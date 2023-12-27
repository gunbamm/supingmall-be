CREATE SCHEMA shopping_mall_project;
USE shopping_mall_project;

CREATE TABLE `users` (
  `user_id` integer PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `gender` varchar(10) NOT NULL CHECK (status IN ('남성', '여성')),
  `phone_number` varchar(15) UNIQUE NOT NULL,
  `email` varchar(30) UNIQUE NOT NULL,
  `password` varchar(255) NOT NULL,
  `image_url` varchar(255) NOT NULL DEFAULT "url",
  `address` varchar(255) NOT NULL,
  `join_date` timestamp NOT NULL,
  `status` VARCHAR(255) DEFAULT 'normal',
  `failure_count` INT DEFAULT 0,
  `deletion_date` timestamp ,
  `lock_date` timestamp 
);

CREATE TABLE `roles` (
  `roles_id` integer PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255) NOT NULL
);

CREATE TABLE `user_roles` (
  `user_roles_id` integer PRIMARY KEY AUTO_INCREMENT,
  `user_id` integer NOT NULL,
  `role_id` integer NOT NULL,
  FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
  FOREIGN KEY (`role_id`) REFERENCES `roles` (`roles_id`)
);

INSERT INTO `roles` (`name`) VALUES
('ROLE_ADMIN'),
('ROLE_SUPERUSER'),
('ROLE_USER');