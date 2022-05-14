import axios, { AxiosInstance } from 'axios';

export class ApiConfiguration {
  public static readonly rootUrl: string = '/api/v1';

  public static readonly programsUrl: string = `${ApiConfiguration.rootUrl  }/programs`;
}

function apiFactory(): AxiosInstance {
  return axios.create({
    baseURL: ApiConfiguration.rootUrl,
    headers: {
      'Content-Type': 'application/json',
    },
    responseType: 'json',
  });
}

export const api = apiFactory();
