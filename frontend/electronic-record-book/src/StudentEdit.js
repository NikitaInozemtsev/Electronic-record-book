import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import EditComponent from './EditComponent';

class StudentEdit extends Component {
    
    render() {
        var fields = [
            'id',
            'name',
            'surname', 
            'patronymic',
            'dateOfBirth', 
            'groupId'
        ]
        var fieldsIfEdit = new Map([
            ['id', 'id'],
            ['name', 'name'],
            ['surname', 'surname'],
            ['patronymic', 'patronymic'],
            ['dateOfBirth', 'dateOfBirth'],
            ['groupId', 'group.id']
        ])
        var fieldsType = new Map([
            ['name', 'text'],
            ['surname', 'text'],
            ['patronymic', 'text'],
            ['dateOfBirth', 'date'],
            ['groupId', 'number']
        ])

        var fieldLabels = new Map([
            ['name', 'Имя студента'],
            ['surname', 'Фамилия студента'],
            ['patronymic', 'Отчество студента'],
            ['dateOfBirth', 'Дата рождения'],
            ['groupId', 'Id группы']
        ])
        var fieldMaxValues = new Map([
            ['dateOfBirth', new Date()]
        ])
        var defaultValue = {
            id: '',
            name: '',
            surname: '',
            patronymic: '',
            dateOfBirth: new Date(2000, 1, 1),
            groupId: ''
        }
        return (
            
            <EditComponent 
            entityName='students'
            fields={fields}
            fieldsType={fieldsType}
            fieldLabels={fieldLabels}
            fieldMaxValues={fieldMaxValues}
            titleNew='Добавление студента'
            titleEdit='Изменение студента'
            defaultValue={defaultValue}
            fieldsIfEdit={fieldsIfEdit}
            />
        )

    }
}
export default withRouter(StudentEdit);