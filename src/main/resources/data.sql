insert into db.public.form_of_controls (name) values ('Экзамен')
, ('Зачет')
, ('Курсовая работа')
, ('Практика')
, ('Научно-исследовательская работа')
, ('Выпускная квалификационная работа');

insert into db.public.departments (head_of_the_department, name, phone_number) values
('Колобов Пантелей Серапионович', 'Какая-то кафедра', '7(505)120-76-21');

insert into db.public.disciplines (name, department_id) values
('Какой-то предмет', 1);

insert into db.public.specialties (name, price, department_id) values
('Какая-то специальность', 200000, 1);

insert into db.public.groups (course, name, specialty_id) values
(2, 'Какая-то группа', 1);

insert into db.public.students (date_of_birth, surname, name, patronymic, group_id) values
(to_date('08.12.2000', 'dd.MM.yyyy'), 'Лихачев', 'Адам', 'Ефимович', 1);

insert into db.public.professors (surname, name, patronymic, post, department_id) values
('Кабанов', 'Тимур', 'Тимурович', 'Какая-то должность', 1);

insert into db.public.achievements (date, mark, semester, discipline_id, form_of_control_id, professor_id, student_id) values
(to_date('24.01.2021', 'dd.MM.yyyy'), 'Хорошо', 3, 1, 1, 1, 1),
(to_date('18.01.2021', 'dd.MM.yyyy'), 'Отлично', 3, 1, 1, 1, 1);