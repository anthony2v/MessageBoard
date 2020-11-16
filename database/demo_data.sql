DELETE FROM message WHERE id=1234;
DELETE FROM message WHERE id=135;
DELETE FROM message WHERE id='000';
DELETE FROM message WHERE id=08;
DELETE FROM message WHERE id=09;

INSERT INTO message
VALUES (1234,"Hello this is the demo for SOEN387 A2 ", '1996-11-01','2008-11-15', "Tiffany");

INSERT INTO message
VALUES (135,"You need to first login to Message Board with your username and password", '2008-01-02','2008-04-25', "Daniel");

INSERT INTO message
VALUES (08,"You can also attach files to your messages", '2022-11-17','2022-11-17', "Anthony");

INSERT INTO message
VALUES (09,"Will test the update function ", '2020-11-15','2020-11-15', "Tiffany");

UPDATE message
SET message_text='The text has been modified'
WHERE id= '09';

DELETE FROM message
WHERE id= '09';

SELECT id FROM message
WHERE id ='135';

