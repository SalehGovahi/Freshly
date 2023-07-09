-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3307
-- Generation Time: Jul 04, 2023 at 12:16 PM
-- Server version: 10.1.38-MariaDB
-- PHP Version: 7.3.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `freshly`
--

-- --------------------------------------------------------

--
-- Table structure for table `auction`
--

CREATE TABLE `auction` (
  `productid` varchar(50) NOT NULL,
  `remainingdays` varchar(50) NOT NULL,
  `auctionprice` varchar(50) NOT NULL,
  `date` date NOT NULL,
  `winner` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `auction`
--

INSERT INTO `auction` (`productid`, `remainingdays`, `auctionprice`, `date`, `winner`) VALUES
('22', '1', '600', '2023-07-04', 'admin04');

-- --------------------------------------------------------

--
-- Table structure for table `comments`
--

CREATE TABLE `comments` (
  `Text` varchar(200) NOT NULL,
  `productid` varchar(200) NOT NULL,
  `Username` varchar(50) NOT NULL,
  `isbought` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `comments`
--

INSERT INTO `comments` (`Text`, `productid`, `Username`, `isbought`) VALUES
('salam', '2', 'mammad', 'no'),
('salam', '2', 'mammad', 'no'),
('khoobi', '2', 'mammad', 'no'),
('chetori', '3', 'mammad', 'yes'),
('kir', '22', 'admin03', 'Yes'),
('koony', '22', 'admin03', 'Yes'),
('che geroon', '10', 'admin03', 'false'),
('kir too nanat', '0', 'admin03', 'true');

-- --------------------------------------------------------

--
-- Table structure for table `costumer`
--

CREATE TABLE `costumer` (
  `Username` varchar(50) NOT NULL,
  `Password` varchar(50) NOT NULL,
  `FirstName` varchar(50) NOT NULL,
  `LastName` varchar(50) NOT NULL,
  `PhoneNumber` varchar(50) NOT NULL,
  `EmailAddress` varchar(50) NOT NULL,
  `cart` varchar(50) NOT NULL,
  `wallet` varchar(50) NOT NULL,
  `discountcode` varchar(50) NOT NULL,
  `history` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `costumer`
--

INSERT INTO `costumer` (`Username`, `Password`, `FirstName`, `LastName`, `PhoneNumber`, `EmailAddress`, `cart`, `wallet`, `discountcode`, `history`) VALUES
('admin', 'admin1234', 'admin', 'admin', '09153218465', 'amirzadehmohammadjavad203@gmail.com', '8585_1@6969_1', '', '', ''),
('admin01', 'admin123', 'admin', 'admin', '09152166330', 'saleh.govahi@gmail.com', '8585_1@6969_1', '0', '98', ''),
('admin02', 'admin123', 'admin', 'admin', '09152166330', 'amirrezareza299@gmail.com', '8585_1@6969_1', '98', '84@87@656@87', ''),
('mhka', '1234', 'm', 'ka', '09046993150', 'mhkaprogrammer@gmail.com', '8585_1@6969_1', '0', '54654@3', ''),
('admin03', 'admin123', 'admin', 'admin', '09152166330', 'saleh.govahi@gmail.com', '8585_1@6969_1', '6577090', '84@87@87', '8585_1@6969_1@9_1@8585_1@6969_1@21_1@8585_1@6969_1@9_1@8585_1@6969_1@21_1@8585_1@6_2@6969_1@8585_1@6969_1@7_1@8585_1@6969_1@21_2@8585_1@20_5@6969_1@8585_1@6969_1@0_1@8585_1@2_1@6969_1@8585_1@6969_1@21_1@8585_1@6969_1@21_2@8585_1@0_1@7_2@6969_1@21_2@'),
('admin04', 'admin123', 'admin', 'admin', '091532235', 'ehsandabidian0@gmail.com', '8585_1@25_2@6969_1', '1099500', 'freshly1402@freshly1383@freshly1382', '');

-- --------------------------------------------------------

--
-- Table structure for table `financial`
--

CREATE TABLE `financial` (
  `count` text NOT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `financial`
--

INSERT INTO `financial` (`count`, `date`) VALUES
('+100', '2023-07-04');

-- --------------------------------------------------------

--
-- Table structure for table `inputoutput`
--

CREATE TABLE `inputoutput` (
  `date` date NOT NULL,
  `id` text NOT NULL,
  `count` text NOT NULL,
  `store` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `inputoutput`
--

INSERT INTO `inputoutput` (`date`, `id`, `count`, `store`) VALUES
('2023-07-04', '7', '+90', 'a'),
('2023-07-01', '7', '-10', 'a'),
('2023-06-13', '7', '+20', 'a'),
('2023-06-02', '7', '-65', 'a'),
('2022-11-16', '7', '-654654', 'a'),
('2023-07-04', '7', '+145', 'a'),
('2023-07-04', '6', '-50', 'b');

-- --------------------------------------------------------

--
-- Table structure for table `messages`
--

CREATE TABLE `messages` (
  `sender` text NOT NULL,
  `recipient` text NOT NULL,
  `text` text NOT NULL,
  `new` text NOT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `messages`
--

INSERT INTO `messages` (`sender`, `recipient`, `text`, `new`, `date`) VALUES
('mamali1', 'admin', 'salam', 'Yes', '2023-07-04'),
('mamali', 'admin', 'hi', 'Yes', '2023-07-04');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `name` varchar(50) NOT NULL,
  `productid` varchar(50) NOT NULL,
  `price` varchar(50) NOT NULL,
  `point` varchar(50) NOT NULL,
  `numberofpoints` varchar(50) NOT NULL,
  `brand` varchar(50) NOT NULL,
  `uniqueproperties` varchar(50) NOT NULL,
  `image` varchar(200) NOT NULL,
  `information` varchar(50) NOT NULL,
  `type` varchar(50) NOT NULL,
  `stock` varchar(50) NOT NULL,
  `productSeller` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`name`, `productid`, `price`, `point`, `numberofpoints`, `brand`, `uniqueproperties`, `image`, `information`, `type`, `stock`, `productSeller`) VALUES
('kalbas', '1', '1230000', '0', '0', 'kalbasjat', '', 'src/main/resources/com/example/freshly/ProductsImages/product_id1temp2.jpg', '', '???? ????????', '100', 'mamali'),
('kalbas', '2', '135', '0', '0', 'kalbasjat', '', 'src/main/resources/com/example/freshly/ProductsImages/product_id2temp2.jpg', '', '???? ????????', '100', 'mamali'),
('kalbas', '4', '456', '0', '0', 'kalbasjat', '', 'src/main/resources/com/example/freshly/ProductsImages/product_id4temp2.jpg', '', '???? ????????', '100', 'mamali'),
('tokhme', '4', '9800', '0', '0', 'tokhme', '', 'src/main/resources/com/example/freshly/ProductsImages/product_id4etemadLogoFooter.png', '', '??????', '100', 'mamali'),
('peste', '5', '6542110', '4.0', '2', 'pestejaat', '', 'src/main/resources/com/example/freshly/ProductsImages/product_id51.jpg', '', '??????', '100', 'mamali'),
('pirashki', '6', '65000', '4.5', '2', 'pirashkijat', '', 'src/main/resources/com/example/freshly/ProductsImages/product_id6Screenshot 2022-01-22 173057.png', '', '????', '100', 'mamali'),
('p', '7', '7', '0', '0', 'p7', '', 'src/main/resources/com/example/freshly/ProductsImages/product_id7Screenshot 2022-01-22 173033.png', '', '??????', '100', 'mamali'),
('????', '8', '97987', '0', '0', '???? ???', '', 'src/main/resources/com/example/freshly/ProductsImages/product_id8neshanLogoFooter.png', '', '???????', '100', 'mamali'),
('tokhme', '9', '987', '0', '0', 'tokhmy', '', 'src/main/resources/com/example/freshly/ProductsImages/product_id9etemadLogoFooter.png', '', '???????', '99', 'admin'),
('mame', '10', '8500000000000', '3.4', '5', 'mamei', '', 'src/main/resources/com/example/freshly/ProductsImages/product_id10logo.jpg', '', 'groceries', '100', 'admin'),
('noshabe', '20', '69000', '0', '0', 'cock', '', 'src/main/resources/com/example/freshly/ProductsImages/product_id20ICON.jpg', '', 'drinks', '100', 'admin'),
('nanehyeomar', '0', '1', '3.0', '3', 'jende', '', 'src/main/resources/com/example/freshly/ProductsImages/product_id0among_us_player_blue_icon_156941.png', '', 'other', '100', 'admin'),
('madarehomar', '21', '2', '1.9', '4', 'kiri', '', 'src/main/resources/com/example/freshly/ProductsImages/product_id21club (1).png', '', 'other', '100', 'admin'),
('kir', '22', '2333333', '3.9', '99', 'kirjaat', 'lasdasf', 'src/main/resources/com/example/freshly/ProductsImages/product_id22club (1) - Copy.png', 'fafafafafafafssfsfsas', 'drinks', '100', 'admin'),
('kiry', '23', '2333333', '5', '85', 'kirjaat', 'lasdasf', 'src/main/resources/com/example/freshly/ProductsImages/product_id22club (1) - Copy.png', 'fafafafafafafssfsfsas', 'drinks', '100', 'admin'),
('tokhm', '24', '5000000', '0', '0', 'tokhmy', '', 'src/main/resources/com/example/freshly/ProductsImages/product_id24sfs.PNG', '', 'proteins', '100', 'mamali1'),
('kirkhar', '25', '1', '0', '0', 'kir', '', 'src/main/resources/com/example/freshly/ProductsImages/product_id251.jpg', '', 'proteins', '50000', 'admin'),
('a', '26', '50', '0', '0', 'a', '', 'src/main/resources/com/example/freshly/ProductsImages/product_id261.jpg', '', 'groceries', '1', 'mamali'),
('a', '28', '123546879', '0', '0', 'a', '', 'src/main/resources/com/example/freshly/ProductsImages/product_id28Capture.PNG', '', 'drinks', '100', 'mamali1');

-- --------------------------------------------------------

--
-- Table structure for table `seller`
--

CREATE TABLE `seller` (
  `Username` varchar(50) NOT NULL,
  `Password` varchar(50) NOT NULL,
  `FirstName` varchar(50) NOT NULL,
  `LastName` varchar(50) NOT NULL,
  `PhoneNumber` varchar(50) NOT NULL,
  `EmailAddress` varchar(50) NOT NULL,
  `Company` varchar(50) NOT NULL,
  `varified` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `seller`
--

INSERT INTO `seller` (`Username`, `Password`, `FirstName`, `LastName`, `PhoneNumber`, `EmailAddress`, `Company`, `varified`) VALUES
('admin', 'admin123', 'admin', 'admin', '09153126548', 'a@gmail.com', 'mohammad', 'true'),
('mamali', 'admin123', 'admin', 'admin', '09152166330', 'saleh.govahi@gmail.com', 'brazzers', 'true'),
('mamali1', 'admin123', 'admin', 'admin', '013568593', 'f@gmail.com', 'mohammad', 'true'),
('ali', 'a', 'a', 'a', '1', 'a@gmail.com', 'mohammad', 'true');

-- --------------------------------------------------------

--
-- Table structure for table `store`
--

CREATE TABLE `store` (
  `name` text NOT NULL,
  `address` text NOT NULL,
  `manager` text NOT NULL,
  `products` text NOT NULL,
  `numberofproducts` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `store`
--

INSERT INTO `store` (`name`, `address`, `manager`, `products`, `numberofproducts`) VALUES
('a', 'a', 'a', '5@7@9@', '192'),
('a', 'a', 'a', '6@', '98'),
('b', 'a', 'a', '6@', '98');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
