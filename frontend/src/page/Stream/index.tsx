/* eslint-disable jsx-a11y/media-has-caption */
import React from 'react';
import { useParams } from 'react-router-dom';

function Stream() {
  const urlParams = useParams<{ id: string }>();
  const videoFileId: number = parseInt(urlParams.id || '', 10);

  return (
    <div className="ProgramDetail">
      <main>
        <video controls preload='auto' width='256'>
            <source src={`/api/v1/video/${videoFileId}/stream`} type='video/mp4'/>
            Download the
            <a href={`/api/v1/video/${videoFileId}/stream`}>MP4</a>
            video.
        </video>
      </main>
    </div>
  );
}

export default Stream;
