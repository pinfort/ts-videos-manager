import React from 'react';
import { TableCell, TableHeaderCell } from '../../../../../ui/component/table/cell';
import { TableContentRow, TableRow } from '../../../../../ui/component/table/row';

export interface ProgramDetailRow extends TableRow {}

export type ProgramDetailTableContentRowProps = {
  children: Iterable<TableCell> | TableCell;
};

export function VideoFilesTableHeaderRow(): ProgramDetailRow {
  return (
        <TableContentRow className=''>
            <TableHeaderCell>
                ファイル名
            </TableHeaderCell>
        </TableContentRow>
  );
}

export function VideoFilesTableContentRow({ children }: ProgramDetailTableContentRowProps): ProgramDetailRow {
  return (
        <TableContentRow className=''>
            {children}
        </TableContentRow>
  );
}
