FROM python:3.6

ADD ./ /data

RUN python3 -m pip install pika tweepy
ENTRYPOINT ["python3", "/data/tweet-streaming-publisher.py", "-m",  "1",  "-d",  "/data/static-data"]