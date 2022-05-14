import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import { Container, Form, FormGroup } from 'reactstrap';
import AppNavbar from './AppNavbar';
import axios from 'axios';
import Button from '@mui/material/Button';
import MuiAlert from '@mui/material/Alert';
import Snackbar from '@mui/material/Snackbar';
import TextField from '@mui/material/TextField';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { format } from "date-fns";

class StudentEdit extends Component {

    emptyItem = {
        id: '',
        name: '',
        surname: '',
        patronymic: '',
        dateOfBirth: new Date(2000, 1, 1),
        groupId: ''
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
    
    async componentDidMount() {
        if (this.props.match.params.id !== 'new') {
            axios.get(`/api/students/${this.props.match.params.id}`)
            .then(res => {
                const temp = {
                    id: res.data.id,
                    name: res.data.name,
                    surname: res.data.surname,
                    patronymic: res.data.patronymic,
                    dateOfBirth: new Date(res.data.dateOfBirth),
                    groupId: res.data.group.id
                }
                this.setState({item: temp});
            });
        }
    }

    handleChange(event) {
        const target = event.target;
        var value = target.value;
        console.log(event.target.type)
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
        var date = item.dateOfBirth;
        item.dateOfBirth = format(date, "dd.MM.yyyy");

        
        axios.request({
            url: '/api/students/' ,
            method: (item.id) ? 'PUT' : 'POST',
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
        item.dateOfBirth = date;
    }

    handleClose = (event, reason) => {
        if (reason === 'clickaway') {
          return;
        }
    
        this.setState({
            open: false,
            ok: false,
            err: ''
        });
      };

    render() {

        const Alert = React.forwardRef(function Alert(props, ref) {
            return <MuiAlert elevation={6} ref={ref} variant="filled" {...props} />;
          });
        const {item} = this.state;
        const title = <h2>{item.id ? 'Изменение студента' : 'Добавление студента'}</h2>;
    
        return <div>
            <AppNavbar/>
            <Snackbar open={this.state.open} autoHideDuration={6000} onClose={this.handleClose}>
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
                        <TextField required={true} type="text" margin="dense" name="name" id="name" variant='standard' label="Имя студента" value={item.name || ''}
                               onChange={this.handleChange}/>
                    </FormGroup>
                    <FormGroup>
                        <TextField required={true} type="text" margin="dense" name="surname" id="surname" variant='standard' label="Фамилия студента" value={item.surname || ''}
                               onChange={this.handleChange} />
                    </FormGroup>
                    <FormGroup>
                        <TextField required={true} type="text" margin="dense" name="patronymic" id="patronymic" variant='standard' label="Отчество студента" value={item.patronymic || ''}
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
                        value={item.dateOfBirth || new Date(2000, 1, 1)}
                        maxDate={new Date()}
                        onChange={(newValue) => {
                            let item = {...this.state.item};
                            item['dateOfBirth'] = newValue;
                            this.setState({item});
                          }}
                        renderInput={(params) => <TextField {...params} required={true} type="date" margin="dense" name="dateOfBirth" id="dateOfBirth" variant='standard' label="Дата рождения" />}
                    />

                    </LocalizationProvider>
                    
                    </FormGroup>
                    <FormGroup>
                        
                        <TextField required={true}  type="number" margin="dense" name="groupId" id="groupId" variant='standard' label="Id группы" value={item.groupId || ''}
                               onChange={this.handleChange} />
                    </FormGroup>
                    <FormGroup className="mt-2">
                        <Button variant="contained" color="success" type="submit">Сохранить</Button>{' '}
                        <Button variant="contained" color="error" href="/students">Назад</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }
}
export default withRouter(StudentEdit);