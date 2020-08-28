-- USER --
DROP TABLE AS2_USER CASCADE CONSTRAINTS;
CREATE TABLE AS2_USER (
				  USER_ID        	NUMBER(6) GENERATED AS IDENTITY PRIMARY KEY ,
				  USERNAME          VARCHAR2(100) NOT NULL,
				  PASSWORD          VARCHAR2(2000) NOT NULL,
                  ROLE     			CHAR(1 BYTE) DEFAULT 'U',
                  CREATED_ON        TIMESTAMP(3)
			);
            
            
-- SCREENSHOT --
DROP TABLE AS2_SCREEN_SHOT CASCADE CONSTRAINTS;
CREATE TABLE AS2_SCREEN_SHOT (
				  SCREEN_SHOT_ID        NUMBER(6) GENERATED AS IDENTITY PRIMARY KEY ,
				  URL                   VARCHAR2(2000) NOT NULL,
				  SCREEN_SHOT_NAME      VARCHAR2(100) NOT NULL,
                  USER_ID         		NUMBER(6) NOT NULL,
                  CREATED_ON         	TIMESTAMP(3),
                  FOREIGN KEY ("USER_ID") REFERENCES "AS2_USER" ("USER_ID")
			);            

-- ACTIVITY_LOG --
DROP TABLE AS2_ACTIVITY_LOG CASCADE CONSTRAINTS; 
CREATE TABLE AS2_ACTIVITY_LOG (
				  ACTIVITY_LOG_ID               NUMBER(6) GENERATED AS IDENTITY PRIMARY KEY ,
				  LOG_TYPE     					CHAR(1 BYTE) NOT NULL,
				  USER_ID       				NUMBER(6) NOT NULL,
                  CREATED_ON                    TIMESTAMP(3),
                  FOREIGN KEY ("USER_ID") REFERENCES "AS2_USER" ("USER_ID")
                  ); 

COMMIT;         