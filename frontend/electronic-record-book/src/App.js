import React, { Component } from 'react';
import './App.css';
import Home from './Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import axios from 'axios';

import FormOfControlList from './FormOfConrolList';
import StudentList from './StudentList'
import SpecialtyList from './SpecialtyList';
import ProfessorList from './ProfessorList';
import GroupList from './GroupList';
import DisciplineList from './DisciplineList';
import DepartmentList from './DepartmentList';
import AchievementList from './AchievementList';
import DepartmentEdit from './DepartmentEdit';
import DisciplineEdit from './DisciplineEdit';
import GroupEdit from './GroupEdit';
import ProfessorEdit from './ProfessorEdit';
import SpecialtyEdit from './SpecialtyEdit';
import StudentEdit from './StudentEdit';
import AchievementEdit from './AchievementEdit';

require('dotenv').config()

class App extends Component {

  render() {
  axios.defaults.baseURL = process.env.REACT_APP_PROXY_URL;
    return (
        <Router>
          <Switch>
            <Route path='/' exact={true} component={Home}/>
            <Route path='/form-of-controls' exact={true} component={FormOfControlList}/>
            <Route path='/students' exact={true} component={StudentList}/>
            <Route path='/students/:id' component={StudentEdit}/>
            <Route path='/specialties' exact={true} component={SpecialtyList}/>
            <Route path='/specialties/:id' component={SpecialtyEdit}/>
            <Route path='/professors' exact={true} component={ProfessorList}/>
            <Route path='/professors/:id' component={ProfessorEdit}/>
            <Route path='/groups' exact={true} component={GroupList}/>
            <Route path='/groups/:id' component={GroupEdit}/>
            <Route path='/disciplines' exact={true} component={DisciplineList}/>
            <Route path='/disciplines/:id' component={DisciplineEdit}/>
            <Route path='/departments' exact={true} component={DepartmentList}/>
            <Route path='/departments/:id' component={DepartmentEdit}/>
            <Route path='/achievements' exact={true} component={AchievementList}/>
            <Route path='/achievements/new' component={AchievementEdit}/>
          </Switch>
        </Router>
    )
  }
}

export default App;