-- Login with the roort and setupa database user 'tomcat' that will be used by the web application for database access

create user 'tomcat'@'localhost' identified by 'tomcat';

grant all privileges on qpsetter.* to 'tomcat'@'localhost';



------

CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL DEFAULT '',
  `country` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
);



CREATE TABLE `question` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `chapterNo` varchar(20) DEFAULT NULL,
  `bitNo` int(11) DEFAULT NULL,
  `quesitonFileId` int(40) DEFAULT NULL,
  `complexity` varchar(20) DEFAULT NULL,
  `questiontext` text,
  `optiona` text,
  `optionb` text,
  `optionc` text,
  `optiond` text,
  `correctAnswer` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

