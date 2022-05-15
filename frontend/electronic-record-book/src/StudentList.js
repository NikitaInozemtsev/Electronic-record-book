import React, { Component } from 'react';
import ListComponent from './ListComponent';


class StudentList extends Component {

    render() {
        var tableHeaders = [
            'Фамилия студента',
            'Имя',
            'Отчество',
            'Дата рождения',
            'Группа',
            'Курс',
            'Специальность'
        ];
        var tableFields = [
            'surname',
            'name',
            'patronymic',
            'dateOfBirth',
            'group.name',
            'group.course',
            'group.specialty.name'
        ];
        return (
            <ListComponent 
            tableHeaders={tableHeaders}
            tableFields={tableFields}
            title='Студенты'
            tooltipTitle='Create new Student'
            entityName='students'/>
        );
    }

}
export default StudentList;