import React, { Component } from 'react';
import './App.css';
import AppNavbar from './AppNavbar';

import Button from '@mui/material/Button';
import ButtonGroup from '@mui/material/ButtonGroup';

class Home extends Component {
    render() {
        return (
            <div>
                <AppNavbar/>
                <ButtonGroup className="mt-2" variant="text" aria-label="text button group">
                    <Button  href="/api/students">Студенты</Button>
                    <Button  href="/api/form-of-controls">Форма контроля</Button>
                    <Button  href="/api/specialties">Специальности</Button>
                    <Button  href="/api/professors">Преподаватели</Button>
                    <Button  href="/api/groups">Группы</Button>
                    <Button  href="/api/disciplines">Дисциплины</Button>
                    <Button  href="/api/departments">Кафедры</Button>
                    <Button  href="/api/achievements">Достижения</Button>
                </ButtonGroup>
            </div>
        );
    }
}
export default Home;