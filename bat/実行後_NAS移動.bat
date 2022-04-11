for /f "delims=" %%a in ('GetOutFiles all') do set FILES=%%a

cd /D "%~dp0"
cd ../../../

REM TODO: 実行コマンドを書く
