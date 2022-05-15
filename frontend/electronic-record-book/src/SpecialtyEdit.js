import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import EditComponent from './EditComponent';

class SpecialtyEdit extends Component {

    render() {
        var fields = [
            'id',
            'name',
            'price', 
            'departmentId'
        ]
        var fieldsIfEdit = new Map([
            ['id', 'id'],
            ['name', 'name'],
            ['price', 'price'],
            ['departmentId', 'department.id']
        ])
        var fieldsType = new Map([
            ['name', 'text'],
            ['price', 'number'],
            ['departmentId', 'number']
        ])

        var fieldLabels = new Map([
            ['name', 'Имя специальности'],
            ['price', 'Цена (руб. в год)'],
            ['departmentId', 'Id кафедры']
        ])
        var fieldMaxValues = new Map([
            ['price', 2147483647]
        ])
        var defaultValue = {
            id: '',
            name: '',
            price: '',
            departmentId: ''
        }
        return (
            
            <EditComponent 
            entityName='specialties'
            fields={fields}
            fieldsType={fieldsType}
            fieldLabels={fieldLabels}
            fieldMaxValues={fieldMaxValues}
            titleNew='Добавление специальности'
            titleEdit='Изменение специальности'
            defaultValue={defaultValue}
            fieldsIfEdit={fieldsIfEdit}
            />
        )

    }
}
export default withRouter(SpecialtyEdit);