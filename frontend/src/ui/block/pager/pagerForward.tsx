import React from 'react';
import { ContentLink, Link } from '../../component/link';

export type PagerForwardProps = {
  className?: string;
  style?: React.CSSProperties;
  href: string;
  onClick: (event: React.MouseEvent<HTMLAnchorElement, MouseEvent>) => void;
};

export interface PagerForward extends Link {}

export function ContentPagerForward({ className, style, href, onClick }: PagerForwardProps): PagerForward {
  return (
      <ContentLink href={href} className={className} style={style} onClick={onClick}>{'<<'}</ContentLink>
  );
}

ContentPagerForward.defaultProps = {
  className: '',
  style: {},
};
