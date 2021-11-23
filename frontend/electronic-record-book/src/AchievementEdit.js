import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import { Container, Form, FormGroup } from 'reactstrap';
import AppNavbar from './AppNavbar';
import axios from 'axios';
import Input from '@mui/material/Input';
import Button from '@mui/material/Button';
import MuiAlert from '@mui/material/Alert';
import Snackbar from '@mui/material/Snackbar';
import MenuItem from '@mui/material/MenuItem';
import Select from '@mui/material/Select';
import InputLabel from '@mui/material/InputLabel';

class AchievementEdit extends Component {

    emptyItem = {
        id: '',
        studentId: '',
        professorId: '',
        formOfControlId: '',
        disciplineId: '',
        mark: '',
        semester: '',
        date: ''
    };

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem,
            ok: false,
            open: false,
            err: '',
            lastDate: ''
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        
        this.handleClose = this.handleClose.bind(this);
    }

    

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let item = {...this.state.item};
        item[name] = value;
        this.setState({item});
    }

    async handleSubmit(event) {
        event.preventDefault();
        let {item} = this.state;
        const dates = item.date.split('-');
        item.date = dates[2] + '.' + dates[1] + '.' + dates[0];
        
        axios.request({
            url: '/api/achievements/' ,
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: JSON.stringify(item),
        })
        .then(res => {
            this.setState({
            ok: true
            })
        })
        .catch(err => 
            this.setState({
                    open: true,
                    err: err.response.data
                })

        );
        this.setState({
            lastDate: item.date
        });
        
    }

    handleClose = (event, reason) => {
        if (reason === 'clickaway') {
          return;
        }
    
        this.setState({
            ok: false,
            open: false,
            err: ''
        });
      };

    render() {
        const Alert = React.forwardRef(function Alert(props, ref) {
            return <MuiAlert elevation={6} ref={ref} variant="filled" {...props} />;
          });
        const {item} = this.state;
        const title = <h2>{'Добавление достижения'}</h2>;
        const handleChangeSelect = (event) => {
            const target = event.target;
            const value = target.value;
            const name = target.name;
            let item = {...this.state.item};
            item[name] = value;
            this.setState({item});
          };
    
        return (<div>
            <AppNavbar/>
            <Snackbar open={this.state.open || this.state.ok} autoHideDuration={6000} onClose={this.handleClose}>
                <Alert onClose={this.handleClose} severity="error" sx={{ width: '100%' }}>
                {this.state.err}
                </Alert>
                
            </Snackbar>
            <Snackbar open={this.state.ok} autoHideDuration={6000} onClose={this.handleClose}>
                
                <Alert onClose={this.handleClose} severity="success" sx={{ width: '100%' }}>
                Ok
                </Alert>
            </Snackbar>
            <Container>
                {title}
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Input required='true' type="number" name="studentId" id="studentId" placeholder="Id студента" value={item.studentId || ''}
                               onChange={this.handleChange} />
                    </FormGroup>
                    <FormGroup>
                        <Input required='true' type="number" name="professorId" id="professorId" placeholder="Id преподавателя" value={item.professorId || ''}
                               onChange={this.handleChange} />
                    </FormGroup>
                    <FormGroup>
                        <Input required='true' type="number" name="formOfControlId" id="formOfControlId" placeholder="Id формы контроля" value={item.formOfControlId || ''}
                               onChange={this.handleChange} />
                    </FormGroup>
                    <FormGroup>
                        <Input required='true' type="number" name="disciplineId" id="disciplineId" placeholder="Id дисциплины" value={item.disciplineId || ''}
                               onChange={this.handleChange} />
                    </FormGroup>
                    <FormGroup>
                    <InputLabel id="demo-simple-select-label">Оценка</InputLabel>
                    <Select
                        labelId="demo-simple-select-label"
                        required='true'
                        id="mark"
                        name="mark"
                        value={item.mark || ''}
                        label="Оценка"
                        onChange={handleChangeSelect}
                        >
                        <MenuItem value={'Отлично'}>Отлично</MenuItem>
                        <MenuItem value={'Хорошо'}>Хорошо</MenuItem>
                        <MenuItem value={'Удовлетворительно'}>Удовлетворительно</MenuItem>
                        <MenuItem value={'Неудовлетворительно'}>Неудовлетворительно</MenuItem>
                        <MenuItem value={'Зачтено'}>Зачтено</MenuItem>
                        <MenuItem value={'Не зачтено'}>Не зачтено</MenuItem>
                        </Select>
                        
                    </FormGroup>
                    <FormGroup>
                        <Input required='true' type="number" name="semester" id="semester" placeholder="Семестер" value={item.semester || ''}
                               onChange={this.handleChange} />
                    </FormGroup>
                    <FormGroup>
                        <Input required='true' type="date" name="date" id="date" placeholder="Дата" value={item.date || ''}
                               onChange={this.handleChange} />
                    </FormGroup>
                    <FormGroup className="mt-2">
                        <Button variant="contained" color="success" type="submit">Сохранить</Button>{' '}
                        <Button variant="contained" color="error" href="/api/achievements">Назад</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>)
    }
}
export default withRouter(AchievementEdit);