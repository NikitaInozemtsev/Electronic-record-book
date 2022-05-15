import React, { Component } from 'react';
import ListComponent from './ListComponent';

class DepartmentList extends Component {

    render() {
        var tableHeaders = [
            'Имя кафедры',
            'Номер телефона кафедры',
            'Директор кафедры'
        ];
        var tableFields = [
            'name',
            'phoneNumber',
            'headOfTheDepartment'
        ];
        return (
            <ListComponent 
            tableHeaders={tableHeaders}
            tableFields={tableFields}
            title='Кафедры'
            tooltipTitle='Create new Department'
            entityName='departments'/>
        );
    }

}
export default DepartmentList;