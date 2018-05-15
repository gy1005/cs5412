#Import the necessary methods from tweepy library
from tweepy.streaming import StreamListener
from tweepy import OAuthHandler
from tweepy import Stream

import threading 
import json
from time import sleep

import logging

import signal
import pika
import sys
from optparse import OptionParser

#Variables that contains the user credentials to access Twitter API 


class Publisher:
    EXCHANGE='tweet_exchange'
    TYPE='fanout'
    ROUTING_KEY = 'tweet_queue'

    def __init__(self, host, port):
        self._params = pika.connection.ConnectionParameters(
            host=host,
            port=port)
        self._conn = None
        self._channel = None

    def connect(self):
        if not self._conn or self._conn.is_closed:
            while True:
                try:
                    print("try connect")
                    self._conn = pika.BlockingConnection(self._params)
                    self._channel = self._conn.channel()
                    self._channel.exchange_declare(exchange=self.EXCHANGE,
                                                exchange_type=self.TYPE)
                    return
                except:
                    print("try connect error")
                    sleep(1)

    def _publish(self, msg):
        self._channel.basic_publish(exchange=self.EXCHANGE,
                                    routing_key="",
                                    body=json.dumps(msg).encode('utf-8'))
        logging.debug('message sent: %s', msg)

    def publish(self, msg):
        """Publish msg, reconnecting if necessary."""

        try:
            self._publish(msg)
        except pika.exceptions.ConnectionClosed:
            logging.debug('reconnecting to queue')
            self.connect()
            self._publish(msg)

    def close(self):
        if self._conn and self._conn.is_open:
            logging.debug('closing queue connection')
            self._conn.close()   




class StdOutListener(StreamListener):
    def __init__(self, match_id, team_id, publisher):
        StreamListener.__init__(self)
        self.publisher = publisher
        self.match_id = match_id
        self.team_id = team_id


    def on_data(self, data):
        
        data_json = json.loads(data)


        tweet = {}
        tweet["id"] = data_json["id"]
        tweet["text"] = data_json["text"]
        tweet["hashTags"] = []
        for item in data_json["entities"]["hashtags"]:
            tweet["hashTags"].append(item["text"])
        tweet["userId"] = data_json["user"]["id"]
        tweet["timestamp"] = int(data_json["timestamp_ms"])
        tweet["userName"] = data_json["user"]["name"]
        tweet["matchId"] = self.match_id
        tweet["teamId"] = self.team_id

        self.publisher.connect()
        self.publisher.publish(tweet)
        print ("teamId", self.team_id, "success")
        return True  
    


    def on_error(self, status_code):        
        if status_code == 420:
            sleep(0.5)
            print ("limit error")
        return False
    

    

def worker(match_id, team_id, keywords, publisher, access_token, access_token_secret, consumer_key, consumer_secret):
    #This handles Twitter authetification and the connection to Twitter Streaming API
 
    l = StdOutListener(match_id, team_id, publisher)
    auth = OAuthHandler(consumer_key, consumer_secret)
    auth.set_access_token(access_token, access_token_secret)    
    stream = Stream(auth, l)
    #This line filter Twitter Streams to capture data by the keywords: 'python', 'javascript', 'ruby'
    stream.filter(track=keywords, languages=['en'], async=True)
    return 0



def signal_handler(signal, frame):
    print('You pressed Ctrl+C!')
    sys.exit(0)

if __name__ == '__main__':

    parser = OptionParser()
    parser.add_option("-m", "--match", dest="match_id", help="monitoring match id", type="int")
    parser.add_option("-d", "--dir", dest="dir", help="input json dir", type="string")
    parser.add_option("-t", "--team", dest="team_id", help="monitoring team id", type="int")
    parser.add_option("-a", "--access_token", dest="access_token", help="access_token", type="string")
    parser.add_option("-A", "--access_token_secret", dest="access_token_secret", help="access_token_secret", type="string")
    parser.add_option("-c", "--consumer_key", dest="consumer_key", help="consumer_key", type="string")
    parser.add_option("-C", "--consumer_secret", dest="consumer_secret", help="consumer_secret", type="string")
    (options, args) = parser.parse_args()

    signal.signal(signal.SIGINT, signal_handler)

    # publisher = Publisher("rabbitmq", 5672) 
    publisher = Publisher("localhost", 20020) 

    with open(options.dir + "/matches.json", "r") as matches_file, open(options.dir + "/teams.json", "r") as teams_file:
        matches_json = json.load(matches_file)
        teams_json = json.load(teams_file)
        if str(options.match_id) in matches_json:
            print (teams_json[str(options.team_id)]["keywords"])
            t = threading.Thread(target=worker, args=(options.match_id, options.team_id, teams_json[str(options.team_id)]["keywords"], publisher, options.access_token, options.access_token_secret, options.consumer_key, options.consumer_secret))
            t.start()
            t.join()      
        else:
            logging.debug ("[error]: No such match!")
            sys.exit(1)




   


