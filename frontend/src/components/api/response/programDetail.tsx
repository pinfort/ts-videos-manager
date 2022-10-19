export interface IProgramDetail {
  program: IProgram;
  videoFiles: IVideoFile[];
}

export interface IProgram {
  id: number;
  name: string;
  executedFileId: number;
  status: ProgramsStatus;
  drops: number | null;
}

export enum ProgramsStatus {
  REGISTERED,
  COMPLETED,
  ERROR,
}

export interface IVideoFile {
  id: number;
  splittedFileId: number;
  file: string;
  size: number;
  mime: string;
  encoding: string;
  status: VideoFileStatus;
}

export enum VideoFileStatus {
  REGISTERED,
  ENCODE_SUCCESS,
  FILE_MOVED,
}
