import React from 'react';
import { Link } from '../../component/link';
import { ContentPagerBackward } from './pagerBackward';
import { ContentPagerForward } from './pagerForward';
import { ContentPagerNumber, PagerNumber } from './pagerNumber';

export type PagerProps = {
  className?: string;
  style?: React.CSSProperties;
  forwardLink: string;
  backwardLink: string;
  links: Map<number, string>;
};

export interface Pager extends Link {}

function getPagerNumbers(links: Map<number, string>, className?: string, style?: React.CSSProperties): PagerNumber[] {
  const numbers: PagerNumber[] = [];
  links.forEach((link, page) => {
    numbers.push(
      <ContentPagerNumber name={page} href={link} className={className} style={style} />,
    );
  });
  return numbers;
}

export function ContentPager({ className, style, forwardLink, backwardLink, links }: PagerProps): Pager {
  return (
      <>
        <ContentPagerForward href={forwardLink} className={className} style={style} />
        {getPagerNumbers(links, className, style)}
        <ContentPagerBackward href={backwardLink} className={className} style={style} />
      </>
  );
}

ContentPager.defaultProps = {
  className: '',
  style: {},
};
