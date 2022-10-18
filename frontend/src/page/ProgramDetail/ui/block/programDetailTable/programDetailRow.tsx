import React from 'react';
import { TableCell, TableHeaderCell } from '../../../../../ui/component/table/cell';
import { TableContentRow, TableRow } from '../../../../../ui/component/table/row';

export interface ProgramDetailRow extends TableRow {}

export type ProgramDetailTableContentRowProps = {
  children: Iterable<TableCell>;
};

export function ProgramDetailTableHeaderRow(): ProgramDetailRow {
  return (
        <TableContentRow className=''>
            <TableHeaderCell>
                項目
            </TableHeaderCell>
            <TableHeaderCell>
                値
            </TableHeaderCell>
        </TableContentRow>
  );
}

export function ProgramDetailTableContentRow({ children }: ProgramDetailTableContentRowProps): ProgramDetailRow {
  return (
        <TableContentRow className=''>
            {children}
        </TableContentRow>
  );
}
