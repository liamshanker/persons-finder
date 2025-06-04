MERGE INTO PERSON (ID, NAME) VALUES
(1, 'Alice'),
(2, 'Bob'),
(3, 'Charlie'),
(4, 'Diana'),
(5, 'Eve'),
(6, 'Frank'),
(7, 'Grace'),
(8, 'Hannah'),
(9, 'Ian'),
(10, 'Jack');

MERGE INTO LOCATION (REFERENCE_ID, LONGITUDE, LATITUDE) VALUES
(1, 174.7632, -36.8521),  -- Auckland
(2, 176.2529, -38.1381),   -- Rotorua
(3, 176.0695, -38.6866),    -- Taupo
(4, 175.26837, -37.7749),   -- Hamilton
(5, 176.9178, -39.4902),     -- Napier
(6, 174.7772, -41.2888),    -- Wellington
(7, 173.2837, -41.2711),  -- Nelson
(8, 172.6364, -43.5310),   -- Christchurch
(9, 170.5036, -45.8741),  -- Dunedin
(10, 168.6610, -45.0322);   -- Queenstown
