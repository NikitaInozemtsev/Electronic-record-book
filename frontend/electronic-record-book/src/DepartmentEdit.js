import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import EditComponent from './EditComponent';

class DepartmentEdit extends Component {

    render() {
        var fields = [
            'id',
            'name',
            'phoneNumber',
            'headOfTheDepartment'
        ]
        var fieldsIfEdit = new Map([
            ['id', 'id'],
            ['name', 'name'],
            ['phoneNumber', 'phoneNumber'],
            ['headOfTheDepartment', 'headOfTheDepartment']
        ])
        var fieldsType = new Map([
            ['name', 'text'],
            ['phoneNumber', 'text'],
            ['headOfTheDepartment', 'text']
        ])

        var fieldLabels = new Map([
            ['name', 'Имя кафедры'],
            ['phoneNumber', 'Номер телефона'],
            ['headOfTheDepartment', 'Директор кафедры']
        ])
        var defaultValue = {
            id: '',
            name: '',
            phoneNumber: '',
            headOfTheDepartment: ''
        }
        return (
            
            <EditComponent 
            entityName='departments'
            fields={fields}
            fieldsType={fieldsType}
            fieldLabels={fieldLabels}
            fieldMaxValues={new Map()}
            titleNew='Добавление кафедры'
            titleEdit='Изменение кафедры'
            defaultValue={defaultValue}
            fieldsIfEdit={fieldsIfEdit}
            />
        )

    }

}
export default withRouter(DepartmentEdit);