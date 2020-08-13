INSERT INTO USER(USER_ID,ID,PSWD1,NAME,YY,MM,DD,GENDER,EMAIL,ADRESS,MOBILE) VALUES (1,'dofany','0516','김도환','1995','07','02','남자','rlaehghks33','@naver.com','01047240694');
INSERT INTO USER(USER_ID,ID,PSWD1,NAME,YY,MM,DD,GENDER,EMAIL,ADRESS,MOBILE) VALUES (2,'jjung2sister','1234','최효정','1994','07','28','여자','jjung2','@naver.com','01097380694');
INSERT INTO QUESTION(user_Id,writer_user_Id,title,contents,create_date) VALUES(1,1,'효정이누나','사랑해요',CURRENT_TIMESTAMP());
INSERT INTO QUESTION(user_Id,writer_user_Id,title,contents,create_date) VALUES(2,2,'도환아','나도 사랑해',CURRENT_TIMESTAMP());