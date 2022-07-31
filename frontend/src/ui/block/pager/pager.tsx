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
  offset: number;
  limit: number;
  setOffset: (offset: number) => void;
};

export interface Pager extends Link {}

function getPagerNumbers(links: Map<number, string>, setOffset: (offset: number) => void, limit: number, className?: string, style?: React.CSSProperties): PagerNumber[] {
  const numbers: PagerNumber[] = [];
  links.forEach((link, page) => {
    numbers.push(
      // eslint-disable-next-line @typescript-eslint/no-use-before-define
      <ContentPagerNumber name={page} href={link} className={className} style={style} onClick={(e)=>{e.preventDefault();onClick(setOffset, (page - 1) * limit);}} />,
    );
  });
  return numbers;
}

function onClick(setOffset: (offset: number) => void, offset: number): void {
  setOffset(offset);
}

export function ContentPager({ className, style, forwardLink, backwardLink, links, offset, limit, setOffset }: PagerProps): Pager {
  return (
      <>
        <ContentPagerForward href={forwardLink} className={className} style={style} onClick={(e)=>{e.preventDefault();onClick(setOffset, Math.max(offset - limit, 0));}} />
        {/* 現在のページ数を表示 */}
        <span className={className}>{offset === 0 ? 1 : Math.ceil(offset / limit) + 1}</span>
        {/* {getPagerNumbers(links, setOffset, limit, className, style)} */}
        <ContentPagerBackward href={backwardLink} className={className} style={style} onClick={(e)=>{e.preventDefault();onClick(setOffset, offset + limit);}} />
      </>
  );
}

ContentPager.defaultProps = {
  className: '',
  style: {},
};
