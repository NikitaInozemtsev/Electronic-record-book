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
                    {group.id}
                </TableCell>
                <TableCell >{group.name}</TableCell>
                <TableCell >{group.course}</TableCell>
                <TableCell >{group.specialty.name}</TableCell>
                <TableCell>
                <ButtonGroup variant="text" aria-label="text button group">
                    
                    <Button color="primary"  href={"/api/groups/" + group.id}>Edit</Button>
                    <Button color="error" onClick={() => this.remove(group.id)}>Delete</Button>
                </ButtonGroup>
                </TableCell>
                </TableRow>
            );
        });
    
        return (
            
            <div>
                <AppNavbar/>
                <h3>Группы <Tooltip  title="Create new Group">
                    <IconButton color="info" href="/api/groups/new">
                        <AddRoundedIcon />
                    </IconButton>
                </Tooltip></h3>
                <TableContainer component={Paper}>
                <Table sx={{ minWidth: 650 }} aria-label="simple table">
                    <TableHead>
                    <TableRow>
                    <TableCell>Id</TableCell>

                        <TableCell>Имя группы</TableCell>
                        <TableCell>Курс</TableCell>
                        <TableCell>Название специальности</TableCell>
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
export default GroupList;