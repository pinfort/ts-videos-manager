FROM node:16.14-slim

COPY . .

RUN yarn install --pure-lockfile
RUN yarn build

EXPOSE 3000

CMD [ "yarn", "start" ]
