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

class ProfessorList extends Component {

    constructor(props) {
        super(props);
        this.state = {professors: []};
    }

    componentDidMount() {
        axios.get('/api/professors/')
        .then(res => {
            this.setState({professors: res.data});
        })
    }

    async remove(id) {
        await fetch(`/api/professors/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updated = [...this.state.professors].filter(i => i.id !== id);
            this.setState({professors: updated});
        });
    }

    render() {
        const {professors, isLoading} = this.state;
    
        if (isLoading) {
            return <p>Loading...</p>;
        }

        const list = professors.map(professor => {
            return(
                <TableRow
                key={professor.id}
                sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                >
                <TableCell component="th" scope="row">
                    {professor.name}
                </TableCell>
                <TableCell >{professor.surname}</TableCell>
                <TableCell >{professor.patronymic}</TableCell>
                <TableCell >{professor.post}</TableCell>
                <TableCell >{professor.department.name}</TableCell>
                <TableCell><Button variant="contained" color="error" onClick={() => this.remove(professor.id)}>Delete</Button></TableCell>
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
                        <TableCell>Фамилия преподавателя</TableCell>
                        <TableCell>Имя</TableCell>
                        <TableCell>Отчество</TableCell>
                        <TableCell>Должность</TableCell>
                        <TableCell>Имя кафедры</TableCell>
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
export default ProfessorList;