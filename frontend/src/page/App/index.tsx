/* eslint-disable react/jsx-no-bind */
/* eslint-disable @typescript-eslint/no-misused-promises */
import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
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
        <SearchForm onChange={setQuery} onSubmit={async () => {
          const currentOffset = offset;
          setOffset(0);
          // もともとoffsetが0だった場合、offsetが変化せずexecuteSearchが自動実行されないので手動で行う
          if (currentOffset === 0) {
            await executeSearch();
          }
        }}/>
      </header>
      <main>
        <ProgramsTable>
          {searchedPrograms.programs.map((program) => (
            <ProgramsTableContentRow>
              <TableContentCell>
                {program.id}
              </TableContentCell>
              <TableContentCell>
                <Link to={`programs/${program.id}`}>
                  {program.name}
                </Link>
              </TableContentCell>
              <TableContentCell>
                {program.executedFileId}
              </TableContentCell>
              <TableContentCell>
                {program.status}
              </TableContentCell>
              <TableContentCell>
                {program.drops ?? -1}
              </TableContentCell>
            </ProgramsTableContentRow>
          ))}
        </ProgramsTable>
      </main>
      <footer className='App-pager-wrapper'>
        <ContentPager className='App-pager-button' forwardLink='/' backwardLink='/' links={new Map<number, string>([[1, '/']])} offset={offset} limit={limit} setOffset={setOffset}/>
      </footer>
    </div>
  );
}

export default App;
