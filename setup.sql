create table treningsokt(
                          oktid int not null AUTO_INCREMENT primary key,
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


/*
create table ovelsesGruppe(
                            ovelsesgruppeid int not null AUTO_INCREMENT,
                            ovelsesgruppenavn text,
                            primary key (ovelsesgruppeid)
);
*/

/*
create table element(
                      ovelsesgruppeid int not null,
                      element VARCHAR(200) not null,
                      foreign key (ovelsesgruppeid) references ovelsesgruppe(ovelsesgruppeid),
                      primary key (ovelsesgruppeid, element)
);
*/

/*
create table ovelsesGruppeOvelseRelasjon(
                                          ovelseid int not null,
                                          ovelsesgruppeid int not null,
                                          foreign key (ovelseid) references ovelse(ovelseid),
                                          foreign key (ovelsesgruppeid) references ovelsesgruppe(ovelsesgruppeid),
                                          primary key (ovelseid, ovelsesgruppeid)
);
*/

create table apparat(
                      apparatid int not null AUTO_INCREMENT primary key,
                      apparatnavn text,
                      apparatbrukbeskrivelse text
);

create table ovelse(
                     ovelseID int not null AUTO_INCREMENT primary key,
                     navn text,
                     antallkg int,
                     aparat boolean,
                     antallSett int,
                     apparatID int,
                     tekstBeskrivelse text,
                     foreign key (apparatid) references apparat(apparatid)
);


create table treningsoktOvelse(
                                oktid int not null,
                                ovelseid int not null,
                                foreign key (oktid) references treningsokt(oktid),
                                foreign key (ovelseid) references ovelse(ovelseid),
                                primary key (oktid, ovelseid)
);
