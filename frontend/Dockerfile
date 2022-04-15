FROM node:16.14-slim as build

WORKDIR /opt/app
COPY . .

RUN yarn install --pure-lockfile
RUN yarn build

FROM nginx:1.21

COPY --from=build /opt/app/build /var/www/html

WORKDIR /usr/share/nginx/html
CMD ["nginx", "-g", "daemon off;"]
