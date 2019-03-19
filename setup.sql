create database treningsdagbok; /* Er ikke sikkert du trenger disse to linjene */
use treningsdagbok; /* Er ikke sikkert du trenger disse to linjene */

create table treningsokt(
	oktid int not null,
  primary key (oktid),
  dato date,
  tidspunkt time,
  varighet time,
  form int,
  prestasjon int
  /* TODO: Feil her -> check (form >= 1 and form <= 11)*/
);

create table oktnotat(
	oktid int not null,
  notatid int not null,
  notatinformasjon text,
  foreign key (oktid) references treningsokt(oktid),
  primary key(oktid, notatid)
);

create table ovelse(
	ovelseID int not null,
	navn text,
  primary key(ovelseID)
);

create table apparat(
	apparatid int not null,
  apparatnavn text,
  apparatbrukbeskrivelse text,
  primary key (apparatid)
);

create table ovelseMedApparat(
	navn text,
    ovelseID int not null,
    antallkg int,
    antallSett int,
    apparatID int not null,
    foreign key (apparatid) references apparat(apparatid),
    foreign key (ovelseid) references ovelse(ovelseid),
    primary key (ovelseid)
);

create table ovelseUtenApparat(
	navn text,
  ovelseid int not null,
  tekstBeskrivelse text,
  foreign key (ovelseid) references ovelse(ovelseid),
  primary key (ovelseid)
);

create table treningsoktOvelse(
	oktid int not null,
  ovelseid int not null,
  foreign key (oktid) references treningsokt(oktid),
  foreign key (ovelseid) references ovelse(ovelseid),
  primary key (oktid, ovelseid)
);

create table ovelsesGruppe(
	ovelsesgruppeid int not null,
  ovelsesgruppenavn text,
  primary key (ovelsesgruppeid)
);

create table element(
	ovelsesgruppeid int not null,
  element VARCHAR(200) not null,
  foreign key (ovelsesgruppeid) references ovelsesgruppe(ovelsesgruppeid),
  primary key (ovelsesgruppeid, element)
);

create table ovelsesGruppeOvelseRelasjon(
	ovelseid int not null,
  ovelsesgruppeid int not null,
  foreign key (ovelseid) references ovelse(ovelseid),
  foreign key (ovelsesgruppeid) references ovelsesgruppe(ovelsesgruppeid),
	primary key (ovelseid, ovelsesgruppeid)
);