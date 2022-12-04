INSERT INTO customers (name, age, gender)
VALUES
 ('Do Phat', 22, 'Female'),
 ('Huynh Bao', 19, 'Female'),
 ('Le Khai', 19, 'Male'),
 ('Le Dat', 18, 'Male'),
 ('Le Khoa', 40, 'Female'),
 ('Dang Phuc', 35, 'Female'),
 ('Do Phuc', 31, 'Male'),
 ('Le Phuc', 40, 'Female'),
 ('Phan Phuc', 35, 'Male'),
 ('Nguyen Phat', 22, 'Female'),
 ('Tran Huy', 35, 'Female'),
 ('Tran Phat', 22, 'Male'),
 ('Le Bao', 28, 'Female'),
 ('Phan Phat', 19, 'Female'),
 ('Tran Huy', 18, 'Female'),
 ('Le Dat', 18, 'Male'),
 ('Pham Khai', 29, 'Female'),
 ('Huynh Phuc', 19, 'Female'),
 ('Huynh Phuc', 18, 'Female'),
 ('Tran Khoa', 40, 'Female');

INSERT INTO `categories` (`name`) VALUES ('A');
INSERT INTO `categories` (`name`) VALUES ('B');
INSERT INTO `categories` (`name`) VALUES ('C');
INSERT INTO `categories` (`name`) VALUES ('D');
INSERT INTO `categories` (`name`) VALUES ('E');

INSERT INTO `products` (`id`, `name`, `price`, `category_id`, `quantity`) VALUES ('3c2c3a2f-4b64-46a0-88cb-00e67b2ddf18', 'ProductA-01', '10', '1', '20');
INSERT INTO `products` (`id`, `name`, `price`, `category_id`, `quantity`) VALUES ('a4110251-8d41-4411-899c-a42e4974bfb8', 'ProductB-03', '20', '3', '20');
INSERT INTO `products` (`id`, `name`, `price`, `category_id`, `quantity`) VALUES ('a5ffd760-9532-4a5a-94f9-ffbf75d2c39d', 'ProductB-02', '15', '2', '40');
INSERT INTO `products` (`id`, `name`, `price`, `category_id`, `quantity`) VALUES ('ec5d0429-eb00-4eec-96b7-7e87ebc0202b', 'ProductC-04', '35', '4', '15');

INSERT INTO `receipts` (`customer_id`) VALUES ('1');
INSERT INTO `receipts` (`customer_id`) VALUES ('1');
INSERT INTO `receipts` (`customer_id`) VALUES ('3');
INSERT INTO `receipts` (`customer_id`) VALUES ('2');

INSERT INTO `receipt_details` (`receipt_id`, `product_id`, `quantity`) VALUES ('1', 'a5ffd760-9532-4a5a-94f9-ffbf75d2c39d', '8');
INSERT INTO `receipt_details` (`receipt_id`, `product_id`, `quantity`) VALUES ('1', '3c2c3a2f-4b64-46a0-88cb-00e67b2ddf18', '10');
INSERT INTO `receipt_details` (`receipt_id`, `product_id`, `quantity`) VALUES ('2', 'ec5d0429-eb00-4eec-96b7-7e87ebc0202b', '2');
INSERT INTO `receipt_details` (`receipt_id`, `product_id`, `quantity`) VALUES ('3', 'ec5d0429-eb00-4eec-96b7-7e87ebc0202b', '1');
INSERT INTO `receipt_details` (`receipt_id`, `product_id`, `quantity`) VALUES ('3', 'a5ffd760-9532-4a5a-94f9-ffbf75d2c39d', '5');
