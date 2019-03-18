create database treningsdagbok; /* Er ikke sikkert du trenger disse to linjene */
use treningsdagbok; /* Er ikke sikkert du trenger disse to linjene */

create table treningsøkt(
	øktid int not null,
    primary key (øktid),
    dato date,
    tidspunkt time,
    varighet time,
    form int,
    prestasjon int,
    check(form<11 ^ form>0)
);

create table øktnotat(
	øktid int not null,
    notatid int not null,
    notatinformasjon text,
    foreign key (øktid) references treningsøkt(øktid),
    primary key(øktid, notatid)
);

create table øvelse(
	øvelseID int not null,
	navn text,
    primary key(ovelseID)
);

create table apparat(
	apparatid int not null,
  apparatnavn text,
  apparatbrukbeskrivelse text
);

create table øvelseMedApparat(
	navn text,
    øvelseID int not null,
    antallkg int,
    antallSett int,
    apparatID int,
    foreign key (apparatid) references apparat(apparatid),
    foreign key (øvelseid) references øvelse(øvelseid),
    primary key (øvelseid)
);

create table øvelseUtenApparat(
	navn text,
    øvelseid int not null,
    tekstBeskrivelse text,
    foreign key (øvelseid) references øvelse(øvelseid),
    primary key (øvelseid)
);

create table treningsøktØvelse(
	øktid int not null,
    øvelseid int not null,
    foreign key (øktid) references treningsøkt(øktid),
    foreign key (øvelseid) references øvelse(øvelseid),
    primary key (øktid, øvelseid)
);

create table øvelsesGruppe(
	øvelsesgruppeid int not null,
    øvelsesgruppenavn text,
    primary key (øvelsesgruppeid)
);

create table element(
	øvelsesgruppeid int not null,
    element text not null,
    foreign key (øvelsesgruppeid) references øvelsesgruppe(øvelsesgruppeid),
    primary key (øvelsesgruppeid, element)
);

create table øvelsesGruppeØvelseRelasjon(
	øvelseid int not null,
    øvelsesgruppe int not null,
    foreign key (øvelseid) references øvelse(øvelseid),
    foreign key (øvelsesgruppeid) references øvelsesgruppe(øvelsesgruppeid),
	primary key (øvelseid, øvelsesgruppeid)
);