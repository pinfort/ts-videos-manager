import React from 'react';

export type InputProps = {
  className?: string;
  style?: React.CSSProperties;
  name?: string;
};

export interface Input extends JSX.Element {}

export function SearchInput({ className, style, name }: InputProps): Input {
  return (
          <input className={className} style={style} name={name} type='search'/>
  );
}

SearchInput.defaultProps = {
  className: '',
  style: {},
  name: '',
};
