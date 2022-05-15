import React, { Component } from 'react';
import ListComponent from './ListComponent';

class DisciplineList extends Component {

    render() {
        var tableHeaders = [
            'Имя дисциплины',
            'Имя кафедры',
            'Директор кафедры'
        ];
        var tableFields = [
            'name',
            'department.name',
            'department.headOfTheDepartment'
        ];
        return (
            <ListComponent 
            tableHeaders={tableHeaders}
            tableFields={tableFields}
            title='Дисциплины'
            tooltipTitle='Create new Discipline'
            entityName='disciplines'/>
        );
    }

}
export default DisciplineList;