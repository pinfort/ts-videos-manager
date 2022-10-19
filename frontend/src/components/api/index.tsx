/* eslint-disable class-methods-use-this */
import { ApiConfiguration, api } from '../../config/api-configuration';
import { IProgramDetail } from './response/programDetail';
import { ISearchedPrograms } from './response/searchedPrograms';

export class Api {
  public async getPrograms(query: string, limit: number, offset: number): Promise<ISearchedPrograms> {
    const url = new URL(ApiConfiguration.programsUrl);
    url.searchParams.set('name', query);
    url.searchParams.set('limit', limit.toString());
    url.searchParams.set('offset', offset.toString());
    const response = await api.get<ISearchedPrograms>(url.toString());
    return response.data;
  }

  public async getProgramDetail(programId: number): Promise<IProgramDetail> {
    const url = new URL(`${ApiConfiguration.programsUrl}/${programId}`);
    const response = await api.get<IProgramDetail>(url.toString());
    return response.data;
  }
}

export const apiComponent = new Api();
