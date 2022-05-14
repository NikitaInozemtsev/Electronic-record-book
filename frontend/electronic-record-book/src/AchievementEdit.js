import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import { Container, Form, FormGroup } from 'reactstrap';
import AppNavbar from './AppNavbar';
import axios from 'axios';
import Button from '@mui/material/Button';
import MuiAlert from '@mui/material/Alert';
import Snackbar from '@mui/material/Snackbar';
import MenuItem from '@mui/material/MenuItem';
import Select from '@mui/material/Select';
import InputLabel from '@mui/material/InputLabel';
import TextField from '@mui/material/TextField';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { format } from "date-fns";

class AchievementEdit extends Component {

    emptyItem = {
        id: '',
        studentId: '',
        professorId: '',
        formOfControlId: '',
        disciplineId: '',
        mark: '',
        semester: '',
        date: new Date()
    };

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem,
            ok: false,
            open: false,
            err: ''
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        
        this.handleClose = this.handleClose.bind(this);
    }

    

    handleChange(event) {
        const target = event.target;
        var value = target.value;
        if (event.target.type == 'number') {
            value = parseInt(value);
        }
        const name = target.name;
        let item = {...this.state.item};
        item[name] = value;
        this.setState({item});
    }

    async handleSubmit(event) {
        this.setState({
            open: false,
            ok: false,
            err: ''
        });
        event.preventDefault();
        let {item} = this.state;
        var date = item.date;
        item.date = format(date, "dd.MM.yyyy");
        
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
        item.date = date;
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
                        <TextField required={true} type="number" name="studentId" id="studentId" margin="dense" variant="standard" label="Id студента" value={item.studentId || ''}
                               onChange={this.handleChange} />
                    </FormGroup>
                    <FormGroup>
                        <TextField required={true} type="number" name="professorId" id="professorId" margin="dense" variant="standard" label="Id преподавателя" value={item.professorId || ''}
                               onChange={this.handleChange} />
                    </FormGroup>
                    <FormGroup>
                        <TextField required={true} type="number" name="formOfControlId" id="formOfControlId" margin="dense" variant="standard" label="Id формы контроля" value={item.formOfControlId || ''}
                               onChange={this.handleChange} />
                    </FormGroup>
                    <FormGroup>
                        <TextField required={true} type="number" name="disciplineId" id="disciplineId" margin="dense" variant="standard" label="Id дисциплины" value={item.disciplineId || ''}
                               onChange={this.handleChange} />
                    </FormGroup>
                    <FormGroup>
                    <InputLabel id="demo-simple-select-label">Оценка</InputLabel>
                    <Select
                        labelId="demo-simple-select-label"
                        required={true}
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
                        <TextField required={true} type="number" name="semester" id="semester" margin="dense" variant="standard" label="Семестер" value={item.semester || ''}
                               onChange={this.handleChange} />
                    </FormGroup>
                    <FormGroup>
                    <LocalizationProvider dateAdapter={AdapterDateFns}>
                    <DatePicker 
                        allowSameDateSelection={true}
                        inputFormat="dd.MM.yyyy"
                        views={['year', 'month', 'day']}
                        mask='__.__.____'
                        showDaysOutsideCurrentMonth={true}
                        value={item.date || new Date()}
                        onChange={(newValue) => {
                            let item = {...this.state.item};
                            item['date'] = newValue;
                            this.setState({item});
                          }}
                        renderInput={(params) => <TextField {...params} required={true} type="date" name="date" id="date" margin="dense" variant="standard" label="Дата"/>}
                    />

                    </LocalizationProvider>
                        
                    </FormGroup>
                    <FormGroup className="mt-2">
                        <Button variant="contained" color="success" type="submit">Сохранить</Button>{' '}
                        <Button variant="contained" color="error" href="/achievements">Назад</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>)
    }
}
export default withRouter(AchievementEdit);