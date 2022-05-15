import React from 'react';

export type FormProps = {
  children: React.ReactNode;
  className?: string;
  style?: React.CSSProperties;
  name?: string;
  onSubmit: () => void;
};

export interface Form extends JSX.Element {}

export function ContentForm({ children, className, style, name, onSubmit }: FormProps): Form {
  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    onSubmit();
  };
  return (
        <form className={className} style={style} name={name} onSubmit={handleSubmit}>
            {children}
        </form>
  );
}

ContentForm.defaultProps = {
  className: '',
  style: {},
  name: '',
};
