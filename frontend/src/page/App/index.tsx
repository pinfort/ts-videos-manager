/* eslint-disable react/jsx-no-bind */
/* eslint-disable @typescript-eslint/no-misused-promises */
import React, { useState } from 'react';
import './App.scss';
import { ProgramsTable } from './ui/block/ProgramsTable/programsTable';
import { ProgramsTableContentRow } from './ui/block/ProgramsTable/programsRow';
import { TableContentCell } from '../../ui/component/table/cell';
import { SearchForm } from './ui/block/searchForm/searchForm';
import { apiComponent } from '../../components/api';
import { ISearchedPrograms } from '../../components/api/response/searchedPrograms';

function App() {
  const [query, setQuery] = useState('');
  const [searchedPrograms, setSearchedPrograms] = useState<ISearchedPrograms>({ programs:[] });

  async function executeSearch() {
    await apiComponent.getPrograms(query).then((response) => {
      setSearchedPrograms(response);
    });
  }

  return (
    <div className="App">
      <header>
        <SearchForm onChange={setQuery} onSubmit={executeSearch}/>
      </header>
      <p>
        <ProgramsTable>
          {searchedPrograms.programs.map((program) => (
            <ProgramsTableContentRow>
              <TableContentCell>
                {program.id}
              </TableContentCell>
              <TableContentCell>
                {program.name}
              </TableContentCell>
              <TableContentCell>
                {program.executedFileId}
              </TableContentCell>
              <TableContentCell>
                {program.status}
              </TableContentCell>
            </ProgramsTableContentRow>
          ))}
        </ProgramsTable>
      </p>
    </div>
  );
}

export default App;
