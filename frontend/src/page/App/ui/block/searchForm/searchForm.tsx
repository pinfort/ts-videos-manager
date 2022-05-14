import React from 'react';
import { ContentButton } from '../../../../../ui/component/button/button';
import { ContentForm } from '../../../../../ui/component/form/form';
import { SearchInput } from '../../../../../ui/component/form/input';
import { ContentLabel } from '../../../../../ui/component/form/label';

export type SearchFormProps = {
  onChange: (value: string) => void
  onClick: (event: React.MouseEvent<HTMLButtonElement, MouseEvent>) => void;
};

export function SearchForm({ onChange, onClick }: SearchFormProps): JSX.Element {
  return (
        <ContentForm action='/' method='get'>
            <ContentLabel>
                <SearchInput name='q' onChange={onChange}/>
            </ContentLabel>
            <ContentButton type='button' onClick={onClick}>
                検索
            </ContentButton>
        </ContentForm>
  );
}
