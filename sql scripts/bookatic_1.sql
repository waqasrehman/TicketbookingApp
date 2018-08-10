-- phpMyAdmin SQL Dump
-- version 4.0.10.7
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: May 05, 2016 at 02:45 PM
-- Server version: 5.5.48-cll
-- PHP Version: 5.4.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `bookatic_1`
--

-- --------------------------------------------------------

--
-- Table structure for table `movies`
--

CREATE TABLE IF NOT EXISTS `movies` (
  `MovieID` int(11) NOT NULL,
  `Title` varchar(50) NOT NULL,
  `Image` varchar(150) NOT NULL,
  `Description` text,
  `Genre` text,
  `rating` double NOT NULL,
  `Price` varchar(50) NOT NULL,
  `Venue` varchar(50) NOT NULL,
  `Available` int(11) NOT NULL,
  `year` int(11) DEFAULT NULL,
  PRIMARY KEY (`MovieID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `movies`
--

INSERT INTO `movies` (`MovieID`, `Title`, `Image`, `Description`, `Genre`, `rating`, `Price`, `Venue`, `Available`, `year`) VALUES
(1, 'Avengers', 'http://wallpoper.com/images/00/00/33/07/avengers_00003307.jpg', 'When Thor''s evil brother, Loki (Tom Hiddleston), gains access to the unlimited power of the energy cube called the Tesseract, Nick Fury (Samuel L. Jackson), director of S.H.I.E.L.D., initiates a superhero recruitment effort to defeat the unprecedented threat to Earth', 'Sci-fi', 8.1, '10', 'Showcase', 1, 2012),
(2, 'Jhon Wick', 'http://www.ruthlessreviews.com/wp-content/uploads/2015/01/john_wick_poster.jpg', 'Legendary assassin John Wick (Keanu Reeves) retired from his violent career after marrying the love of his life. Her sudden death leaves John in deep mourning. When sadistic mobster Iosef Tarasov (Alfie Allen) and his thugs steal John''s prized car and kill the puppy that was a last gift from his wife, John unleashes the remorseless killing machine within and seeks vengeance. Meanwhile, Iosef''s father (Michael Nyqvist) -- John''s former colleague -- puts a huge bounty on John''s head.', 'Thriller', 9.2, '10', 'Odeom', 23, 2014),
(3, 'Minions', 'https://s-media-cache-ak0.pinimg.com/736x/4c/ff/89/4cff899a2c6abc13f5ad8c462172d728.jpg', 'Evolving from single-celled yellow organisms at the dawn of time, Minions live to serve, but find themselves working for a continual series of unsuccessful masters, from T. Rex to Napoleon. Without a master to grovel for, the Minions fall into a deep depression. But one minion, Kevin, has a plan; accompanied by his pals Stuart and Bob, Kevin sets forth to find a new evil boss for his brethren to follow. Their search leads them to Scarlet Overkill, the world''s first-ever super-villainess.', 'Animation', 7, '5', 'Showcase', 10, 2015),
(5, 'Avatar', 'http://www.brucelittlefield.com/wp-content/uploads/2010/01/New-Avatar-Poster-Featuring-Zoe-Saldanas-Navi-300x400.jpg', 'On the lush alien world of Pandora live the Na''vi, beings who appear primitive but are highly evolved. Because the planet''s environment is poisonous, human/Na''vi hybrids, called Avatars, must link to human minds to allow for free movement on Pandora. Jake Sully (Sam Worthington), a paralyzed former Marine, becomes mobile again through one such Avatar and falls in love with a Na''vi woman (Zoe Saldana). As a bond with her grows, he is drawn into a battle for the survival of her world.', 'Sci-fi', 9, '5', 'Showcase', 15, 2009),
(6, 'Ride Alone 2', 'http://selectvideo.ca/posters/5775/medium/ride_along_2_br_combo.jpg?1459629855', 'Rookie lawman Ben Barber (Kevin Hart) aspires to become a detective like James Payton (Ice Cube), his future brother-in-law. James reluctantly takes Ben to Miami to follow up on a lead that''s connected to a drug ring.', 'Comedy', 7.1, '10', 'Odeom ', 13, 2016),
(7, 'Mad Max', 'https://wallpaperscraft.com/image/mad_max_mad_max_fury_road_skull_mask_iron_102362_240x320.jpg', 'Years after the collapse of civilization, the tyrannical Immortan Joe enslaves apocalypse survivors inside the desert fortress the Citadel. When the warrior Imperator Furiosa (Charlize Theron) leads the despot''s five wives in a daring escape, she forges an alliance with Max Rockatansky (Tom Hardy), a loner and former captive. Fortified in the massive, armored truck the War Rig, they try to outrun the ruthless warlord and his henchmen in a deadly high-speed chase through the Wasteland.', 'Fantasy/Sci-fi', 8.2, '5', 'Show Case', 19, 2015),
(8, 'Star Wars', 'http://www.artsfon.com/pic/201512/240x320/artsfon.com-79086.jpg', 'Thirty years after the defeat of the Galactic Empire, the galaxy faces a new threat from the evil Kylo Ren (Adam Driver) and the First Order. When a defector named Finn crash-lands on a desert planet, he meets Rey (Daisy Ridley), a tough scavenger whose droid contains a top-secret map. Together, the young duo joins forces with Han Solo (Harrison Ford) to make sure the Resistance receives the intelligence concerning the whereabouts of Luke Skywalker (Mark Hamill), the last of the Jedi Knights.. ', 'Fantasy/Sci-fi', 8.5, '13', 'Show Case', 49, 2015),
(10, 'the Revenant', 'http://hdqwalls.com/download/the-revenant-movie-2016-240x320.jpg', 'While exploring the uncharted wilderness in 1823, frontiersman Hugh Glass (Leonardo DiCaprio) sustains life-threatening injuries from a brutal bear attack. When a member (Tom Hardy) of his hunting team kills his young son (Forrest Goodluck) and leaves him for dead, Glass must utilize his survival skills to find a way back to civilization. Grief-stricken and fueled by vengeance, the legendary fur trapper treks through the snowy terrain to track down the man who betrayed him.', 'Drama film/Thriller', 7.8, '12', 'Odeom ', 13, 2015),
(11, 'Hitman', 'http://images.coolestwallpapers.com/user-content/uploads/wall/240x320/26/hitman_agent_47_2015_movie-wide.jpg', 'Genetically engineered from conception to be the perfect killing machine, he''s the culmination of decades of research, endowed with unprecedented strength, speed, stamina and intelligence. Known only as Agent 47 (Rupert Friend), his latest target is a corporation that plans to unlock the secret of his past to create an army of killers even more powerful than him. With help from a young woman, the elite assassin confronts revelations about his own origins in an epic battle with his deadliest foe.', 'Action', 6.1, '8', 'Odeom', 3, 2015),
(13, 'Zootopia', 'https://wallpaperscraft.com/image/zootopia_disney_nick_wilde_judy_hopps_107871_240x320.jpg', 'From the largest elephant to the smallest shrew, the city of Zootopia is a mammal metropolis where various animals live and thrive. When Judy Hopps (Ginnifer Goodwin) becomes the first rabbit to join the police force, she quickly learns how tough it is to enforce the law.', 'Action', 8.3, '10', 'Showcase', 10, 2016);

-- --------------------------------------------------------

--
-- Table structure for table `purchase`
--

CREATE TABLE IF NOT EXISTS `purchase` (
  `username` text,
  `title` text,
  `venue` text,
  `price` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `purchase`
--

INSERT INTO `purchase` (`username`, `title`, `venue`, `price`) VALUES
('zkd1', 'bastille day', 'ODEON', 7),
('wur1', 'Fury', 'Odeom', 8),
('wur1', 'Jhon Wick', 'Odeom', 10),
('tom', 'Hitman', 'Odeom', 8),
('kirith', 'Ride Alone 2', 'Odeom ', 10),
('erfan', 'Minions', 'Showcase', 5),
('wur1', 'Jhon Wick', 'Odeom', 10),
('wur1', 'Jhon Wick', 'Odeom', 10),
('wur1', 'Avengers', 'Showcase', 10),
('mfl', 'Mad Max', 'Show Case', 5);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` text NOT NULL,
  `email` text NOT NULL,
  `password` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=26 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `email`, `password`) VALUES
(15, 'erfan', '', ''),
(16, 'kirith', '', ''),
(17, 'tom', '', ''),
(21, 'wur1', ' ', ''),
(22, 'Br', '', ''),
(23, 'waqas', ' ', ''),
(24, 'zkd2', '', ''),
(25, 'mfl', '', '');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
