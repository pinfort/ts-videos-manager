import React from 'react';

export type LinkProps = {
  children: React.ReactNode;
  className?: string;
  style?: React.CSSProperties;
  href: string;
  onClick: (event: React.MouseEvent<HTMLAnchorElement, MouseEvent>) => void;
};

export interface Link extends JSX.Element {}

export function ContentLink({ children, className, style, href, onClick }: LinkProps): Link {
  return (
          <a className={className} style={style} href={href} onClick={onClick}>
              {children}
          </a>
  );
}

ContentLink.defaultProps = {
  className: '',
  style: {},
};
