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
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import Select from '@mui/material/Select';

class EditComponent extends Component {

    constructor(props) {
        super(props);
        this.state = {
            entityName: props.entityName,
            fields: props.fields,
            fieldsType: props.fieldsType,
            fieldLabels: props.fieldLabels,
            fieldMaxValues: props.fieldMaxValues,
            titleNew: props.titleNew,
            titleEdit: props.titleEdit,
            item: props.defaultValue,
            fieldsIfEdit: props.fieldsIfEdit,
            selectItems: props.selectItems,
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
            axios.get(`/api/${this.state.entityName}/${this.props.match.params.id}`)
            .then(res => {
                var temp = this.state.item;
                this.state.fieldsIfEdit.forEach((value, key) => {
                    var type = this.state.fieldsType.get(key);
                    if (type == 'date') {
                        eval(`temp.${key} = new Date(res.data.${value})`);
                    }
                    else {
                        eval(`temp.${key} = res.data.${value}`);
                    }
                });
                
                this.setState({item: temp});
            });
        }
    }

    handleChange(event) {
        const target = event.target;
        var value = target.value;
        const name = target.name;
        if (event.target.type == 'number') {
            console.log(value)
            value = parseInt(value);
            var maxValue = this.state.fieldMaxValues.get(name);
            if (value > maxValue) {
                value = maxValue;
            }
        }
        
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
        var dateField;
        this.state.fields.forEach(element => {
            if (this.state.fieldsType.get(element) == 'date') {
                dateField = element;
            }
        });
        var date;
        console.log(dateField)
        if (dateField) {
            date = eval(`item.${dateField}`);
            console.log(date)
            var dateStr = format(date, 'dd.MM.yyyy');
            eval(`item.${dateField} = dateStr`) ;
        }

        
        axios.request({
            url: `/api/${this.state.entityName}/` ,
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
        if (dateField) {
            eval(`item.${dateField} = date`);
        }
    }

    handleClose = (event, reason) => {
        console.log(event);
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
        const title = <h2>{item.id ? this.state.titleEdit : this.state.titleNew}</h2>;
    
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
                    {
                        this.state.fields.map(field => {
                            if (field != 'id') {
                                var type = this.state.fieldsType.get(field);
                                var label = this.state.fieldLabels.get(field);
                                var result;
                                if (type == 'date') {
                                    var maxValue = this.state.fieldMaxValues.get(field);
                                    result = (
                                        <FormGroup>
                                    <LocalizationProvider dateAdapter={AdapterDateFns}>
                                    <DatePicker 
                                        allowSameDateSelection={true}
                                        inputFormat="dd.MM.yyyy"
                                        views={['year', 'month', 'day']}
                                        mask='__.__.____'
                                        showDaysOutsideCurrentMonth={true}
                                        value={eval(`item.${field}`)}
                                        maxDate={maxValue}
                                        onChange={(newValue) => {
                                            let item = {...this.state.item};
                                            item[field] = newValue;
                                            this.setState({item});
                                        }}
                                        renderInput={(params) => <TextField {...params} required={true} type="date" margin="dense" name={field} id={field} variant='standard' label={label} />}
                                    />
                
                                    </LocalizationProvider>
                                    </FormGroup>);
                                }
                                else if (type == 'select') {
                                    var selectItems = this.state.selectItems.get(field);
                                    result = (
                                        <FormGroup>
                                            <InputLabel>{label}</InputLabel>
                                            <Select
                                                required={true}
                                                id={field}
                                                name={field}
                                                value={eval(`item.${field}`)}
                                                label={label}
                                                onChange={this.handleChange}
                                                >
                                                {selectItems.map(sel => (
                                                    <MenuItem value={sel}>{sel}</MenuItem>
                                                ))}
                                                </Select>
                                                
                                        </FormGroup>
                                    );
                                }
                                else {
                                    result = (
                                        <FormGroup>
                                            <TextField required={true} type={type} margin="dense" name={field} id={field} variant='standard' label={label} value={eval(`item.${field}`)}
                                                onChange={this.handleChange}/>
                                        </FormGroup>
                                    )
                                }
                                
                                
                                return result;
                            }
                        })
                    }
                    <FormGroup className="mt-2">
                        <Button variant="contained" color="success" type="submit">Сохранить</Button>{' '}
                        <Button variant="contained" color="error" href={`/${this.state.entityName}`}>Назад</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }
}
export default withRouter(EditComponent);