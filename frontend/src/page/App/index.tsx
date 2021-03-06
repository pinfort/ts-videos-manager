/* eslint-disable react/jsx-no-bind */
/* eslint-disable @typescript-eslint/no-misused-promises */
import React, { useEffect, useState } from 'react';
import './App.scss';
import { ProgramsTable } from './ui/block/ProgramsTable/programsTable';
import { ProgramsTableContentRow } from './ui/block/ProgramsTable/programsRow';
import { TableContentCell } from '../../ui/component/table/cell';
import { SearchForm } from './ui/block/searchForm/searchForm';
import { apiComponent } from '../../components/api';
import { ISearchedPrograms } from '../../components/api/response/searchedPrograms';
import { ContentPager } from '../../ui/block/pager/pager';

function App() {
  const [query, setQuery] = useState('');
  const [limit, setLimit] = useState(10);
  const [offset, setOffset] = useState(0);
  const [searchedPrograms, setSearchedPrograms] = useState<ISearchedPrograms>({ programs:[] });

  async function executeSearch() {
    await apiComponent.getPrograms(query, limit, offset).then((response) => {
      setSearchedPrograms(response);
    });
  }

  useEffect(() => {
    // eslint-disable-next-line @typescript-eslint/no-floating-promises
    (async () => {
      await executeSearch();
    })();
  }, [limit, offset]);

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
      <footer>
        <ContentPager forwardLink='/' backwardLink='/' links={new Map<number, string>([[1, '/']])} offset={offset} limit={limit} setOffset={setOffset}/>
      </footer>
    </div>
  );
}

export default App;
