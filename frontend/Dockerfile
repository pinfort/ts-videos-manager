FROM node:lts-slim as build

WORKDIR /opt/app

COPY package.json yarn.lock ./
RUN yarn install --pure-lockfile

COPY . .
RUN yarn build

FROM nginx:stable

COPY --from=build /opt/app/build /var/www/html

WORKDIR /usr/share/nginx/html
CMD ["nginx", "-g", "daemon off;"]
