CREATE DATABASE IF NOT EXISTS `forestory-local-database`;
GRANT ALL PRIVILEGES ON `forestory-local-database`.* TO 'forestory-local-user'@'%' WITH GRANT OPTION;

USE `forestory-local-database`;