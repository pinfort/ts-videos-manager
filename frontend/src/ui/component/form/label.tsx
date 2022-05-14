import React from 'react';

export type LabelProps = {
  children: React.ReactNode;
  className?: string;
  style?: React.CSSProperties;
};

export interface Label extends JSX.Element {}

export function ContentLabel({ children, className, style }: LabelProps): Label {
  return (
          <label className={className} style={style}>
              {children}
          </label>
  );
}

ContentLabel.defaultProps = {
  className: '',
  style: {},
};
