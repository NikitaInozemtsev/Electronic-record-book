import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import EditComponent from './EditComponent';

class DisciplineEdit extends Component {

    render() {
        var fields = [
            'id',
            'name',
            'departmentId'
        ]
        var fieldsIfEdit = new Map([
            ['id', 'id'],
            ['name', 'name'],
            ['departmentId', 'department.id']
        ])
        var fieldsType = new Map([
            ['name', 'text'],
            ['departmentId', 'number']
        ])

        var fieldLabels = new Map([
            ['name', 'Имя дисциплины'],
            ['departmentId', 'Id кафедры']
        ])
        var defaultValue = {
            id: '',
            name: '',
            departmentId: ''
        }
        return (
            
            <EditComponent 
            entityName='disciplines'
            fields={fields}
            fieldsType={fieldsType}
            fieldLabels={fieldLabels}
            fieldMaxValues={new Map()}
            titleNew='Добавление дисциплины'
            titleEdit='Изменение дисциплины'
            defaultValue={defaultValue}
            fieldsIfEdit={fieldsIfEdit}
            />
        )

    }

}
export default withRouter(DisciplineEdit);