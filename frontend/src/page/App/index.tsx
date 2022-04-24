import React from 'react';
import './App.scss';
import { ProgramsTable } from './ui/block/ProgramsTable/programsTable';
import { ProgramsTableContentRow } from './ui/block/ProgramsTable/programsRow';
import { TableContentCell } from '../../ui/component/table/cell';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <ProgramsTable>
          <ProgramsTableContentRow>
            <TableContentCell>
              id
            </TableContentCell>
            <TableContentCell>
              name
            </TableContentCell>
            <TableContentCell>
              executedFileId
            </TableContentCell>
            <TableContentCell>
              status
            </TableContentCell>
          </ProgramsTableContentRow>
        </ProgramsTable>
      </header>
    </div>
  );
}

export default App;
