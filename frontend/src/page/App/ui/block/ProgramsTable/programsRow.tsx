import React from 'react';
import { TableHeaderCell, TableCell, TableContentCell } from '../../../../../ui/component/table/cell';
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
            <TableContentCell>
                drops
            </TableContentCell>
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
