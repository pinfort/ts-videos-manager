import React from 'react';

export type LinkProps = {
  children: React.ReactNode;
  className?: string;
  style?: React.CSSProperties;
  href: string;
};

export interface Link extends JSX.Element {}

export function ContentLink({ children, className, style, href }: LinkProps): Link {
  return (
          <a className={className} style={style} href={href}>
              {children}
          </a>
  );
}

ContentLink.defaultProps = {
  className: '',
  style: {},
};
