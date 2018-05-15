#!/bin/bash

access_token_0="767403987637272576-Xz5FU291Shxcao2tDXgIsxFhFoMoqzT"
access_token_secret_0="YxdprLLLC4ne0Ev71tdtea91GQE4Tw4MiSsWO61ch1cm5"
consumer_key_0="VkWhWnsd07ynDXJcLzk09I731"
consumer_secret_0="6lWD2xOCKlvk4yDb1AHcmtNYHujI9EmEYWyBpiUEzHF9EemMej"

access_token_1="888474416056856577-DSOv5BNh8jQM2OPgsLyetw9Fn4fdN0K"
access_token_secret_1="iBYk27SfIPDwgRnXmdNBWyh8l3BxKHtfMWmvuC0rijmvI"
consumer_key_1="3jNROAF2CAljySYNEsaWpFXNB"
consumer_secret_1="0aVcOATF0pkkjOsAABBWWqzj0iS4xyZiIlScRRqQwFzHxfLoPR"

python3 ../tweet-streaming-publisher/tweet-streaming-publisher.py -m 1 -t 7 -d ../tweet-streaming-publisher/static-data -a $access_token_0 -A $access_token_secret_0 -c $consumer_key_0 -C $consumer_secret_0 &
python3 ../tweet-streaming-publisher/tweet-streaming-publisher.py -m 1 -t 25 -d ../tweet-streaming-publisher/static-data -a $access_token_1 -A $access_token_secret_1 -c $consumer_key_1 -C $consumer_secret_1 
python3 ../tweet-streaming-subscriber/tweet-streaming-subscriber.py
