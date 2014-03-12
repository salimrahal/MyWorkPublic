CREATE DATABASE GUESTBOOK;

USE GUESTBOOK;

CREATE TABLE GUESTBOOK (
  id int(11) NOT NULL auto_increment,
  Name varchar(255) NOT NULL default '',
  EntryDate datetime NOT NULL default '0000-00-00 00:00:00',
  Comment text NOT NULL,
  PRIMARY KEY  (id),
  KEY EntryDate (EntryDate)
) TYPE=MyISAM;

GRANT ALL ON GUESTBOOK.* to guestbook@localhost identified by 'foobar';
