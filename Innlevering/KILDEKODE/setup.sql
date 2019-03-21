CREATE TABLE `apparat` (
  `apparatid` int(11) NOT NULL,
  `apparatnavn` text COLLATE utf8_unicode_ci,
  `apparatbrukbeskrivelse` text COLLATE utf8_unicode_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `ovelse` (
  `ovelseID` int(11) NOT NULL,
  `navn` text COLLATE utf8_unicode_ci,
  `antallkg` int(11) DEFAULT NULL,
  `aparat` tinyint(1) DEFAULT NULL,
  `antallSett` int(11) DEFAULT NULL,
  `apparatID` int(11) DEFAULT NULL,
  `tekstBeskrivelse` text COLLATE utf8_unicode_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `treningsokt` (
  `oktid` int(11) NOT NULL,
  `dato` date DEFAULT NULL,
  `tidspunkt` time DEFAULT NULL,
  `varighet` time DEFAULT NULL,
  `form` int(11) DEFAULT NULL,
  `prestasjon` int(11) DEFAULT NULL,
  `oktnotat` text COLLATE utf8_unicode_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `treningsoktOvelse` (
  `oktid` int(11) NOT NULL,
  `ovelseid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

ALTER TABLE `apparat`
  ADD PRIMARY KEY (`apparatid`);

ALTER TABLE `ovelse`
  ADD PRIMARY KEY (`ovelseID`),
  ADD KEY `apparatID` (`apparatID`);

ALTER TABLE `treningsokt`
  ADD PRIMARY KEY (`oktid`);

ALTER TABLE `treningsoktOvelse`
  ADD PRIMARY KEY (`oktid`,`ovelseid`),
  ADD KEY `ovelseid` (`ovelseid`);

ALTER TABLE `apparat`
  MODIFY `apparatid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

ALTER TABLE `ovelse`
  MODIFY `ovelseID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

ALTER TABLE `treningsokt`
  MODIFY `oktid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

ALTER TABLE `ovelse`
  ADD CONSTRAINT `ovelse_ibfk_1` FOREIGN KEY (`apparatID`) REFERENCES `apparat` (`apparatid`);

ALTER TABLE `treningsoktOvelse`
  ADD CONSTRAINT `treningsoktOvelse_ibfk_1` FOREIGN KEY (`oktid`) REFERENCES `treningsokt` (`oktid`),
  ADD CONSTRAINT `treningsoktOvelse_ibfk_2` FOREIGN KEY (`ovelseid`) REFERENCES `ovelse` (`ovelseid`);
COMMIT;