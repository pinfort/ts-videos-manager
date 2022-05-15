import React from 'react';
import { ContentLink, Link } from '../../component/link';

export type PagerForwardProps = {
  className?: string;
  style?: React.CSSProperties;
  href: string;
};

export interface PagerForward extends Link {}

export function ContentPagerForward({ className, style, href }: PagerForwardProps): PagerForward {
  return (
      <ContentLink href={href} className={className} style={style}>{'<<'}</ContentLink>
  );
}

ContentPagerForward.defaultProps = {
  className: '',
  style: {},
};
