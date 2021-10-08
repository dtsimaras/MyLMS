------------------------------------------------
--  Tables 
------------------------------------------------ 

drop table libConfig cascade constraints;

create table libConfig 
(
	id 		            number(1)      not null,
	loanDays 	        number(2)      not null,
	loanQuantity        number(2)      not null,
	imageParentPath     varchar2(255)  not null
);

drop table libUser cascade constraints;

create table libUser 
(
	id 		  number(10)    not null,
	name	  varchar(50)	not null,
	lastname  varchar(50)   not null,
	email 	  varchar2(50)  not null,
	password  varchar2(255) not null,
	role      varchar2(20)  not null
);

drop table Author cascade constraints;

create table Author
(
	id		number(10)	 not null,
	name	varchar2(100) not null
);

drop table Book cascade constraints;

create table Book
(
	id 		  		 number(10)    not null,
	title 			 varchar2(200) not null,
	copies 			 number(3) 	   not null,
	available_copies number(3)	   not null,
	imagepath		 varchar2(255)	  
);

drop table Book_Author cascade constraints;

create table Book_Author
(
	book_id		number(10)	not null,
	author_id	number(10)	not null,
	arrangement	number(2)	not null
);

drop table Loan cascade constraints;

create table Loan
(
	id 		  		number(10) not null,
	user_id   		number(10) not null,
	book_id	  		number(10) not null,
	date_checkout   date  	   not null,
	date_due		date	   not null,
	date_checkin	date
	/*notes */
);

------------------------------------------------
--  Constraints 
------------------------------------------------ 

alter table libConfig       add constraint libconfig_id_PK          primary key ( id ) ;

alter table libuser    		add constraint user_id_PK				primary key ( id ) ;
alter table libuser    		add constraint user_email_UK			unique ( email ) ;

alter table book			add constraint book_id_PK				primary key ( id ) ;

alter table loan    		add constraint loan_id_PK				primary key ( id ) ;

alter table author    		add constraint author_id_PK				primary key ( id ) ;
alter table author    		add constraint author_name_UK			unique ( name ) ;

alter table book_author		add constraint book_author_PK			primary key (book_id, author_id) ;

----------- CK -----------

alter table libConfig       add constraint libconfig_id_CK          check ( id = 1 ) ;

alter table libuser			add constraint user_email_CK			check ( regexp_like ( email, '[[:alnum:]]+@[[:alnum:]]+\.[[:alnum:]]' ) ) ;
alter table libuser			add constraint user_role_CK				check ( role in ( 'admin', 'librarian', 'member' ) ) ;

----------- FK -----------

alter table loan		 	add constraint loan_user_id				foreign key ( user_id ) 		references libuser ( id ) ;
alter table loan		 	add constraint loan_book_id				foreign key ( book_id )		 	references book ( id ) ;

alter table book_author		add constraint book_author_bid			foreign key ( book_id )			references book ( id ) ;
alter table book_author		add constraint book_author_aid			foreign key ( author_id )		references author ( id ) ;

------------------------------------------------
--  Sequences
------------------------------------------------

drop sequence   user_id_SEQ ;
create sequence user_id_SEQ increment by 1 start with 1 minvalue 1 ;
drop sequence   author_id_SEQ ;
create sequence author_id_SEQ increment by 1 start with 1 minvalue 1 ;
drop sequence   book_id_SEQ ;
create sequence book_id_SEQ increment by 1 start with 1 minvalue 1 ;
drop sequence   loan_id_SEQ ;
create sequence loan_id_SEQ increment by 1 start with 1 minvalue 1 ;

------------------------------------------------
--  Triggers
------------------------------------------------

-- User
create or replace trigger user_id_TRIG
	before insert on libuser
	for each row

begin
if :NEW.id is null or :NEW.id < 1 then

	select user_id_SEQ.NEXTVAL
		into :NEW.id

		from dual;
end if;
end user_id_TRIG ;
/
-- Author
create or replace trigger author_id_TRIG
	before insert on author
	for each row

begin
if :NEW.id is null or :NEW.id < 1 then

	select author_id_SEQ.NEXTVAL
		into :NEW.id

		from dual;
end if;
end author_id_TRIG ;
/
-- Book
create or replace trigger book_id_TRIG
	before insert on book
	for each row

begin
if :NEW.id is null or :NEW.id < 1 then

	select book_id_SEQ.NEXTVAL
		into :NEW.id

		from dual;
end if;
end book_id_TRIG ;
/
-- Loan
create or replace trigger loan_id_TRIG
	before insert on loan
	for each row

begin
if :NEW.id is null or :NEW.id < 1 then

	select loan_id_SEQ.NEXTVAL
		into :NEW.id

		from dual;
end if;
end loan_id_TRIG ;
/

------------------------------------------------
--  Demo Data
------------------------------------------------
insert into libConfig (id, loanDays, loanQuantity, imageParentPath)
    values (1, 14, 5, '/images/');

insert into libuser (name, lastname, email, password, role) values ('Dimitris', 'Tsimaras', 'a@gmail.com', '99DDE1D35D1FDD283924D84E6D9F1D82061868F0C7A5478F608B60DC2353FC17', 'admin');
insert into libuser (name, lastname, email, password, role) values ('Marios', 'Mantakas', 'b@gmail.com', '99DDE1D35D1FDD283924D84E6D9F1D82061868F0C7A5478F608B60DC2353FC17', 'librarian');
insert into libuser (name, lastname, email, password, role) values ('Giannis','Giannakis', 'c@gmail.com', '99DDE1D35D1FDD283924D84E6D9F1D82061868F0C7A5478F608B60DC2353FC17', 'librarian');
insert into libuser (name, lastname, email, password, role) values ('Giannis', 'Tsimaras', 'd@gmail.com', '99DDE1D35D1FDD283924D84E6D9F1D82061868F0C7A5478F608B60DC2353FC17', 'member');
insert into libuser (name, lastname, email, password, role) values ('Anna', 'Go','e@gmail.com', '99DDE1D35D1FDD283924D84E6D9F1D82061868F0C7A5478F608B60DC2353FC17', 'member');
insert into libuser (name, lastname, email, password, role) values ('Arthur', 'Loely', 'f@gmail.com', '99DDE1D35D1FDD283924D84E6D9F1D82061868F0C7A5478F608B60DC2353FC17', 'member');

insert into book (title, copies, available_copies) values ('Harry Potter 1', 3, 0);
insert into book (title, copies, available_copies) values ('Harry Potter 2', 5, 1);
insert into book (title, copies, available_copies) values ('Harry Potter 3', 1, 0);
insert into book (title, copies, available_copies) values ('Harry Potter 4', 4, 3);
insert into book (title, copies, available_copies) values ('Harry Potter 5', 1, 1);
insert into book (title, copies, available_copies) values ('Harry Potter 6', 11, 10);
insert into book (title, copies, available_copies) values ('Harry Potter 7', 20, 20);

insert into loan (user_id, book_id, date_checkout, date_due, date_checkin) 
	values (1,1, to_date('2020/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),to_date('2020/05/11', 'yyyy/mm/dd'), to_date('2020/05/10 21:02:44', 'yyyy/mm/dd hh24:mi:ss'));
insert into loan (user_id, book_id, date_checkout, date_due, date_checkin) 
	values (2,2, to_date('2020/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),to_date('2020/05/11', 'yyyy/mm/dd'), to_date('2020/05/10 21:02:44', 'yyyy/mm/dd hh24:mi:ss'));
insert into loan (user_id, book_id, date_checkout, date_due, date_checkin) 
	values (2,2, to_date('2020/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),to_date('2020/05/11', 'yyyy/mm/dd'), to_date('2020/05/10 21:02:44', 'yyyy/mm/dd hh24:mi:ss'));
insert into loan (user_id, book_id, date_checkout, date_due, date_checkin) 
	values (5,4, to_date('2020/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),to_date('2020/05/11', 'yyyy/mm/dd'), to_date('2020/05/10 21:02:44', 'yyyy/mm/dd hh24:mi:ss'));
insert into loan (user_id, book_id, date_checkout, date_due) 
	values (1,6, to_date('2021/07/23 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),to_date('2021/08/30', 'yyyy/mm/dd'));
insert into loan (user_id, book_id, date_checkout, date_due) 
	values (1,2, to_date('2021/07/24 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),to_date('2021/11/15', 'yyyy/mm/dd'));
insert into loan (user_id, book_id, date_checkout, date_due) 
	values (6,2, to_date('2021/07/10 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),to_date('2021/11/17', 'yyyy/mm/dd'));
insert into loan (user_id, book_id, date_checkout, date_due) 
	values (1,1, to_date('2021/07/17 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),to_date('2021/11/24', 'yyyy/mm/dd'));
insert into loan (user_id, book_id, date_checkout, date_due) 
	values (6,1, to_date('2021/07/17 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),to_date('2021/07/30', 'yyyy/mm/dd'));
insert into loan (user_id, book_id, date_checkout, date_due) 
	values (6,3, to_date('2021/07/17 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),to_date('2021/07/30', 'yyyy/mm/dd'));


insert into book (title, copies, available_copies) values ('Ptuxiaki Java EE', 2, 2);
insert into author (name) values ('D.Tsimaras');
insert into author (name) values ('M.Mantakas');
insert into author (name) values ('hard work');

insert into author (name) values ('J.K.Rowling');

insert into book_author (book_id, author_id, arrangement) values (8, 1, 1);
insert into book_author (book_id, author_id, arrangement) values (8, 2, 1);
insert into book_author (book_id, author_id, arrangement) values (8, 3, 1);

insert into book_author (book_id, author_id, arrangement) values (1, 4, 1);
insert into book_author (book_id, author_id, arrangement) values (2, 4, 1);
insert into book_author (book_id, author_id, arrangement) values (3, 4, 1);
insert into book_author (book_id, author_id, arrangement) values (4, 4, 1);
insert into book_author (book_id, author_id, arrangement) values (5, 4, 1);
insert into book_author (book_id, author_id, arrangement) values (6, 4, 1);
insert into book_author (book_id, author_id, arrangement) values (6, 1, 2);
insert into book_author (book_id, author_id, arrangement) values (7, 1, 1);
insert into book_author (book_id, author_id, arrangement) values (7, 4, 2);

commit;