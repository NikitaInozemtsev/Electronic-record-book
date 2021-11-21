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
import DepartmentEdit from './DepartmentEdit';
import DisciplineEdit from './DisciplineEdit';
import GroupEdit from './GroupEdit';
import ProfessorEdit from './ProfessorEdit';
import SpecialtyEdit from './SpecialtyEdit';
import StudentEdit from './StudentEdit';
import AchievementEdit from './AchievementEdit';

class App extends Component {
  render() {
    return (
        <Router>
          <Switch>
            <Route path='/' exact={true} component={Home}/>
            <Route path='/api/form-of-controls' exact={true} component={FormOfControlList}/>
            <Route path='/api/students' exact={true} component={StudentList}/>
            <Route path='/api/students/:id' component={StudentEdit}/>
            <Route path='/api/specialties' exact={true} component={SpecialtyList}/>
            <Route path='/api/specialties/:id' component={SpecialtyEdit}/>
            <Route path='/api/professors' exact={true} component={ProfessorList}/>
            <Route path='/api/professors/:id' component={ProfessorEdit}/>
            <Route path='/api/groups' exact={true} component={GroupList}/>
            <Route path='/api/groups/:id' component={GroupEdit}/>
            <Route path='/api/disciplines' exact={true} component={DisciplineList}/>
            <Route path='/api/disciplines/:id' component={DisciplineEdit}/>
            <Route path='/api/departments' exact={true} component={DepartmentList}/>
            <Route path='/api/departments/:id' component={DepartmentEdit}/>
            <Route path='/api/achievements' exact={true} component={AchievementList}/>
            <Route path='/api/achievements/new' component={AchievementEdit}/>
          </Switch>
        </Router>
    )
  }
}

export default App;