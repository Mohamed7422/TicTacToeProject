/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  muhamed
 * Created: Sep 7, 2023
 */
-- Instructions for using this SQL script:
-- 1. Open a database management tool (include Apachi Derby to your project).
-- 2. Connect to your Derby database.
-- 3. Execute this SQL script to create the Player and Game tables.
--    You can run the entire script at once, or copy and paste each section separately.
-- 4. After running the script, the tables and their relationships will be set up.


-- Create the Player table
CREATE TABLE Player (
    PlayerId INT GENERATED ALWAYS AS IDENTITY,
    UserName VARCHAR(50) NOT NULL UNIQUE,
    Password VARCHAR(50) NOT NULL,
    Score INT DEFAULT 0,
    Status VARCHAR(50) NOT NULL DEFAULT 'Online', -- Default status is 'Online'
    PRIMARY KEY (PlayerId)
);

-- Create the Game table
CREATE TABLE Game (
    Id INT GENERATED ALWAYS AS IDENTITY,
    PlayerName VARCHAR(50) NOT NULL,
    OpponentName VARCHAR(50) NOT NULL,
    DateTime DATE NOT NULL,
    GameResult VARCHAR(50) NOT NULL,
    IsRecorded BOOLEAN DEFAULT false,
    CONSTRAINT gamePK PRIMARY KEY (Id),
    CONSTRAINT playerNameFK FOREIGN KEY (PlayerName) REFERENCES Player(UserName),
    CONSTRAINT OpponentNameFK FOREIGN KEY (OpponentName) REFERENCES Player(UserName),
);

