create database politicaldatatest;
use politicaldatatest;

create table parties
(
    Id int not null auto_increment primary key,
    name varchar(100) not null,
    abbreviation varchar(2) not null
);

create table subjects
(
    Id int not null auto_increment primary key,
    name varchar(200) not null,
    source varchar(200) not null,
    partyId int not null,
    lastUpdated varchar(250) not null,
    foreign key (partyId) references parties(Id)
);

create table bulletPoints
(
    Id int not null auto_increment primary key,
    partyId int,
    bulletPoint varchar(8000),
    subjectId int,
    lastUpdated varchar(250) not null,
    foreign key (partyId) references parties(Id),
    foreign key (subjectId) references subjects(Id)
);

insert into parties(name, abbreviation)
values ('SVERIGEDEMOKRATERNA', 'SD'),
       ('SOCIALDEMOKRATERNA','S'),
       ('MILJOPARTIET','MP'),
       ('VANSTERPARTIET', 'V'),
       ('CENTERPARTIET', 'C'),
       ('LIBERALERNA','L'),
       ('KRISTDEMOKRATERNA','KD'),
       ('MODERATERNA','M');

create view vv_BP AS
select BP.Id BPID, BP.bulletPoint BP, BP.lastUpdated LastUpdated,P.name Party, P.Id PID, S.name Subject, S.id SID, S.source Source from bulletpoints BP
inner join parties P on BP.partyId = P.Id
inner join subjects S on BP.subjectId = S.Id;