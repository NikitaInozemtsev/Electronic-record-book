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


class ListComponent extends Component {

    constructor(props) {
        super(props);
        this.state = {
            items: [],
            tableHeaders: props.tableHeaders,
            tableFields: props.tableFields,
            title: props.title,
            tooltipTitle: props.tooltipTitle,
            entityName: props.entityName
        };
    }

    componentDidMount() {
        axios.get(`/api/${this.state.entityName}/`)
        .then(res => {
            this.setState({items: res.data});
        })
    }

    remove(id) {
        axios.delete(`/api/${this.state.entityName}/${id}`).then(res => {
            let updated = [...this.state.items].filter(i => i.id !== id);
            this.setState({items: updated});
        })
        .catch(err => {
            console.log(err);
        });
    }

    render() {
        const {items, isLoading} = this.state;
    
        if (isLoading) {
            return <p>Loading...</p>;
        }

        const list = items.map(item => {
            return(
                <TableRow
                key={item.id}
                sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                >
                    <TableCell component="th" scope="row">
                    {item.id}
                    </TableCell>
                    {this.state.tableFields.map(element => (
                            <TableCell >{eval(`item.${element}`)}</TableCell>
                        
                    ))}
                <TableCell>
                <ButtonGroup variant="text" aria-label="text button group">
                    
                    <Button color="primary"  href={`/${this.state.entityName}/${item.id}`}>Edit</Button>
                    <Button color="error" onClick={() => this.remove(item.id)}>Delete</Button>
                </ButtonGroup>
                    </TableCell>
                </TableRow>
            );
        });
    
        return (
            
            <div>
                <AppNavbar/>
                <h3>{this.state.title} <Tooltip  title={this.state.tooltipTitle}>
                    <IconButton color="info" href={`/${this.state.entityName}/new`}>
                        <AddRoundedIcon />
                    </IconButton>
                </Tooltip></h3>
                <TableContainer component={Paper}>
                <Table sx={{ minWidth: 650 }} aria-label="simple table">
                    <TableHead>
                    <TableRow>
                        <TableCell>Id</TableCell>
                        {this.state.tableHeaders.map(element => (
                                <TableCell >{element}</TableCell>
                            
                        ))}
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
export default ListComponent;