import React from 'react';

export type FormProps = {
  children: React.ReactNode;
  className?: string;
  action: string;
  method: 'get' | 'post' | 'put' | 'delete';
  style?: React.CSSProperties;
  name?: string;
};

export interface Form extends JSX.Element {}

export function ContentForm({ children, className, style, name, method, action }: FormProps): Form {
  return (
        <form className={className} style={style} name={name} method={method} action={action}>
            {children}
        </form>
  );
}

ContentForm.defaultProps = {
  className: '',
  style: {},
  name: '',
};
