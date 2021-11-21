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

class GroupList extends Component {

    constructor(props) {
        super(props);
        this.state = {groups: []};
    }

    componentDidMount() {
        axios.get('/api/groups/')
        .then(res => {
            this.setState({groups: res.data});
        })
    }

    async remove(id) {
        await fetch(`/api/groups/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updated = [...this.state.groups].filter(i => i.id !== id);
            this.setState({groups: updated});
        });
    }

    render() {
        const {groups, isLoading} = this.state;
    
        if (isLoading) {
            return <p>Loading...</p>;
        }

        const list = groups.map(group => {
            return(
                <TableRow
                key={group.id}
                sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                >
                <TableCell component="th" scope="row">
                    {group.name}
                </TableCell>
                <TableCell >{group.course}</TableCell>
                <TableCell >{group.specialty.name}</TableCell>
                <TableCell><Button variant="contained" color="error" onClick={() => this.remove(group.id)}>Delete</Button></TableCell>
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
                        <TableCell>Имя группы</TableCell>
                        <TableCell>Курс</TableCell>
                        <TableCell>Название специальности</TableCell>
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
export default GroupList;