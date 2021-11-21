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


class StudentList extends Component {

    constructor(props) {
        super(props);
        this.state = {students: []};
    }

    componentDidMount() {
        axios.get('/api/students/')
        .then(res => {
            this.setState({students: res.data});
        })
    }

    async remove(id) {
        await fetch(`/api/students/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updated = [...this.state.students].filter(i => i.id !== id);
            this.setState({student: updated});
        });
    }

    render() {
        const {students, isLoading} = this.state;
    
        if (isLoading) {
            return <p>Loading...</p>;
        }

        const list = students.map(student => {
            return(
                <TableRow
                key={student.id}
                sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                >
                <TableCell component="th" scope="row">
                    {student.surname}
                </TableCell>
                <TableCell >{student.name}</TableCell>
                <TableCell >{student.patronymic}</TableCell>
                <TableCell >{student.dateOfBirth}</TableCell>
                <TableCell >{student.group.name}</TableCell>
                <TableCell >{student.group.course}</TableCell>
                <TableCell >{student.group.specialty.name}</TableCell>
                <TableCell><Button variant="contained" color="error" onClick={() => this.remove(student.id)}>Delete</Button></TableCell>
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
                        <TableCell>Фамилия студента</TableCell>
                        <TableCell>Имя</TableCell>
                        <TableCell>Отчество</TableCell>
                        <TableCell>Дата рождения</TableCell>
                        <TableCell>Группа</TableCell>
                        <TableCell>Курс</TableCell>
                        <TableCell>Специальность</TableCell>
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
export default StudentList;