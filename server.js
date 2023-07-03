var kafka = require('kafka-node');
var Consumer = kafka.Consumer;
var client = new kafka.KafkaClient({ kafkaHost: 'localhost:9092' });

// Create a Kafka consumer
var consumer = new Consumer(client, [{ topic: 'mytopic3' }], { groupId: 'mygroup' });

// Handle incoming messages
consumer.on('message', function (message) {
  console.log('Received message:', message.value);
});

// Handle errors
consumer.on('error', function (error) {
  console.error('Consumer error:', error);
});
