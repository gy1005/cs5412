FROM python

ADD ./ /data

RUN python3 -m pip install pika textblob numpy requests
ENTRYPOINT ["python3", "/data/tweet-streaming-subscriber.py" ]