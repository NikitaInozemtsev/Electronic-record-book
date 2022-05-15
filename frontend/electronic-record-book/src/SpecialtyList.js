import React, { Component } from 'react';
import ListComponent from './ListComponent';

class SpecialtyList extends Component {

    render() {
        var tableHeaders = [
            'Название специальности',
            'Цена (руб. в год)',
            'Название кафедры',
            'Номер телефона кафедры',
            'Директор кафедры'
        ];
        var tableFields = [
            'name',
            'price',
            'department.name',
            'department.phoneNumber',
            'department.headOfTheDepartment'
        ];
        return (
            <ListComponent 
            tableHeaders={tableHeaders}
            tableFields={tableFields}
            title='Специальности'
            tooltipTitle='Create new Specialty'
            entityName='specialties'/>
        );
    }

}
export default SpecialtyList;