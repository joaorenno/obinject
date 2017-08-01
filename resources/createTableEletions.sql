-- --------------------------------------------------------

--
-- Estrutura para tabela `Campaign`
--

CREATE TABLE IF NOT EXISTS `campaigns` (
  `register` int(11) NOT NULL,
  `patrimony` double NOT NULL,
  `spending` double NOT NULL,
  `candidate_identityCard` int(11) DEFAULT NULL,
  `election_yearElection` int(11) DEFAULT NULL,
  `party_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`register`),
  KEY `FKFB836530484E822B` (`candidate_identityCard`),
  KEY `FKFB8365309CF86F77` (`election_yearElection`),
  KEY `FKFB836530B79780C8` (`party_name`)
) DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura para tabela `Campaign_Person`
--

CREATE TABLE IF NOT EXISTS `campaign_persons` (
  `Campaign_register` int(11) NOT NULL,
  `campaigners_identityCard` int(11) NOT NULL,
  KEY `FK1BC1E96415D482F6` (`campaigners_identityCard`),
  KEY `FK1BC1E9642ECFD7A4` (`Campaign_register`)
) DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura para tabela `Counting`
--

CREATE TABLE IF NOT EXISTS `countings` (
  `register` int(11) NOT NULL,
  `total` int(11) NOT NULL,
  `candidate_identityCard` int(11) DEFAULT NULL,
  `election_yearElection` int(11) DEFAULT NULL,
  PRIMARY KEY (`register`),
  KEY `FKEDADDC93484E822B` (`candidate_identityCard`),
  KEY `FKEDADDC939CF86F77` (`election_yearElection`)
) DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura para tabela `Election`
--

CREATE TABLE IF NOT EXISTS `elections` (
  `yearElection` int(11) NOT NULL,
  `monitored` bit(1) NOT NULL,
  PRIMARY KEY (`yearElection`)
) DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura para tabela `Party`
--

CREATE TABLE IF NOT EXISTS `parties` (
  `name` varchar(255) NOT NULL,
  `office` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`name`)
) DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura para tabela `Person`
--

CREATE TABLE IF NOT EXISTS `people` (
  `DTYPE` varchar(31) NOT NULL,
  `identityCard` int(11) NOT NULL,
  `adress` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `assignment` varchar(255) DEFAULT NULL,
  `income` float DEFAULT NULL,
  `candidateName` varchar(255) DEFAULT NULL,
  `candidateState` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `race` varchar(255) DEFAULT NULL,
  `isSpeaker` bit(1) DEFAULT NULL,
  `prefixName` varchar(255) DEFAULT NULL,
  `mandate` int(11) DEFAULT NULL,
  PRIMARY KEY (`identityCard`)
) DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura para tabela `Vote`
--

CREATE TABLE IF NOT EXISTS `votes` (
  `register` int(11) NOT NULL,
  `instante` date DEFAULT NULL,
  `election_yearElection` int(11) DEFAULT NULL,
  `elector_identityCard` int(11) DEFAULT NULL,
  `representative_identityCard` int(11) DEFAULT NULL,
  `senator_identityCard` int(11) DEFAULT NULL,
  PRIMARY KEY (`register`),
  KEY `FK28C70AE36D60B` (`senator_identityCard`),
  KEY `FK28C70A9CF86F77` (`election_yearElection`),
  KEY `FK28C70AF52111EF` (`representative_identityCard`),
  KEY `FK28C70AE215560B` (`elector_identityCard`)
) DEFAULT CHARSET=latin1;
