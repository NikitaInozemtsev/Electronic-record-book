import React, { Component } from 'react';
import ListComponent from './ListComponent';

class GroupList extends Component {

    render() {
        var tableHeaders = [
            'Имя группы',
            'Курс',
            'Название специальности'
        ];
        var tableFields = [
            'name',
            'course',
            'specialty.name'
        ];
        return (
            <ListComponent 
            tableHeaders={tableHeaders}
            tableFields={tableFields}
            title='Группы'
            tooltipTitle='Create new Group'
            entityName='groups'/>
        );
    }

}
export default GroupList;