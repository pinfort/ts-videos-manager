import React from 'react';

export type InputProps = {
  className?: string;
  style?: React.CSSProperties;
  name?: string;
  onChange: (value: string) => void;
};

export interface Input extends JSX.Element {}

export function SearchInput({ className, style, name, onChange }: InputProps): Input {
  return (
          <input className={className} style={style} name={name} onChange={(e) => onChange(e.target.value)} type='search'/>
  );
}

SearchInput.defaultProps = {
  className: '',
  style: {},
  name: '',
};
