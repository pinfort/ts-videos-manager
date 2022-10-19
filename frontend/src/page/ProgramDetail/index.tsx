/* eslint-disable react/jsx-no-bind */
/* eslint-disable @typescript-eslint/no-misused-promises */
import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { apiComponent } from '../../components/api';
import { IProgramDetail, ProgramsStatus } from '../../components/api/response/programDetail';
import { TableContentCell } from '../../ui/component/table/cell';
import { ProgramDetailTableContentRow } from './ui/block/programDetailTable/programDetailRow';
import { ProgramDetailTable } from './ui/block/programDetailTable/programDetailTable';
import { VideoFilesTableContentRow } from './ui/block/videoFilesTable/videoFileRow';
import { VideoFilesTable } from './ui/block/videoFilesTable/videoFileTable';

function ProgramDetail() {
  const [programDetail, setProgramDetail] = useState<IProgramDetail>({
    program:{
      id:1,
      name:'loading...',
      executedFileId:1,
      status:ProgramsStatus.REGISTERED,
      drops: null,
    },
    videoFiles: [],
  });

  const urlParams = useParams<{ id: string }>();
  const programId: number = parseInt(urlParams.id || '', 10);

  async function fetchProgramDetail() {
    await apiComponent.getProgramDetail(programId).then((response) => {
      setProgramDetail(response);
    }).catch((error) => {
      setProgramDetail({
        program:{
          id:400,
          name:'error...',
          executedFileId:0,
          status:ProgramsStatus.REGISTERED,
          drops: null,
        },
        videoFiles: [],
      });
    });
  }

  useEffect(() => {
    if (!Number.isNaN(programId)) {
      // eslint-disable-next-line @typescript-eslint/no-floating-promises
      (async () => {
        await fetchProgramDetail();
      })();
    }
  }, [programId]);

  return (
    <div className="ProgramDetail">
      <main>
        <ProgramDetailTable>
          <ProgramDetailTableContentRow>
            <TableContentCell>
              番号
            </TableContentCell>
            <TableContentCell>
              {programDetail.program.id}
            </TableContentCell>
          </ProgramDetailTableContentRow>
          <ProgramDetailTableContentRow>
            <TableContentCell>
              ファイル名
            </TableContentCell>
            <TableContentCell>
              {programDetail.program.name}
            </TableContentCell>
          </ProgramDetailTableContentRow>
          <ProgramDetailTableContentRow>
            <TableContentCell>
              executedFileId
            </TableContentCell>
            <TableContentCell>
              {programDetail.program.executedFileId}
            </TableContentCell>
          </ProgramDetailTableContentRow>
          <ProgramDetailTableContentRow>
            <TableContentCell>
              状態
            </TableContentCell>
            <TableContentCell>
              {ProgramsStatus[programDetail.program.status]}
            </TableContentCell>
          </ProgramDetailTableContentRow>
          <ProgramDetailTableContentRow>
            <TableContentCell>
              ドロップ数
            </TableContentCell>
            <TableContentCell>
              {programDetail.program.drops || 0}
            </TableContentCell>
          </ProgramDetailTableContentRow>
        </ProgramDetailTable>
        <hr/>
        <VideoFilesTable>
          {programDetail.videoFiles.map((videoFile) => (
            <VideoFilesTableContentRow>
              <TableContentCell>
                {videoFile.file}
              </TableContentCell>
            </VideoFilesTableContentRow>
          ))}
        </VideoFilesTable>
      </main>
    </div>
  );
}

export default ProgramDetail;
