import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import EditComponent from './EditComponent';

class AchievementEdit extends Component {

    render() {
        var fields = [
            'id',
            'studentId',
            'professorId',
            'formOfControlId',
            'disciplineId',
            'mark',
            'semester',
            'date'
        ]
        var fieldsType = new Map([
            ['studentId', 'number'],
            ['professorId', 'number'],
            ['formOfControlId', 'number'],
            ['disciplineId', 'number'],
            ['mark', 'select'],
            ['semester', 'number'],
            ['date', 'date']
        ])

        var fieldLabels = new Map([
            ['studentId', 'Id студента'],
            ['professorId', 'Id преподавателя'],
            ['formOfControlId', 'Id формы контроля'],
            ['disciplineId', 'Id дисциплины'],
            ['mark', 'Оценка'],
            ['semester', 'Семестр'],
            ['date', 'Дата']
        ])
        var defaultValue = {
            id: '',
            studentId: '',
            professorId: '',
            formOfControlId: '',
            disciplineId: '',
            mark: '',
            semester: '',
            date: new Date()
        }
        var selectItems = new Map([
            ['mark', [
                'Отлично',
                'Хорошо',
                'Удовлетворительно',
                'Неудовлетворительно',
                'Зачтено',
                'Не зачтено'
            ]]
        ])
        return (
            
            <EditComponent 
            entityName='achievements'
            fields={fields}
            fieldsType={fieldsType}
            fieldLabels={fieldLabels}
            fieldMaxValues={new Map()}
            titleNew='Добавление достижения'
            defaultValue={defaultValue}
            selectItems={selectItems}
            />
        )

    }
}
export default withRouter(AchievementEdit);