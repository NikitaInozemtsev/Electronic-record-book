import React, { Component } from 'react';
import './App.css';
import Home from './Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';

import FormOfControlList from './FormOfConrolList';
import StudentList from './StudentList'
import SpecialtyList from './SpecialtyList';
import ProfessorList from './ProfessorList';
import GroupList from './GroupList';
import DisciplineList from './DisciplineList';
import DepartmentList from './DepartmentList';
import AchievementList from './AchievementList';

class App extends Component {
  render() {
    return (
        <Router>
          <Switch>
            <Route path='/' exact={true} component={Home}/>
            <Route path='/api/form-of-controls' exact={true} component={FormOfControlList}/>
            <Route path='/api/students' exact={true} component={StudentList}/>
            <Route path='/api/specialties' exact={true} component={SpecialtyList}/>
            <Route path='/api/professors' exact={true} component={ProfessorList}/>
            <Route path='/api/groups' exact={true} component={GroupList}/>
            <Route path='/api/disciplines' exact={true} component={DisciplineList}/>
            <Route path='/api/departments' exact={true} component={DepartmentList}/>
            <Route path='/api/achievements' exact={true} component={AchievementList}/>
          </Switch>
        </Router>
    )
  }
}

export default App;