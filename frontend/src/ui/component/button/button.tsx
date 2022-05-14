import React from 'react';

export type ButtonProps = {
  children: React.ReactNode;
  className?: string;
  style?: React.CSSProperties;
  type: 'button' | 'submit' | 'reset';
  name?: string;
  onClick: (event: React.MouseEvent<HTMLButtonElement, MouseEvent>) => void;
};

export interface Button extends JSX.Element {}

export function ContentButton({ children, className, style, type, name, onClick }: ButtonProps): Button {
  return (
        // eslint-disable-next-line react/button-has-type
        <button className={className} style={style} type={type} name={name} onClick={onClick}>
            {children}
        </button>
  );
}

ContentButton.defaultProps = {
  className: '',
  style: {},
  name: '',
};
