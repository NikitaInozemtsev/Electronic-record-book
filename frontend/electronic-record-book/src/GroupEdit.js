import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import EditComponent from './EditComponent';


class GroupEdit extends Component {

    render() {
        var fields = [
            'id',
            'name',
            'course', 
            'specialtyId'
        ]
        var fieldsIfEdit = new Map([
            ['id', 'id'],
            ['name', 'name'],
            ['course', 'course'],
            ['specialtyId', 'specialty.id']
        ])
        var fieldsType = new Map([
            ['name', 'text'],
            ['course', 'number'],
            ['specialtyId', 'number']
        ])

        var fieldLabels = new Map([
            ['name', 'Имя группы'],
            ['course', 'Курс'],
            ['specialtyId', 'Id специальности']
        ])
        var defaultValue = {
            id: '',
            name: '',
            course: '',
            specialtyId: ''
        }
        return (
            
            <EditComponent 
            entityName='groups'
            fields={fields}
            fieldsType={fieldsType}
            fieldLabels={fieldLabels}
            fieldMaxValues={new Map()}
            titleNew='Добавление группы'
            titleEdit='Изменение группы'
            defaultValue={defaultValue}
            fieldsIfEdit={fieldsIfEdit}
            />
        )

    }

}
export default withRouter(GroupEdit);