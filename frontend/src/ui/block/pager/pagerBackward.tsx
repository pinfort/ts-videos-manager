import React from 'react';
import { ContentLink, Link } from '../../component/link';

export type PagerBackwardProps = {
  className?: string;
  style?: React.CSSProperties;
  href: string;
};

export interface PagerBackward extends Link {}

export function ContentPagerBackward({ className, style, href }: PagerBackwardProps): PagerBackward {
  return (
      <ContentLink href={href} className={className} style={style}>{'>>'}</ContentLink>
  );
}

ContentPagerBackward.defaultProps = {
  className: '',
  style: {},
};
