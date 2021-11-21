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
import ButtonGroup from '@mui/material/ButtonGroup';
import AddRoundedIcon from '@mui/icons-material/AddRounded';
import IconButton from '@mui/material/IconButton';
import Tooltip from '@mui/material/Tooltip';

class DepartmentList extends Component {

    constructor(props) {
        super(props);
        this.state = {departments: []};
    }

    componentDidMount() {
        axios.get('/api/departments/')
        .then(res => {
            this.setState({departments: res.data});
        })
    }

    async remove(id) {
        await fetch(`/api/departments/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updated = [...this.state.departments].filter(i => i.id !== id);
            this.setState({departments: updated});
        });
    }

    render() {
        const {departments, isLoading} = this.state;
    
        if (isLoading) {
            return <p>Loading...</p>;
        }

        const list = departments.map(department => {
            return(
                <TableRow
                key={department.name}
                sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                >
                <TableCell component="th" scope="row">
                    {department.name}
                </TableCell>
                <TableCell >{department.phoneNumber}</TableCell>
                <TableCell >{department.headOfTheDepartment}</TableCell>
                <TableCell>
                    
                    <ButtonGroup variant="text" aria-label="text button group">
                    
                        <Button color="primary"  href={"/api/departments/" + department.id}>Edit</Button>
                        <Button color="error" onClick={() => this.remove(department.id)}>Delete</Button>
                    </ButtonGroup>
                    
                </TableCell>
                </TableRow>
            );
        });
    
        return (
            
            <div>
                <AppNavbar/>
                
                <h3>Кафедры <Tooltip  title="Create new Department">
                    <IconButton color="info" href="/api/departments/new">
                        <AddRoundedIcon />
                    </IconButton>
                </Tooltip></h3>
                
                <TableContainer component={Paper}>
                <Table sx={{ minWidth: 650 }} aria-label="simple table">
                    <TableHead>
                    <TableRow>
                        <TableCell>Имя кафедры</TableCell>
                        <TableCell>Номер телефона кафедры</TableCell>
                        <TableCell>Директор кафедры</TableCell>
                        <TableCell>Действия</TableCell>
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
export default DepartmentList;