import React from 'react';

export type ButtonProps = {
  children: React.ReactNode;
  className?: string;
  style?: React.CSSProperties;
  type: 'button' | 'submit' | 'reset';
  name?: string;
};

export interface Button extends JSX.Element {}

export function ContentButton({ children, className, style, type, name }: ButtonProps): Button {
  return (
        // eslint-disable-next-line react/button-has-type
        <button className={className} style={style} type={type} name={name}>
            {children}
        </button>
  );
}

ContentButton.defaultProps = {
  className: '',
  style: {},
  name: '',
};
