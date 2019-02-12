 CREATE TABLE Bruker (
   BrukerNavn	  	VARCHAR(30) NOT NULL,
   Navn				VARCHAR(30),
   Passord			VARCHAR(30) NOT NULL,
   Rolle			VARCHAR(30),
   CONSTRAINT Bruker_PK PRIMARY KEY (BrukerNavn)); 

CREATE TABLE Emne (
   EmneID    		VARCHAR(10) NOT NULL,
   Navn 			VARCHAR(30),
   Faglærer			VARCHAR(30),
   CONSTRAINT Emne_PK PRIMARY KEY (EmneID),
   CONSTRAINT Faglærer_FK FOREIGN KEY (Faglærer) REFERENCES Bruker(BrukerNavn)
													ON UPDATE CASCADE
                                                    ON DELETE CASCADE);

CREATE TABLE Saltid (
   Dato    			INTEGER NOT NULL,
   Tidspunkt		INTEGER NOT NULL,
   EmneID			VARCHAR(10) NOT NULL,
   Varighet 		INTEGER,
   Faglærer			VARCHAR(30),
   CONSTRAINT Saltid_PK PRIMARY KEY (Dato, Tidspunkt, EmneID),
   CONSTRAINT FaglærerSaltid_FK FOREIGN KEY (Faglærer) REFERENCES Bruker(BrukerNavn)
													ON UPDATE CASCADE
                                                    ON DELETE CASCADE,
	CONSTRAINT EmneID_FK FOREIGN KEY (EmneID) REFERENCES Emne(EmneID)
													ON UPDATE CASCADE
                                                    ON DELETE CASCADE);
   

CREATE TABLE StudassPåSal (
   Dato    			INTEGER NOT NULL,
   Tidspunkt		INTEGER NOT NULL,   
   Studass			VARCHAR(30) NOT NULL,
   Varighet 		INTEGER,
   CONSTRAINT StudassPåSal_PK PRIMARY KEY (Dato, Tidspunkt, Studass),
   CONSTRAINT Studass_FK FOREIGN KEY (Studass) REFERENCES Bruker(BrukerNavn)
													ON UPDATE CASCADE
                                                    ON DELETE CASCADE);

CREATE TABLE Booking (
   BookingID		INTEGER NOT NULL,   
   Student			VARCHAR(30) NOT NULL,
   StudassPåSal		VARCHAR(30) NOT NULL,
   CONSTRAINT Booking_PK PRIMARY KEY (BookingID),
   CONSTRAINT Student_FK FOREIGN KEY (Student) REFERENCES Bruker(BrukerNavn)
													ON UPDATE CASCADE
                                                    ON DELETE CASCADE,
   CONSTRAINT StudassPåSal_FK FOREIGN KEY (StudassPåSal) REFERENCES StudassPåSal(Studass)
													ON UPDATE CASCADE
                                                    ON DELETE CASCADE);