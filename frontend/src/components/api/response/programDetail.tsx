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
  REGISTERED = 'REGISTERED',
  COMPLETED = 'COMPLETED',
  ERROR = 'ERROR',
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
  REGISTERED = 'REGISTERED',
  ENCODE_SUCCESS = 'ENCODE_SUCCESS',
  FILE_MOVED = 'FILE_MOVED',
}

export function programsStatusToJapanese(status: ProgramsStatus): string {
  switch (status) {
    case ProgramsStatus.REGISTERED:
      return '準備中';
    case ProgramsStatus.COMPLETED:
      return '録画済み';
    case ProgramsStatus.ERROR:
      return 'エラー';
    default:
      return '異常';
  }
}
