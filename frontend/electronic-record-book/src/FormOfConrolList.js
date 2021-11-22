import React, { Component } from 'react';
import AppNavbar from './AppNavbar';
import axios from 'axios';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableHead from '@mui/material/TableHead';
import TableContainer from '@mui/material/TableContainer';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';

class FormOfControlList extends Component {

    constructor(props) {
        super(props);
        this.state = {formOfControls: []};
    }

    componentDidMount() {
        axios.get('/api/form-of-controls/')
        .then(res => {
            this.setState({formOfControls: res.data});
        })
    }


    render() {
        const {formOfControls, isLoading} = this.state;
    
        if (isLoading) {
            return <p>Loading...</p>;
        }

        const list = formOfControls.map(formOfControl => {
            return(
                <TableRow
                key={formOfControl.id}
                sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                >
                <TableCell component="th" scope="row">
                    {formOfControl.id}
                </TableCell>
                <TableCell>
                    {formOfControl.name}
                </TableCell>
               </TableRow>
            );
        });
    
        return (
            
            <div>
                <AppNavbar/>
                <TableContainer component={Paper}>
                <Table sx={{ minWidth: 650 }} aria-label="simple table">
                    <TableHead>
                    <TableRow>
                        <TableCell>Id</TableCell>
                        <TableCell>Форма контроля</TableCell>
                    </TableRow>
                    </TableHead>
                    <TableBody>
                    {list}
                    </TableBody>
                </Table>
                </TableContainer>
                
            </div>
        );
    }

}
export default FormOfControlList;