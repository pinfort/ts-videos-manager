import React from 'react';
import { TableHeaderCell, TableCell } from '../../../../../ui/component/table/cell';
import { TableContentRow, TableRow } from '../../../../../ui/component/table/row';

export type ProgramsTableContentRowProps = {
  children: Iterable<TableCell>;
};

export interface ProgramsRow extends TableRow {}

export function ProgramsTableHeaderRow(): ProgramsRow {
  return (
        <TableContentRow className=''>
            <TableHeaderCell>
                id
            </TableHeaderCell>
            <TableHeaderCell>
                name
            </TableHeaderCell>
            <TableHeaderCell>
                executedFileId
            </TableHeaderCell>
            <TableHeaderCell>
                status
            </TableHeaderCell>
        </TableContentRow>
  );
}

export function ProgramsTableContentRow({ children }: ProgramsTableContentRowProps): ProgramsRow {
  return (
        <TableContentRow className=''>
            {children}
        </TableContentRow>
  );
}
