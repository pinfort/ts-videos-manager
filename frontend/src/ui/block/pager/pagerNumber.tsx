import React from 'react';
import { ContentLink, Link } from '../../component/link';

export type PagerNumberProps = {
  className?: string;
  style?: React.CSSProperties;
  name?: number;
  href: string;
};

export interface PagerNumber extends Link {}

export function ContentPagerNumber({ className, style, name, href }: PagerNumberProps): PagerNumber {
  return (
      <ContentLink href={href} className={className} style={style}>{name}</ContentLink>
  );
}

ContentPagerNumber.defaultProps = {
  className: '',
  style: {},
  name: '',
};
