-- phpMyAdmin SQL Dump
-- version 4.9.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Sep 18, 2022 at 06:46 PM
-- Server version: 10.5.16-MariaDB
-- PHP Version: 7.3.32

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `id18971716_library_data`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id` varchar(40) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(40) COLLATE utf8_unicode_ci NOT NULL,
  `pwd` varchar(40) COLLATE utf8_unicode_ci NOT NULL,
  `otp` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(40) COLLATE utf8_unicode_ci NOT NULL,
  `course` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `email`, `pwd`, `otp`, `name`, `course`) VALUES
('06914902018', 'rahulbidawas@gmail.com', '123456789', '', 'Rahul Lour', 'BCA');

-- --------------------------------------------------------

--
-- Table structure for table `Books`
--

CREATE TABLE `Books` (
  `book_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `author` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `publisher` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `yop` int(11) NOT NULL,
  `course` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `total_books` int(11) NOT NULL,
  `books_available` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `Books`
--

INSERT INTO `Books` (`book_name`, `author`, `publisher`, `yop`, `course`, `total_books`, `books_available`) VALUES
('Basic Electrical Engineering', 'S.N Singh', 'PHI India', 2012, 'B.TECH', 8, 8),
('Brave New World', 'Aldous Huxley', 'Chatto and Windex', 1932, 'BBA', 3, 3),
('Brief Answers To Big Questions', 'Stephen Hawking', 'JM publishers', 2018, 'BSC', 2, 1),
('Business Ethics', 'A.C. Fernando', 'Pearson Education', 2013, 'B.COM', 2, 2),
('Business Law', 'N.D. Kapoor', 'Sultan Chand', 2013, 'BBA', 4, 4),
('Business Mathematics', 'Trivedi', 'Pearson Education', 2012, 'B.COM', 13, 13),
('Cell Biology and Genetics', 'P.S. Verma', 'Kalyani Publishers', 2009, 'BSC', 7, 7),
('Computer Networks', 'A. S. Tenanbaum', 'Pearson Education Asia', 2003, 'BCA', 5, 5),
('Computer System Architecture', 'Morris Mano', 'Prentice-Hall of India', 1999, 'BCA', 8, 8),
('Corporate Softskill', 'S. Gulati', 'Rupa Publication', 2002, 'BBA', 16, 16),
('Cost Accounting', 'M.N. Arora', 'Vikas Publishing House', 2012, 'BBA', 18, 18),
('Discrete Mathematical Structures', 'Kolman', 'PHI', 1996, 'B.TECH', 8, 8),
('Electronic Commerce', 'Gary Schneider', 'Cengage Learning', 2014, 'BBA', 9, 9),
('Electronic Devices And Circuit Theory', 'Robert T. Paynter', 'Pearson Education', 2006, 'B.TECH', 8, 8),
('Engineering Physics', 'Uma Mukhrji', 'Narosa', 2010, 'B.TECH', 3, 3),
('Environmental Science', 'G. T. Miller', 'Thomas Learning', 2012, 'B.TECH', 16, 16),
('Essential University Physics', 'Richard Wolfson', 'Pearson', 2009, 'B.TECH', 9, 9),
('Financial Management', 'P.K. Jain ', 'McGraw Hill Education ', 2014, 'B.COM ', 7, 7),
('Fundamentals Of Database Systems', 'Elmsari and Navathe', 'Pearson', 2013, 'B.TECH', 13, 13),
('Hidden Lang Of Computer Hardware And Software', 'Charles Petzold', 'Microsoft press', 1999, 'BCA', 1, 1),
('Higher Engineering Mathematics', 'B. S. Grewal', 'Khanna Publications', 2014, 'B.TECH', 21, 21),
('Law Of Contract', 'Anson', 'Oxford University Press', 2010, 'LLB', 8, 8),
('Law Of Partnership', 'Avtar Singh', 'Eastern Book Company', 2012, 'LLB', 19, 19),
('Leave The World Behind', 'Rumaan Alum', 'Ecco', 2020, 'BBA', 1, 1),
('Magical Book On Quicker Maths', 'M Tyra', 'BSC Publishing Co Pvt Ltd', 2018, 'B.COM', 10, 10),
('Managerial Accounting', 'R.W. Hilton', 'McGraw Hill Education', 2017, 'B.COM', 3, 3),
('Multimedia- Making It Work', 'Tay Vaughan', 'McGraw Hill', 2014, 'B.TECH', 15, 14),
('Object-Oriented Programming With Java', 'G.T. Thampi', 'Wiley', 2009, 'BCA', 25, 25),
('Optics', 'A. Ghatak', 'TMH', 2013, 'B.Tech', 4, 4),
('Physical Chemistry', 'P. Reid', 'Pearson Education', 2013, 'B.Tech', 22, 22),
('Political Theory', 'Eddy Asirvatham', 'S. Chand & Company Ltd', 2012, 'LLB', 17, 17),
('Programming In ANSI C', 'E. BalaGuruswamy', 'McGraw Hill Education', 2008, 'BCA', 10, 10),
('Sale Of Goods', 'Avtar Singh', 'Eastern Book Company', 2011, 'LLB', 9, 9),
('The Law Of Torts', 'Ramaswamy Iyer‟s', 'Lexis Nexis', 2007, 'LLB', 21, 21),
('The Power Of Habit', 'Charles Duhigg', 'Duhigg', 2012, 'B.Com', 10, 10),
('The Soul Of A New Machine', 'Tracy Kidder', 'Little, Brown and company', 1981, 'BCA', 3, 2);

-- --------------------------------------------------------

--
-- Table structure for table `Books_lent`
--

CREATE TABLE `Books_lent` (
  `bookname` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `id` varchar(40) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `borrow_date` date DEFAULT NULL,
  `return_date` date DEFAULT NULL,
  `late_fee` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Books_lent`
--

INSERT INTO `Books_lent` (`bookname`, `id`, `borrow_date`, `return_date`, `late_fee`) VALUES
('Brief Answers To Big Questions', '06914902018', '2022-09-09', '2022-09-16', 20),
('Multimedia- Making It Work', '06914902018', '2022-09-09', '2022-09-16', 20),
('The Soul Of A New Machine', '06914902018', '2022-09-09', '2022-09-16', 20);

-- --------------------------------------------------------

--
-- Table structure for table `Books_requests`
--

CREATE TABLE `Books_requests` (
  `id` varchar(40) COLLATE utf8_unicode_ci NOT NULL,
  `bookname` varchar(50) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `Books_requests`
--

INSERT INTO `Books_requests` (`id`, `bookname`) VALUES
('06914902018', 'Business Law'),
('06914902018', 'Cost Accounting');

-- --------------------------------------------------------

--
-- Table structure for table `Books_return_requests`
--

CREATE TABLE `Books_return_requests` (
  `id` varchar(40) COLLATE utf8_unicode_ci NOT NULL,
  `bookname` varchar(50) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `Books_return_requests`
--

INSERT INTO `Books_return_requests` (`id`, `bookname`) VALUES
('06914902018', 'Brief Answers To Big Questions'),
('06914902018', 'Multimedia- Making It Work'),
('06914902018', 'The Soul Of A New Machine');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `name` varchar(40) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `id` varchar(40) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `pwd` varchar(40) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(40) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `otp` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `books_taken` int(10) DEFAULT NULL,
  `course` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`name`, `id`, `pwd`, `email`, `otp`, `books_taken`, `course`) VALUES
('Rahul Lour', '06914902018', '123456789', 'rahulbidawas@gmail.com', 'HJYaYDAnIN', NULL, 'BCA');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Books`
--
ALTER TABLE `Books`
  ADD PRIMARY KEY (`book_name`);

--
-- Indexes for table `Books_lent`
--
ALTER TABLE `Books_lent`
  ADD PRIMARY KEY (`bookname`,`id`);

--
-- Indexes for table `Books_return_requests`
--
ALTER TABLE `Books_return_requests`
  ADD PRIMARY KEY (`id`,`bookname`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
