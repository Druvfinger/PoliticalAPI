DELIMITER //
create procedure addSubject(in name varchar(200),in source varchar(200),in partyId int, in dt TIMESTAMP)
begin
    insert into subjects (name, source, partyId, lastUpdated) values (name, source, partyId, dt);
end //
DELIMITER ;

DELIMITER //
create procedure addBulletPoint(in partyId int, in bulletPoint varchar(1000), in subjectId int, in dt TIMESTAMP)
begin
    insert into bulletpoints (partyId, bulletPoint, subjectId, lastUpdated) VALUES (partyId, bulletPoint, subjectId, dt);
end //
DELIMITER ;