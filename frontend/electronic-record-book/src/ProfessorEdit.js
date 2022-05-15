import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import EditComponent from './EditComponent';

class ProfessorEdit extends Component {

    render() {
        var fields = [
            'id',
            'name',
            'surname', 
            'patronymic',
            'post',
            'departmentId'
        ]
        var fieldsIfEdit = new Map([
            ['id', 'id'],
            ['name', 'name'],
            ['surname', 'surname'],
            ['patronymic', 'patronymic'],
            ['post', 'post'],
            ['departmentId', 'department.id']
        ])
        var fieldsType = new Map([
            ['name', 'text'],
            ['surname', 'text'],
            ['patronymic', 'text'],
            ['post', 'text'],
            ['departmentId', 'number']
        ])

        var fieldLabels = new Map([
            ['name', 'Имя преподавателя'],
            ['surname', 'Фамилия преподавателя'],
            ['patronymic', 'Отчество преподавателя'],
            ['post', 'Должность'],
            ['departmentId', 'Id кафедры']
        ])
        var defaultValue = {
            id: '',
            name: '',
            surname: '',
            patronymic: '',
            post: '',
            departmentId: ''
        }
        return (
            
            <EditComponent 
            entityName='professors'
            fields={fields}
            fieldsType={fieldsType}
            fieldLabels={fieldLabels}
            fieldMaxValues={new Map()}
            titleNew='Добавление преподвателя'
            titleEdit='Изменение преподвателя'
            defaultValue={defaultValue}
            fieldsIfEdit={fieldsIfEdit}
            />
        )

    }
}
export default withRouter(ProfessorEdit);