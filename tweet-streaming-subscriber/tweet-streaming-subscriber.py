import pika
import time
import requests

import json
from textblob import TextBlob
import re
import string
import random as rd
import signal
import sys
import threading
import logging
import nltk


next_start_time = round(time.time()/60)*60000

def callback(ch, method, properties, body):
    global next_start_time
    global team_ids
    # try:

    tweet = json.loads(body.decode("utf-8"))
    
    sentiment = {}

    sentiment["timestamp"] =  tweet["timestamp"]
    sentiment["id"] = ''.join(rd.choice(string.ascii_uppercase + string.ascii_lowercase + string.digits,) for n in range(32))
    sentiment["tweetId"] = tweet["id"]
    sentiment["polarity"] = analize_sentiment(tweet["text"])
    sentiment["teamId"] = tweet["teamId"]
    sentiment["tweet"] = tweet
   

    headers = {"Content-Type": "application/json"}   
    
    r = requests.post("http://localhost:20010/sentiment/save", headers=headers, json=sentiment) 
    print("save sentiment", r.status_code, r.headers)      
    if  r.status_code != 200:
        ch.basic_reject(delivery_tag=method.delivery_tag, requeue=False)  
        return
    r = requests.post("http://localhost:20010/tweet/save", headers=headers, json=tweet)
    print("save tweet", r.status_code, r.headers)
    if  r.status_code != 200:
        ch.basic_reject(delivery_tag=method.delivery_tag, requeue=False)  
        return

    ch.basic_ack(delivery_tag = method.delivery_tag)

    time_scale = 60000
    
    if sentiment["timestamp"] > next_start_time + time_scale:       
        for i in range(30):
            t = threading.Thread(target=make_sentiment_response, args=(i, next_start_time, next_start_time + time_scale-1))
            t.start()
        
        for i in range(3):
            t = threading.Thread(target=make_hotspot, args=(i, next_start_time, next_start_time + time_scale-1))
            t.start()

        next_start_time += time_scale

    # except:
    #     print ("error")
    #     ch.basic_reject(delivery_tag=method.delivery_tag, requeue=False)  

def make_sentiment_response(team_id, strat_time, end_time):
    
    r = requests.get("http://localhost:20010/sentiment/team/" + str(team_id)+ "/start/" + str(strat_time) + "/end/"+ str(end_time))
    sentiment_response = {}
    sentiments = json.loads(r.content.decode("utf-8"))
    if sentiments:
        sentiments.sort(key=lambda s: s["polarity"])
        sentiment_response["posSentiments"] = []
        sentiment_response["negSentiments"] = []
        for i in range(min(5, len(sentiments))):
            if sentiments[i]["polarity"] < 0.0:
                sentiment_response["negSentiments"].append(sentiments[i])
            if sentiments[-(i + 1)]["polarity"] > 0.0:
                sentiment_response["posSentiments"].append(sentiments[-(i + 1)])
        polarity = 0.0
        pos_count = 0
        neg_count = 0
        for s in sentiments:
            if s["polarity"] > 0:
                pos_count += 1
            if s["polarity"] < 0:
                neg_count += 1
            polarity += s["polarity"]
        sentiment_response["polarity"] = polarity
        sentiment_response["teamId"] = team_id
        sentiment_response["timestamp"] = strat_time
        sentiment_response["id"] = ''.join(rd.choice(string.ascii_uppercase + string.ascii_lowercase + string.digits,) for n in range(32))
        sentiment_response["count"] = len(sentiments)
        sentiment_response["posCount"] = pos_count
        sentiment_response["negCount"] = neg_count

        headers = {"Content-Type": "application/json"}
        r = requests.post("http://localhost:20010/sentiment/saveresp", headers=headers, json=sentiment_response)
        print("save sentiment_response", r.status_code, r.headers)
        
    return 0
 


def get_words(batch):
    tokenized_text = []
    for tweet_text in batch:
        tokenized = nltk.word_tokenize(clean_tweet(tweet_text))
        tagged = nltk.pos_tag(tokenized)
        for tagged_word in tagged:
            if tagged_word[1] in ('NN','NNS','NNP','NNPS'):
                tokenized_text.append(tagged_word[0])
    return tokenized_text

def get_frequency(word_list):
    total = len(word_list)
    freq_list = []
    for count, elem in sorted(((word_list.count(e), e) for e in set(word_list)), reverse=True):
        freq_list.append((elem, float(count)/total)) 
    return freq_list

def clean_tweet(text):
    '''
    Utility function to clean the text in a tweet by removing 
    links and special characters using regex.
    '''
    text = text.replace("RT","")
    return ' '.join(re.sub("(@[A-Za-z0-9]+)|([^0-9A-Za-z \t])|(\w+:\/\/\S+)", " ", text).split())

def analize_sentiment(text):
    '''
    Utility function to classify the polarity of a tweet
    using textblob.
    '''
    analysis = TextBlob(clean_tweet(text))
    return analysis.sentiment.polarity
    # if analysis.sentiment.polarity > 0:
    #     return 1
    # elif analysis.sentiment.polarity == 0:
    #     return 0
    # else:
    #     return -1


def signal_handler(signal, frame):
    print('You pressed Ctrl+C!')
    sys.exit(0)

def make_hotspot(match_id, strat_time, end_time):
    r = requests.get("http://localhost:20010/tweet/api/match/1/between/" + str(strat_time) + "/and/" + str(end_time))
    content = json.loads(r.text)   
    if content: 
        batch = []
        for tweet in content:
            batch.append(tweet['text'])
        word_list = get_words(batch)
        freq_list = get_frequency(word_list)
        freq_list = freq_list[:min(len(freq_list), 10)]
        hotspot = {}
        hotspot["id"] = ''.join(rd.choice(string.ascii_uppercase + string.ascii_lowercase + string.digits,) for n in range(32))
        hotspot["matchId"] = match_id
        hotspot["timestamp"] = strat_time
        keywords = []
        for item in freq_list:
            keyword = {}
            keyword["text"] = item[0]
            keyword["freq"] = item[1]
            keywords.append(keyword)
        hotspot["keywords"] = keywords
        headers = {"Content-Type": "application/json"}

        r = requests.post("http://localhost:20010/hotspot/save", headers=headers, json=hotspot)
        print("save hotspot", r.status_code, r.headers)



signal.signal(signal.SIGINT, signal_handler)



# connection = pika.BlockingConnection(pika.ConnectionParameters(host='localhost', port=20013))
while True:
    try:
        # connection = pika.BlockingConnection(pika.ConnectionParameters(host='rabbitmq', port=5672))
        connection = pika.BlockingConnection(pika.ConnectionParameters(host='localhost', port=20020))
        channel = connection.channel()
        channel.exchange_declare(exchange='tweet_exchange', exchange_type='fanout')

        channel.queue_declare(queue="tweet_queue", durable=True)
        channel.queue_bind(exchange='tweet_exchange', queue='tweet_queue')
        print(' [*] Waiting for messages. To exit press CTRL+C')

        channel.basic_qos(prefetch_count=1)
        channel.basic_consume(callback, queue='tweet_queue')

        channel.start_consuming()
    except:
        print ('error')
        # avoid rapid reconnection on longer RMQ server outage
        time.sleep(1)
