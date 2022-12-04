CREATE DATABASE product_management_app;
USE product_management_app;

CREATE TABLE `categories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `customers` (
  `id` int NOT NULL AUTO_INCREMENT,	
  `name` varchar(45) NOT NULL,
  `age` int DEFAULT NULL,
  `gender` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `products` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `category_id` int NOT NULL,
  `quantity` int unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `category_id_idx` (`category_id`)
);

CREATE TABLE `receipts` (
  `id` int NOT NULL AUTO_INCREMENT,
  `customer_id` int NOT NULL,
  `status` varchar(45) NOT NULL DEFAULT 'Unchecked',
  `is_valid` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`id`),
  KEY `customer_id_idx` (`customer_id`),
  CONSTRAINT `customer_id` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`)
);

CREATE TABLE `receipt_details` (
  `receipt_id` int NOT NULL,
  `product_id` varchar(45) NOT NULL,
  `quantity` varchar(45) NOT NULL,
  `is_ok` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`receipt_id`,`product_id`),
  KEY `product_id_idx` (`product_id`),
  CONSTRAINT `product_id` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `receipt_id` FOREIGN KEY (`receipt_id`) REFERENCES `receipts` (`id`)
);