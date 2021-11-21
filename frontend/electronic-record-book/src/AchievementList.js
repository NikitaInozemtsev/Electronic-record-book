import React, { Component } from 'react';
import AppNavbar from './AppNavbar';
import axios from 'axios';
import Box from '@mui/material/Box';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TablePagination from '@mui/material/TablePagination';
import TableRow from '@mui/material/TableRow';
import TableSortLabel from '@mui/material/TableSortLabel';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Paper from '@mui/material/Paper';
import Checkbox from '@mui/material/Checkbox';
import IconButton from '@mui/material/IconButton';
import Tooltip from '@mui/material/Tooltip';
import DeleteIcon from '@mui/icons-material/Delete';
import FilterListIcon from '@mui/icons-material/FilterList';
import { alpha } from '@mui/material/styles';
import DateRangePicker from '@mui/lab/DateRangePicker';
import AdapterDateFns from '@mui/lab/AdapterDateFns';
import LocalizationProvider from '@mui/lab/LocalizationProvider';
import TextField from '@mui/material/TextField';
import { format } from "date-fns";

class AchievementList extends Component {

    constructor(props) {
        super(props);
        this.order = React.createRef();
        this.orderBy = React.createRef();
        this.date = React.createRef();
        
        this.retrieveAchievements = this.retrieveAchievements.bind(this);
        this.handlePageChange = this.handlePageChange.bind(this);
        this.handlePageSizeChange = this.handlePageSizeChange.bind(this);
        this.handleSelectAllClick = this.handleSelectAllClick.bind(this);
        this.handleDateChange = this.handleDateChange.bind(this);
        this.state = {
            achievements: [],
            page: 0,
            totalItems: 0,
            selected: [],
            pageSize: 5
        };
        
        this.order.current=null;
        this.orderBy.current=null;
        this.date.current = [null, null];
        this.headCells = [
          
          {
            id: 'fullNameStudent',
            numeric: false,
            disablePadding: true,
            label: 'ФИО студента',
          },
          {
            id: 'fullNameProfessor',
            numeric: false,
            disablePadding: false,
            label: 'ФИО преподавателя',
          },
          {
            id: 'formOfControl',
            numeric: false,
            disablePadding: false,
            label: 'Форма контроля',
          },
          {
            id: 'discipline',
            numeric: false,
            disablePadding: false,
            label: 'Дисциплина',
          },
          {
            id: 'mark',
            numeric: true,
            disablePadding: false,
            label: 'Оценка',
          },
          {
              id: 'semester',
              numeric: true,
              disablePadding: false,
              label: 'Семестер',
          },
          {
              id: 'date',
              numeric: false,
              disablePadding: false,
              label: 'Дата',
          },
      ];
      
        this.pageSizes = [5, 10, 50];
    }

    createData(id, fullNameStudent, fullNameProfessor, formOfControl, discipline, mark, semester, date) {
        return {
            id,
            fullNameStudent,
            fullNameProfessor,
            formOfControl,
            discipline,
            mark,
            semester,
            date,
        };
      }

   
      

    getAll(params) {
        return axios.get("/api/achievements/", { params });
    }

    getRequestParams(page, pageSize, order, orderBy, date) {
        let params = {};
    
            
        if (page) {
          params["page"] = page;
        }
    
       
        if (pageSize) {
            params["size"] = pageSize;
        }

        if(orderBy) {
          var res;
          switch(orderBy) {
            case 'fullNameStudent':
              res = 'student.surname,student.name,student.patronymic';
              break;
            case 'fullNameProfessor':
              res = 'professor.surname,professor.name,professor.patronymic';
              break;
            case 'formOfControl':
              res = 'formOfControl.name';
              break;
            case 'discipline':
              res = 'discipline.name';
              break;
            default:
              res = orderBy;
              break;
          }
          if (order === 'desc') {
            res += ':desc';
          }
          else {
            res += ':asc';
          }
          params["sort"] = res;
          
        }

        if (date[0]) {
          params["dateFrom"] = format(date[0], "dd.MM.yyyy");
        }

        if (date[1]) {
          params["dateTo"] = format(date[1], "dd.MM.yyyy");
        }
        
        return params;
      }

    componentDidMount() {
        this.retrieveAchievements();
    }

    retrieveAchievements() {
        const { page, pageSize} = this.state;
        const order = this.order.current;
        const orderBy = this.orderBy.current;
        const date = this.date.current;
        const params = this.getRequestParams(page, pageSize, order, orderBy, date);
        

        this.getAll(params)
          .then((response) => {
            this.setState({
                achievements: response.data.achievements,
                totalItems: response.data.totalItems,
                totalPages: response.data.totalPages,
                selected: []
            });
        });
    }
    
    handlePageChange(event, value) {
        this.setState(
            {
            page: value,
            },
            () => {
            this.retrieveAchievements();
            }
        );
    }

    handlePageSizeChange(event) {
        this.setState(
            {
            pageSize: event.target.value,
            page: 0
            },
            () => {
            this.retrieveAchievements();
            }
    );
    }


    async remove(selected) {
      var body = [];
      selected.forEach((value) => {
        body.push({
          id: value
        });
      });
     
      await axios.delete(`/api/achievements/delete-by-ids`, {
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
      },
        data: body
      });
      this.retrieveAchievements();
  }

    handleSelectAllClick = (event) => {
        if (event.target.checked) {
          const newSelecteds = this.state.achievements.map((n) => n.id);
          this.setState({
            selected: newSelecteds
          });
          return;
        }
        this.setState({
          selected: []
        });
      };

      handleClick = (event, id) => {
        const selectedIndex = this.state.selected.indexOf(id);
        let newSelected = [];
    
        if (selectedIndex === -1) {
          newSelected = newSelected.concat(this.state.selected, id);
        } else if (selectedIndex === 0) {
          newSelected = newSelected.concat(this.state.selected.slice(1));
        } else if (selectedIndex === this.state.selected.length - 1) {
          newSelected = newSelected.concat(this.state.selected.slice(0, -1));
        } else if (selectedIndex > 0) {
          newSelected = newSelected.concat(
            this.state.selected.slice(0, selectedIndex),
            this.state.selected.slice(selectedIndex + 1),
          );
        }
        this.setState({
          selected: newSelected
        });
    }

    handleRequestSort = (event, property) => {
      const isAsc = this.orderBy.current === property && this.order.current === 'asc';
      this.order.current = isAsc ? 'desc' : 'asc';
      this.orderBy.current = property;
      
      this.retrieveAchievements()
    };

    isSelected(id) {
      if (this.state.selected.indexOf(id) !== -1) {
        return true;
      }
      return false;
    }

    handleDateChange(value) {
      console.log(value);
      this.date.current = value;
      
      this.retrieveAchievements();
    }
    
    render() {
        const {achievements, pageSize, selected, page, isLoading} = this.state;
        const orderBy = this.orderBy.current;
        const order = this.order.current;
        const numSelected = selected.length
     
        if (isLoading) {
            return <p>Loading...</p>;
        }

        const rows = achievements.map(achievement => {
            return(this.createData(achievement.id,
                achievement.student.surname + " " + achievement.student.name + " " + achievement.student.patronymic, 
                achievement.professor.surname + " " + achievement.professor.name + " " + achievement.professor.patronymic, achievement.formOfControl.name, 
                achievement.discipline.name, 
                achievement.mark, 
                achievement.semester,
                achievement.date));
        });

        const emptyRows = page > 0 ? pageSize - rows.length : 0;

        

        const createSortHandler = (property) => (event) => {
          this.handleRequestSort(event, property);
        };
        
        
        

        return (
            
            <div>
                <AppNavbar/>
                
                <Box sx={{ width: '100%' }}>
      <Paper sx={{ width: '100%', mt: 2 }}>
      <Toolbar
      sx={{
        pl: { sm: 2 },
        pr: { xs: 1, sm: 1 },
        ...(numSelected > 0 && {
          bgcolor: (theme) =>
            alpha(theme.palette.primary.main, theme.palette.action.activatedOpacity),
        }),
      }}
    >
      {numSelected > 0 ? (
        <Typography
          sx={{ flex: '1 1 100%' }}
          color="inherit"
          variant="subtitle1"
          component="div"
        >
          {numSelected} selected
        </Typography>
      ) : (
        <Typography
          sx={{ flex: '1 1 100%' }}
          variant="h6"
          id="tableTitle"
          component="div"
        >
          Достижения
        </Typography>
        
      )}
      <LocalizationProvider dateAdapter={AdapterDateFns}>
      <DateRangePicker  
        startText="Дата от"
        endText="Дата по"
        inputFormat="dd.MM.yyyy"
        mask="__.__.____"
        showToolbar={true}
        value={this.date.current}
        onChange={(newValue) =>{
          this.handleDateChange(newValue)
        }
        }
        renderInput={(startProps, endProps) => (
          <React.Fragment>
            <TextField {...startProps} />
            <Box sx={{ mx: 2 }}> to </Box>
            <TextField {...endProps} />
          </React.Fragment>
        )}
      />
    </LocalizationProvider>
      {numSelected > 0 ? (
        <Tooltip title="Delete">
          <IconButton onClick={() => this.remove(selected)}>
            <DeleteIcon />
          </IconButton>
        </Tooltip>
      ) : (
        <Tooltip title="Filter list">
          <IconButton>
            <FilterListIcon />
          </IconButton>
        </Tooltip>
      )}
    </Toolbar>
        <TableContainer>
          <Table
            sx={{ minWidth: 750 }}
            aria-labelledby="tableTitle"
            size={'medium'}
          >
           <TableHead>
          <TableRow>
            <TableCell padding="checkbox">
              <Checkbox
                color="primary"
                indeterminate={numSelected > 0 && numSelected < rows.length}
                checked={rows.length > 0 && numSelected === rows.length}
                onChange={this.handleSelectAllClick}
                inputProps={{
                  'aria-label': 'select all desserts',
                }}
              />
            </TableCell>
            {this.headCells.map((headCell) => (
              <TableCell
                key={headCell.id}
                
                padding={headCell.disablePadding ? 'none' : 'normal'}
                
              >
                <TableSortLabel
                  active={orderBy === headCell.id}
                  direction={orderBy === headCell.id ? order : 'asc'}
                  onClick={createSortHandler(headCell.id)}
                >
                  {headCell.label}
                  
                </TableSortLabel>
              </TableCell>
            ))}
          </TableRow>
        </TableHead>
            

            <TableBody>
              {/* if you don't need to support IE11, you can replace the `stableSort` call with:
                 rows.slice().sort(getComparator(order, orderBy)) */}
              {rows.map((row, index) => {
                  const isItemSelected = this.isSelected(row.id);
                  const labelId = `enhanced-table-checkbox-${index}`;

                  return (
                    <TableRow
                      hover
                      onClick={(event) =>this.handleClick(event, row.id)}
                      role="checkbox"
                      aria-checked={isItemSelected}
                      tabIndex={-1}
                      key={row.id}
                      selected={isItemSelected}
                    >
                      <TableCell padding="checkbox">
                        <Checkbox
                          color="primary"
                          checked={isItemSelected}
                          inputProps={{
                            'aria-labelledby': labelId,
                          }}
                        />
                      </TableCell>
                      <TableCell
                        component="th"
                        id={labelId}
                        scope="row"
                        padding="none"
                      >
                        {row.fullNameStudent}
                      </TableCell>
                      <TableCell >{row.fullNameProfessor}</TableCell>
                      <TableCell >{row.formOfControl}</TableCell>
                      <TableCell >{row.discipline}</TableCell>
                      <TableCell >{row.mark}</TableCell>
                      <TableCell >{row.semester}</TableCell>
                      <TableCell>{row.date}</TableCell>
                    </TableRow>
                  );
                })}
              {emptyRows > 0 && (
                <TableRow
                  style={{
                    height: (53) * emptyRows,
                  }}
                >
                  <TableCell colSpan={8} />
                </TableRow>
              )}
            </TableBody>
          </Table>
        </TableContainer>
        <TablePagination
          rowsPerPageOptions={this.pageSizes}
          component="div"
          count={this.state.totalItems}
          rowsPerPage={pageSize}
          page={page}
          onPageChange={this.handlePageChange}
          onRowsPerPageChange={this.handlePageSizeChange}
        />
      </Paper>
      
    </Box>
            </div>
            
        );
    }

}
export default AchievementList;