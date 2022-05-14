export interface ISearchedPrograms {
  programs: ISearchedProgram[];
}

export interface ISearchedProgram {
  id: number;
  name: string;
  executedFileId: number;
  status: SearchProgramsStatus;
}

export enum SearchProgramsStatus {
  REGISTERED,
  COMPLETED,
  ERROR,
}
