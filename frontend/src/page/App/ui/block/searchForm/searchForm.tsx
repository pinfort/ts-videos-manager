import React from 'react';
import { ContentButton } from '../../../../../ui/component/button/button';
import { ContentForm } from '../../../../../ui/component/form/form';
import { SearchInput } from '../../../../../ui/component/form/input';
import { ContentLabel } from '../../../../../ui/component/form/label';

export type SearchFormProps = {
  onChange: (value: string) => void
  onSubmit: () => void;
};

export function SearchForm({ onChange, onSubmit }: SearchFormProps): JSX.Element {
  return (
        <ContentForm onSubmit={onSubmit}>
            <ContentLabel>
                <SearchInput name='q' onChange={onChange}/>
            </ContentLabel>
            <ContentButton type='submit'>
                検索
            </ContentButton>
        </ContentForm>
  );
}
