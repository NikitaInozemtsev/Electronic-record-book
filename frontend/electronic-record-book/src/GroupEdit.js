import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import { Container, Form, FormGroup } from 'reactstrap';
import AppNavbar from './AppNavbar';
import axios from 'axios';
import Button from '@mui/material/Button';
import MuiAlert from '@mui/material/Alert';
import Snackbar from '@mui/material/Snackbar';
import TextField from '@mui/material/TextField';


class GroupEdit extends Component {

    emptyItem = {
        id: '',
        name: '',
        course: '',
        specialtyId: ''
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
            axios.get(`/api/groups/${this.props.match.params.id}`)
            .then(res => {
                const temp = {
                    id: res.data.id,
                    name: res.data.name,
                    course: res.data.course,
                    specialtyId: res.data.specialty.id
                }
                this.setState({item: temp});
            });
        }
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
        const {item} = this.state;
        
        
        axios.request({
            url: '/api/groups/' ,
            method: (item.id) ? 'PUT' : 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: JSON.stringify(item),
        })
        .then(res => {
            console.log(res);
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
        const title = <h2>{item.id ? 'Изменение группы' : 'Добавление группы'}</h2>;
    
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
                        <TextField required={true} type="text" name="name" id="name" margin="dense" variant="standard" label="Имя группы" value={item.name || ''}
                               onChange={this.handleChange} />
                    </FormGroup>
                    <FormGroup>
                        <TextField required={true} type="number" name="course" id="course" margin="dense" variant="standard" label="Курс" value={item.course || ''}
                               onChange={this.handleChange} />
                    </FormGroup>
                    <FormGroup>
                        <TextField required={true} type="number" name="specialtyId" id="specialtyId" margin="dense" variant="standard" label="Id специальности" value={item.specialtyId || ''}
                               onChange={this.handleChange} />
                    </FormGroup>
                    <FormGroup className="mt-2">
                        <Button variant="contained" color="success" type="submit">Сохранить</Button>{' '}
                        <Button variant="contained" color="error" href="/groups">Назад</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }
}
export default withRouter(GroupEdit);