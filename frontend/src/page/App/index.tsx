import React from 'react';
import './App.scss';
import { ProgramsTable } from './ui/block/ProgramsTable/programsTable';
import { ProgramsTableContentRow } from './ui/block/ProgramsTable/programsRow';
import { TableContentCell } from '../../ui/component/table/cell';
import { SearchForm } from './ui/block/searchForm/searchForm';


function App() {
  return (
    <div className="App">
      <header>
        <SearchForm/>
      </header>
      <p>
        <ProgramsTable>
          <ProgramsTableContentRow>
            <TableContentCell>
              id
            </TableContentCell>
            <TableContentCell>
              name
            </TableContentCell>
            <TableContentCell>
              status
            </TableContentCell>
          </ProgramsTableContentRow>
        </ProgramsTable>
      </p>
    </div>
  );
}

export default App;
