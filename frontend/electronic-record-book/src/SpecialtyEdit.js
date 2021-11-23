import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import { Container, Form, FormGroup } from 'reactstrap';
import AppNavbar from './AppNavbar';
import axios from 'axios';
import Input from '@mui/material/Input';
import Button from '@mui/material/Button';
import MuiAlert from '@mui/material/Alert';
import Snackbar from '@mui/material/Snackbar';

class SpecialtyEdit extends Component {

    emptyItem = {
        id: '',
        name: '',
        price: '',
        departmentId: ''
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
            axios.get(`/api/specialties/${this.props.match.params.id}`)
            .then(res => {
                const temp = {
                    id: res.data.id,
                    name: res.data.name,
                    price: res.data.price,
                    departmentId: res.data.department.id
                }
                this.setState({item: temp});
            });
        }
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
        const {item} = this.state;
        
        
        axios.request({
            url: '/api/specialties/' ,
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
        const title = <h2>{item.id ? 'Изменение специальности' : 'Добавление специальности'}</h2>;
    
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
                        <Input required='true' type="text" name="name" id="name" placeholder="Имя специальности" value={item.name || ''}
                               onChange={this.handleChange} />
                    </FormGroup>
                    <FormGroup>
                        <Input required='true' type="number" name="price" id="price" placeholder="Цена (руб. в год)" value={item.price || ''}
                               onChange={this.handleChange} />
                    </FormGroup>
                    <FormGroup>
                        <Input required='true' type="number" name="departmentId" id="departmentId" placeholder="Id кафедры" value={item.departmentId || ''}
                               onChange={this.handleChange} />
                    </FormGroup>
                    <FormGroup className="mt-2">
                        <Button variant="contained" color="success" type="submit">Сохранить</Button>{' '}
                        <Button variant="contained" color="error" href="/api/specialties">Назад</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }
}
export default withRouter(SpecialtyEdit);