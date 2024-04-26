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

create table bpChangeLog
(
    Id int not null auto_increment primary key,
    changeTypeId int not null,
    descr varchar(50),
    bpId int not null,
    changeTime TIMESTAMP not null,
    foreign key (changeTypeId) references changeType(Id)
);

create table subjectChangeLog
(
    Id int not null auto_increment primary key,
    changeTypeId int not null,
    descr varchar(50),
    subjectId int not null,
    changeTime TIMESTAMP not null,
    foreign key (changeTypeId) references changeType(Id)
);

create table changeType
(
    Id int not null auto_increment primary key,
    changeType varchar(10) not null
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

insert into changeType(changeType)
values ('INSERTED'), ('UPDATED'), ('DELETED')


create view vv_BP AS
select BP.Id BPID, BP.bulletPoint BP, BP.lastUpdated LastUpdated,P.name Party, P.Id PID, S.name Subject, S.id SID, S.source Source from bulletpoints BP
inner join parties P on BP.partyId = P.Id
inner join subjects S on BP.subjectId = S.Id;

create view vv_bpChangeLog AS
select CL.Id , CL.descr , CL.bpId , CL.changeTypeId, CL.changeTime, CT.changeType from bpChangeLog CL
inner join changeType CT on CL.changeTypeId = CT.Id

create view vv_subjectChangeLog AS
select CL.Id , CL.descr , CL.subjectId , CL.changeTypeId, CL.changeTime, CT.changeType from subjectChangeLog CL
inner join changeType cT on CT.Id = CL.changeTypeId