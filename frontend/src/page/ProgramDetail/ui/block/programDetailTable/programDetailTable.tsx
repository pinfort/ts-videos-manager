import React from 'react';
import { TableRow } from '../../../../../ui/component/table/row';
import { ContentTable } from '../../../../../ui/component/table/table';
import { ProgramDetailTableHeaderRow } from './programDetailRow';

export type ProgramDetailTableProps = {
  children: TableRow | TableRow[];
};

export function ProgramDetailTable({ children }: ProgramDetailTableProps): JSX.Element {
  return (
        <ContentTable className='' head={<ProgramDetailTableHeaderRow/>}>
            {children}
        </ContentTable>
  );
}
