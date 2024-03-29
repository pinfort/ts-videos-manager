export interface ISearchedPrograms {
  programs: ISearchedProgram[];
}

export interface ISearchedProgram {
  id: number;
  name: string;
  executedFileId: number;
  status: SearchProgramsStatus;
  drops: number;
  size: number;
  recordedAt: string;
  channel: string,
  title: string,
  channelName: string,
  duration: number,
}

export enum SearchProgramsStatus {
  REGISTERED = 'REGISTERED',
  COMPLETED = 'COMPLETED',
  ERROR = 'ERROR',
}

export function searchProgramStatusToJapanese(status: SearchProgramsStatus): string {
  switch (status) {
    case SearchProgramsStatus.REGISTERED:
      return '準備中';
    case SearchProgramsStatus.COMPLETED:
      return '録画済み';
    case SearchProgramsStatus.ERROR:
      return 'エラー';
    default:
      return '異常';
  }
}
