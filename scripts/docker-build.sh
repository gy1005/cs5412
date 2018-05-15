#!/bin/bash

cd ../sentiment-service
docker build -t yg397/sentiment-service .


cd ../tweet-service
docker build -t yg397/tweet-service .

cd ../zuul-service
docker build -t yg397/zuul-service .

cd ../eureka-service
docker build -t yg397/eureka-service .

cd ../hotspot-service
docker build -t yg397/hotspot-service .

# cd ../tweet-steaming-publisher
# docker build -t yg397/tweet-streaming-publisher .

# cd ../tweet-streaming-subscriber
# docker build -t yg397/tweet-streaming-subscriber .
