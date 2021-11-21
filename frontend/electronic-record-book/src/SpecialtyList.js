import React, { Component } from 'react';
import AppNavbar from './AppNavbar';
import axios from 'axios';
import Button from '@mui/material/Button';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableHead from '@mui/material/TableHead';
import TableContainer from '@mui/material/TableContainer';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';

class SpecialtyList extends Component {

    constructor(props) {
        super(props);
        this.state = {specialties: []};
    }

    componentDidMount() {
        axios.get('/api/specialties/')
        .then(res => {
            this.setState({specialties: res.data});
        })
    }

    async remove(id) {
        await fetch(`/api/specialties/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updated = [...this.state.specialties].filter(i => i.id !== id);
            this.setState({specialties: updated});
        });
    }

    render() {
        const {specialties, isLoading} = this.state;
    
        if (isLoading) {
            return <p>Loading...</p>;
        }

        const list = specialties.map(specialty => {
            return(
                <TableRow
                key={specialty.id}
                sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                >
                <TableCell component="th" scope="row">
                    {specialty.name}
                </TableCell>
                <TableCell >{specialty.price}</TableCell>
                <TableCell >{specialty.department.name}</TableCell>
                <TableCell >{specialty.department.phoneNumber}</TableCell>
                <TableCell >{specialty.department.headOfTheDepartment}</TableCell>
                <TableCell><Button variant="contained" color="error" onClick={() => this.remove(specialty.id)}>Delete</Button></TableCell>
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
                        <TableCell>Название специальности</TableCell>
                        <TableCell>Цена (руб. в год)</TableCell>
                        <TableCell>Название кафедры</TableCell>
                        <TableCell>Номер телефона кафедры</TableCell>
                        <TableCell>Директор кафедры</TableCell>
                        <TableCell></TableCell>

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
export default SpecialtyList;