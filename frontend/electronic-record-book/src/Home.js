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
                    <Button  href="/students">Студенты</Button>
                    <Button  href="/form-of-controls">Форма контроля</Button>
                    <Button  href="/specialties">Специальности</Button>
                    <Button  href="/professors">Преподаватели</Button>
                    <Button  href="/groups">Группы</Button>
                    <Button  href="/disciplines">Дисциплины</Button>
                    <Button  href="/departments">Кафедры</Button>
                    <Button  href="/achievements">Достижения</Button>
                </ButtonGroup>
            </div>
        );
    }
}
export default Home;