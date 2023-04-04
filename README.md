#ActiveMQ Lab 2 with docker support
***
##Prerequisites
To run application you should have already launched instance of ActiveMQ on your local machine. You can use symptoma/activemq
image to run ActiveMQ in the separate docker container.

Command to pull symptoma/activemq:
````
docker pull symptoma/activemq:latest
````
Command to run symptoma/activemq image:
````
echo Starting activemq docker contaier
docker run -it \
-p 61616:61616 \
-p 8161:8161 \
-e ACTIVEMQ_DISALLOW_WEBCONSOLE=false \
-e ACTIVEMQ_USERNAME=user \
-e ACTIVEMQ_PASSWORD=**** \
-e ACTIVEMQ_WEBADMIN_USERNAME=admin \
-e ACTIVEMQ_WEBADMIN_PASSWORD=***** \
symptoma/activemq:latest
````
To verify that ActiveMQ server was launched successfully you can go to `localhost:8161`. If everything is ok, you'll see the home page of ActiveMQ.
***
##Running `active-mq-lab2` in the docker container
First you have to build the docker image. To do this use the next command:
````
docker build -t active-mq-lab2:v1 .
````
To run docker container use the next command:
````
docker run -it -e OPERATION=<OPERATION> -e NUM_OF_THREADS=<NUM_OF_THREADS> active-mq-lab2:v1
````
As you can see, it requires passing 2 env variables:
- **OPERATION** - name of operation to run (can be 'producer' or 'consumer')
- **NUM_OF_THREADS** - number of threads for producer or consumer
