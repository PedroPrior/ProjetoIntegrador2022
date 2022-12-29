select * from author;
select * from category;
select * from book;

insert  into Author values (uuid_generate_v4(), '03/01/1982', 'J. R. R. Tolkien');
insert  into Author values (uuid_generate_v4(), '20-08-1890', 'H.P Lovecraft');
insert  into Author values (uuid_generate_v4(), '05-12-1952', 'R.C Martin');
insert  into Author values (uuid_generate_v4(), '23-04-1564', 'Shakespeare');
insert  into Author values (uuid_generate_v4(), '31-07-1965', 'J.K Rowling');

insert  into category values (uuid_generate_v4(), 'Fantasy');
insert  into category values (uuid_generate_v4(), 'Rpg');
insert  into category values (uuid_generate_v4(),  'Computer Science');
insert  into category values (uuid_generate_v4(), 'Romance');
insert  into category values (uuid_generate_v4(), 'Sci-Fi');


insert into book values(uuid_generate_v4(), 'Nice book', 173746, 'Lord Of the Rings  I', 9.0, '3e2b5fc1-7e07-42a1-93a4-99f9be531826',
                        '493dba83-f84c-4ae1-afe0-4c6bb448028f');

insert into book values(uuid_generate_v4(), 'Nice book', 1373746, 'Lord Of the Rings  II', 9.2, '3e2b5fc1-7e07-42a1-93a4-99f9be531826',
                        '493dba83-f84c-4ae1-afe0-4c6bb448028f');


insert into book values(uuid_generate_v4(), 'Nice book', 1273741, 'Clean Code', 8.9, '276f55f2-057e-46f9-b766-5f2170fe9e86',
                        '0d7596d7-6716-4733-a8d6-66c742ec4c8d');


insert into book values(uuid_generate_v4(), 'Nice book', 1273141, 'Cthulhu', 8.5, '7637ab4b-344f-4649-8d63-cb2c4a42105e',
                        '65a0af58-7fb4-401e-9839-07c62233880f');


insert into book values(uuid_generate_v4(), 'Nice book', 1213141, 'Harry Potter', 8.1, '9feae641-6c9d-45bb-acd9-71dab16a6d6c',
                        '493dba83-f84c-4ae1-afe0-4c6bb448028f');

insert into book values(uuid_generate_v4(), 'Nice book', 1298939, 'Romeo and Juliet', 8.9, '605af176-78b0-4b4d-803b-2f093109a0e1',
                        'e903d158-189f-49ab-b8a7-e19f2e80b1ff');