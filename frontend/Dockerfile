FROM node:19-alpine
WORKDIR /app
COPY package*.json .
RUN npm i --silent
COPY . .
RUN npm run build
EXPOSE 3000
CMD ["npm", "start"]