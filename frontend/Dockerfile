FROM node:16.14-slim as build
ENV NODE_ENV production

WORKDIR /opt/app

COPY package.json yarn.lock ./
RUN yarn install --pure-lockfile

COPY . .
RUN yarn build

FROM nginx:1.21

COPY --from=build /opt/app/build /var/www/html

WORKDIR /usr/share/nginx/html
CMD ["nginx", "-g", "daemon off;"]
