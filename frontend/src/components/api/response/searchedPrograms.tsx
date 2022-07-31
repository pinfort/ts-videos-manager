export interface ISearchedPrograms {
  programs: ISearchedProgram[];
}

export interface ISearchedProgram {
  id: number;
  name: string;
  executedFileId: number;
  status: SearchProgramsStatus;
  drops: number | null;
}

export enum SearchProgramsStatus {
  REGISTERED,
  COMPLETED,
  ERROR,
}
