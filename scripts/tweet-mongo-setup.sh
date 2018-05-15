#!/bin/bash

mongo --host tweet-mongodb-primary:27017 <<EOF
   var cfg = {
        "_id": "tweet-rs",
        "members": [
            {
                "_id": 0,
                "host": "tweet-mongodb-primary:27017"
            },
            {
                "_id": 1,
                "host": "tweet-mongodb-secondary-0:27017"
            },
            {
                "_id": 2,
                "host": "tweet-mongodb-secondary-1:27017"
            }
        ]
    };
    rs.initiate(cfg, { force: true });
    rs.reconfig(cfg, { force: true });
EOF