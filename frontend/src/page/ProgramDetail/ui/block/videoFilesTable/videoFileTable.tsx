import React from 'react';
import { TableRow } from '../../../../../ui/component/table/row';
import { ContentTable } from '../../../../../ui/component/table/table';
import { VideoFilesTableHeaderRow } from './videoFileRow';

export type VideoFileTableProps = {
  children: TableRow | TableRow[];
};

export function VideoFilesTable({ children }: VideoFileTableProps): JSX.Element {
  return (
        <ContentTable className='' head={<VideoFilesTableHeaderRow/>}>
            {children}
        </ContentTable>
  );
}
