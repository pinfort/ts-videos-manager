import React from 'react';
import { TableRow } from './row';

export type TableProps = {
  head: TableRow;
  children: TableRow | TableRow[];
  className?: string;
  style?: React.CSSProperties;
};

export interface Table extends JSX.Element {}

export function ContentTable({ head, children, className, style }: TableProps): Table {
  return (
        <table className={className} style={style}>
            <thead>
                {head}
            </thead>
            <tbody>
                {children}
            </tbody>
        </table>
  );
}

ContentTable.defaultProps = {
  className: '',
  style: {},
};
