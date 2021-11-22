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


class DisciplineList extends Component {

    constructor(props) {
        super(props);
        this.state = {disciplines: []};
    }

    componentDidMount() {
        axios.get('/api/disciplines/')
        .then(res => {
            this.setState({disciplines: res.data});
        })
    }

    async remove(id) {
        await fetch(`/api/disciplines/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updated = [...this.state.disciplines].filter(i => i.id !== id);
            this.setState({disciplines: updated});
        });
    }

    render() {
        const {disciplines, isLoading} = this.state;
    
        if (isLoading) {
            return <p>Loading...</p>;
        }

        const list = disciplines.map(discipline => {
            return(
                <TableRow
                key={discipline.id}
                sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                >
                <TableCell component="th" scope="row">
                    {discipline.id}
                </TableCell>
                <TableCell >{discipline.name}</TableCell>
                <TableCell >{discipline.department.name}</TableCell>
                <TableCell >{discipline.department.headOfTheDepartment}</TableCell>
                <TableCell>
                <ButtonGroup variant="text" aria-label="text button group">
                    
                    <Button color="primary"  href={"/api/disciplines/" + discipline.id}>Edit</Button>
                    <Button color="error" onClick={() => this.remove(discipline.id)}>Delete</Button>
                </ButtonGroup>
                    </TableCell>
                </TableRow>
            );
        });
    
        return (
            
            <div>
                <AppNavbar/>
                <h3>Дисциплины <Tooltip  title="Create new Discipline">
                    <IconButton color="info" href="/api/disciplines/new">
                        <AddRoundedIcon />
                    </IconButton>
                </Tooltip></h3>
                <TableContainer component={Paper}>
                <Table sx={{ minWidth: 650 }} aria-label="simple table">
                    <TableHead>
                    <TableRow>
                        <TableCell>Id</TableCell>
                        <TableCell>Имя дисциплины</TableCell>
                        <TableCell>Имя кафедры</TableCell>
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
export default DisciplineList;