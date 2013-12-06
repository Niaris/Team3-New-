-- Deletes all the previous data from the existing tables

DELETE FROM Likes;
DELETE FROM Review;
DELETE FROM Location;
DELETE FROM UserProfile;
DELETE FROM Users;

--
-- Dumping data for table `Users` for testing purposes. This will ensure that there are registered
-- Users in the application whithout the need of runnig it with
-- two different mobiles/emulators.
--

ALTER TABLE Users AUTO_INCREMENT=1;
INSERT INTO `Users` (`User_Fname`, `User_EmailAddress`) VALUES
('Ellís Carvalho', 'ellisvcarvalho@gmail.com'),
('rufai ahmed', 'rufaiahmed5@gmail.com'),
('putaniaris', 'nono@hotmail.com'),
('Redwan Sulaimani', 'r.s.hamoh@googlemail.com'),
('Chara Ioannou', 'charaioannou.ci@gmail.com'),
('Maria Solomonidou', 'msolomonidou12@googlemail.com');

-- ---------------------------------------------------------------

--
-- Dumping data for table `UserProfile` for testing purposes. This will ensure that there are registered
-- User Profile  for each user registered above in the application whithout the need of runnig it with
-- two different mobiles/emulators.
--

ALTER TABLE UserProfile AUTO_INCREMENT=1;
INSERT INTO `UserProfile` (`User_Name`, `User_Interests`, `User_Google_Account`) VALUES
('ellisCarvalho', 'I like to travel and to listen to music.', 'ellisvcarvalho@gmail.com'),
('rufaiAhmed', 'I love football', 'rufaiahmed5@gmail.com'),
('putaniaris', 'I like technology and travelling', 'nono@hotmail.com'),
('redwanSulaimani', 'I like to go to parties and drink', 'r.s.hamoh@googlemail.com'),
('charaIoannou', 'I like to dance', 'charaioannou.ci@gmail.com'),
('mariaSolomonidou', 'I love to cook', 'msolomonidou12@googlemail.com');

-- ---------------------------------------------------------------

--
-- Dumping data for table `Location` for testing purposes. This will ensure that there are some already registered
-- locations in the application. This will show on the map if and only if you are near to them. If nothing is shown the applicaiton
-- will allow you to add your current location and also show suggested locations nearby.
--
ALTER TABLE Location AUTO_INCREMENT=1;
INSERT INTO `Location` (`Location_Name`, `Location_Latitude`, `Location_Longitude`, `Location_Address`) VALUES
('Boiler House', 50.86840000, -0.08565830, 'Boiler House Hill, University of Sussex, Brighton, The City of Brighton and Hove BN1, UK'),
('Falmer Bar', 50.86454700, -0.08894700, 'Falmer House, Falmer, Brighton'),
('Amex Stadium', 50.86182330, -0.08274000, 'Village Way, University of Brighton, Brighton, The City of Brighton and Hove BN1, UK'),
('East Slope Bar', 50.86943700, -0.08709500, 'University Of Sussex, Refectory Road, Falmer, Brighton'),
('Northfield Ln', 50.87201850, -0.09063700, 'Falmer'),
('Falmer Station', 50.862012, -0.087317, '3 Station Approach, Falmer, Brighton, The City of Brighton and Hove BN1 9SD, UK'),
('Chichester I', 50.865892, -0.087145, 'Brighton, The City of Brighton and Hove BN1 9RE, UK'),
('Brighton Pier', 50.819429, -0.136398, 'Madeira Drive, Brighton BN2 1TW, UK'),
('SeaLife', 50.819613, -0.135785, 'Marine Parade, Brighton, East Sussex BN2 1TB, UK'),
('Churchill Square', 50.823668, -0.145370, 'Russell Place, Brighton BN1 2RG, UK'),
('St Peters Church', 50.828493, -0.134851, 'York Place, Brighton BN1 4GU, UK');

-- ---------------------------------------------------------------
--
-- Dumping data for table `Review` for testing purposes. This will ensure that there are some reviews
-- in the application. Again, to access these reviews the locations added before have to be shown on the map
-- will show on the map, and they will show if and only if you are near to them.After adding a locaiton you will be able
-- to add a review.
-- !!IMPORTANT!! We pass Location_ID as 1 because the first Location_ID in the table Location will be 1 since AUTO_INCREMENT is used.
--

ALTER TABLE Review AUTO_INCREMENT=1;
INSERT INTO `Review` (`Review_Comment`, `Review_Rating`, `Review_ImagePath`, `Review_Date`, `Review_Time`, `Location_ID`, `User_Email`) VALUES
('Not a good place to go', 1, '', '2013-11-27', '12:57:42', 1, 'msolomonidou12@googlemail.com'),
('It is good, but should have some improvements.', 2, '', '2013-11-27', '12:57:42', 1, 'ellisvcarvalho@gmail.com'),
('I didnt like it.', 1, '', '2013-11-27', '12:57:42', 1, 'charaioannou.ci@gmail.com'),
('Love this place <3', 4, '', '2013-11-27', '12:57:42', 1, 'nono@hotmail.com'),
('I love this bar!', 5, '', '2013-11-27', '12:57:42', 4, 'msolomonidou12@googlemail.com'),
('Beer is very cheap. Go there!', 4, '', '2013-11-27', '12:57:42', 4, 'ellisvcarvalho@gmail.com'),
('Quite noisy if you live near it.', 2, '', '2013-11-27', '12:57:42', 4, 'charaioannou.ci@gmail.com'),
('Beautiful stadium.', 5, '', '2013-11-27', '12:57:42', 3, 'nono@hotmail.com'),
('A lot of traffic nearby when there is a match, but the stadium is very great!', 3, '', '2013-11-27', '12:58:25', 3, 'msolomonidou12@googlemail.com');

-- ---------------------------------------------------------------

--
-- Dumping data for table `Likes` for testing purposes. This will ensure that there are some Likes in the application that correspond to a particular Location.
-- !!IMPORTANT!! We pass Location_ID as 1 because the first Location_ID in the table Location will be 1 since AUTO_INCREMENT is used.
--

ALTER TABLE Likes AUTO_INCREMENT=1;
INSERT INTO `Likes` (`Likes`, `User_Email`, `Location_ID`) VALUES
(1, 'ellisvcarvalho@gmail.com', 1),
(1, 'msolomonidou12@googlemail.com', 9),
(1, 'ellisvcarvalho@gmail.com', 4),
(1, 'msolomonidou12@googlemail.com', 7),
(1, 'ellisvcarvalho@gmail.com', 5),
(1, 'msolomonidou12@googlemail.com', 6),
(1, 'ellisvcarvalho@gmail.com', 6),
(1, 'msolomonidou12@googlemail.com', 8)
(1, 'ellisvcarvalho@gmail.com', 7),
(1, 'msolomonidou12@googlemail.com', 4);