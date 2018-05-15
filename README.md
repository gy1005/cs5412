# NBAfans: Tweet sentiment analysis system
Tweet sentiment analysis system for NBA matches

## Requirements
- Python 3.4+
-- with pika, requests, textblob, nltk, tweepy
- docker-ce 18.04
- docker-compose 1.21.1
- Node.js
- free ports from 20000 to 20050

## Deployment
- Set up docker containers, including all spring boot microservices, zuul, eureka, mongoDB, rebbitMQ and redis. The address of Zuul API Gateway is localhost:20010.
 `docker-compose up`
- Run tweet-steaming-publisher and tweet-steaming-subcriber after all docker containers are correctly set up.
`./scripts/run-tweet-streaming.sh`
- Run the Node.js frontend. View the webpage on localhost:3000
`cd ./frontend && npm i && node app.js`