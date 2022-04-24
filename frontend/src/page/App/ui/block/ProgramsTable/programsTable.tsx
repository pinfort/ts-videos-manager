import React from 'react';
import { TableRow } from '../../../../../ui/component/table/row';
import { ProgramsTableHeaderRow } from './programsRow';
import { ContentTable } from '../../../../../ui/component/table/table';

export type ProgramsTableProps = {
  children: TableRow | TableRow[];
};

export function ProgramsTable({ children }: ProgramsTableProps): JSX.Element {
  return (
        <ContentTable className='' head={<ProgramsTableHeaderRow/>}>
            {children}
        </ContentTable>
  );
}
