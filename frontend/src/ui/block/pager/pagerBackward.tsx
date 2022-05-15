import React from 'react';
import { ContentLink, Link } from '../../component/link';

export type PagerBackwardProps = {
  className?: string;
  style?: React.CSSProperties;
  href: string;
  onClick: (event: React.MouseEvent<HTMLAnchorElement, MouseEvent>) => void;
};

export interface PagerBackward extends Link {}

export function ContentPagerBackward({ className, style, href, onClick }: PagerBackwardProps): PagerBackward {
  return (
      <ContentLink href={href} className={className} style={style} onClick={onClick}>{'>>'}</ContentLink>
  );
}

ContentPagerBackward.defaultProps = {
  className: '',
  style: {},
};
