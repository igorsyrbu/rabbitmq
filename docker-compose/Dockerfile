FROM rabbitmq:3.8.17-management

RUN rabbitmq-plugins enable rabbitmq_federation
RUN rabbitmq-plugins enable rabbitmq_federation_management

COPY rabbitmq.config /etc/rabbitmq/rabbitmq.conf
COPY definitions.json /etc/rabbitmq/definitions.json
COPY .erlang.cookie /var/lib/rabbitmq/.erlang.cookie
