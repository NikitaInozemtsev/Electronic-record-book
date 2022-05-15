import React, { Component } from 'react';
import ListComponent from './ListComponent';

class ProfessorList extends Component {

    render() {
        var tableHeaders = [
            'Фамилия преподавателя',
            'Имя',
            'Отчество',
            'Должность',
            'Имя кафедры'
        ];
        var tableFields = [
            'name',
            'surname',
            'patronymic',
            'post',
            'department.name'
        ];
        return (
            <ListComponent 
            tableHeaders={tableHeaders}
            tableFields={tableFields}
            title='Преподаватели'
            tooltipTitle='Create new Professor'
            entityName='professors'/>
        );
    }

}
export default ProfessorList;