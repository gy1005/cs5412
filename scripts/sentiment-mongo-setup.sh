#!/bin/bash

mongo --host sentiment-mongodb-primary:27017 <<EOF
   var cfg = {
        "_id": "sentiment-rs",
        "members": [
            {
                "_id": 0,
                "host": "sentiment-mongodb-primary:27017"
            },
            {
                "_id": 1,
                "host": "sentiment-mongodb-secondary-0:27017"
            },
            {
                "_id": 2,
                "host": "sentiment-mongodb-secondary-1:27017"
            }
        ]
    };
    rs.initiate(cfg, { force: true });
    rs.reconfig(cfg, { force: true });
EOF