import React from 'react';
import { ContentButton } from '../../../../../ui/component/button/button';
import { ContentForm } from '../../../../../ui/component/form/form';
import { SearchInput } from '../../../../../ui/component/form/input';
import { ContentLabel } from '../../../../../ui/component/form/label';

export function SearchForm(): JSX.Element {
  return (
        <ContentForm action='/' method='get'>
            <ContentLabel>
                <SearchInput name='q'/>
            </ContentLabel>
            <ContentButton type='submit'>
                検索
            </ContentButton>
        </ContentForm>
  );
}
