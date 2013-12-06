--
-- Dumping data for table `Users` for testing purposes. This will ensure that there are registered
-- Users in the application whithout the need of runnig it with
-- two different mobiles/emulators.
--

INSERT INTO `Users` (`User_Fname`, `User_EmailAddress`) VALUES
('Ell�s Carvalho', 'ellisvcarvalho@gmail.com'),
('rufai ahmed', 'rufaiahmed5@gmail.com'),
('putaniaris', 'nono@hotmai.com'),
('Redwan Sulaimani', 'r.s.hamoh@googlemail.com'),
('Chara Ioannou', 'charaioannou.ci@gmail.com'),
('Maria Solomonidou', 'msolomonidou12@googlemail.com');

-- ---------------------------------------------------------------

--
-- Dumping data for table `Location` for testing purposes. This will ensure that there are some already registered
-- locations in the application. This will show on the map if and only if you are near to them. If nothing is shown the applicaiton
-- will allow you to add your current location and also show suggested locations nearby.
--

INSERT INTO `Location` (`Location_Name`, `Location_Latitude`, `Location_Longitude`, `Location_Address`) VALUES
('Boilder House', 50.86840000, -0.08565830, 'Boiler House Hill, University of Sussex, Brighton, The City of Brighton and Hove BN1, UK'),
('Iceland', 65.96669660, -18.53330000, 'Skíðabraut, Dalvik, Iceland'),
('TEST', 50.86539660, -0.08913490, 'Arts Road, University of Sussex, Brighton, The City of Brighton and Hove BN1, UK'),
('Grande Parade', 50.82502340, -0.13543990, '42 Grand Parade, Brighton, The City of Brighton and Hove BN2, UK');

-- ---------------------------------------------------------------
--
-- Dumping data for table `Review` for testing purposes. This will ensure that there are some reviews
-- in the application. Again, to access these reviews the locations added before have to be shown on the map
-- will show on the map, and they will show if and only if you are near to them.After adding a locaiton you will be able
-- to add a review.
-- !!IMPORTANT!! We pass Location_ID as 1 because the first Location_ID in the table Location will be 1 since AUTO_INCREMENT is used.
--

INSERT INTO `Review` (`Review_Comment`, `Review_Rating`, `Review_ImagePath`, `Review_Date`, `Review_Time`, `Location_ID`, `User_Email`) VALUES
('likes test', 3, '', '2013-11-27', '12:57:42', 1, 'msolomonidou12@googlemail.com'),
('Another likes tes', 3, '', '2013-11-27', '12:58:25', 1, 'ellisvcarvalho@gmail.com');

-- ---------------------------------------------------------------

--
-- Dumping data for table `UserProfile` for testing purposes. This will ensure that there are some User Profiles
-- in the application that correspond to some of the User. 
--

INSERT INTO `UserProfile` ( `User_Name`, `User_Interests`, `User_Google_Account`) VALUES
('LovevSmurfs ', 'I am drinking tea with the Smurfs every day!', 'msolomonidou12@googlemail.com');

-- ---------------------------------------------------------------

--
-- Dumping data for table `Likes` for testing purposes. This will ensure that there are some Likes in the application that correspond to a particular Location.
-- !!IMPORTANT!! We pass Location_ID as 1 because the first Location_ID in the table Location will be 1 since AUTO_INCREMENT is used.
--

INSERT INTO `Likes` (`Like_ID`, `Likes`, `User_Email`, `Location_ID`) VALUES
(1, 'ellisvcarvalho@gmail.com', 1),
(1, 'msolomonidou12@googlemail.com', 1);

