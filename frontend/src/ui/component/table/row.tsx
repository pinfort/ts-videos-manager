import React from 'react';
import { TableCell } from './cell';

export type TableRowProps = {
  children: TableCell | Iterable<TableCell>;
  className?: string;
  style?: React.CSSProperties;
};

export interface TableRow extends JSX.Element {}

export function TableContentRow({ children, className, style }: TableRowProps): TableRow {
  return (
        <tr className={className} style={style}>
            {children}
        </tr>
  );
}

TableContentRow.defaultProps = {
  className: '',
  style: {},
};
