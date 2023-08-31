CREATE SEQUENCE public.ref_book_id_seq;


CREATE TABLE IF NOT EXISTS public.ref_books
(
    id bigint NOT NULL DEFAULT nextval('ref_book_id_seq'::regclass),
    ref_name text COLLATE pg_catalog."default",
    fields json,
    CONSTRAINT ref_books_pkey PRIMARY KEY (id)
);

--alter table ref_books alter column id set DEFAULT nextval('ref_book_id_seq'::regclass);

insert into ref_books(id, ref_name, fields)
values(1, 'Справочник1', '[{"LastName":"Овечкин", "FirstName":"Пётр","MiddleName":"Иванович", "age":30},
{"LastName":"Иванов", "FirstName":"Иван","MiddleName":"Иванович", "age":50}]');

insert into ref_books(id, ref_name, fields)
values(2, 'Справочник2', '{"LastName":"Овечкин", "FirstName":"Пётр","MiddleName":"Иванович", "age":30,"sex":"man","birthday":"2003-01-01"}');

commit;