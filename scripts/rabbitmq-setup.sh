#!/bin/bash

sleep 5s

rabbitmqctl -n rabbit@rabbitmq-1 stop_app
rabbitmqctl -n rabbit@rabbitmq-1 join_cluster rabbit@rabbitmq-0
rabbitmqctl -n rabbit@rabbitmq-1 start_app

rabbitmqctl -n rabbit@rabbitmq-2 stop_app
rabbitmqctl -n rabbit@rabbitmq-2 join_cluster rabbit@rabbitmq-0
rabbitmqctl -n rabbit@rabbitmq-2 start_app