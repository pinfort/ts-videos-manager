import React from 'react';

export type TableCellProps = {
  children: React.ReactNode;
  className?: string;
  style?: React.CSSProperties;
};

export interface TableCell extends JSX.Element {}

export function TableHeaderCell({ children, className, style }: TableCellProps): TableCell {
  return (
        <th className={className} style={style}>
            {children}
        </th>
  );
}

TableHeaderCell.defaultProps = {
  className: '',
  style: {},
};

export function TableContentCell({ children, className, style }: TableCellProps): TableCell {
  return (
        <td className={className} style={style}>
            {children}
        </td>
  );
}

TableContentCell.defaultProps = {
  className: '',
  style: {},
};
