var kafka = require('kafka-node');
var Consumer = kafka.Consumer;
var Producer = kafka.Producer;
var WebSocket = require('ws');

var client = new kafka.KafkaClient({ kafkaHost: 'localhost:9092' });
var producer = new Producer(client);

// Create a Kafka consumer
var consumer = new Consumer(client, [{ topic: 'mytopic3' }], { groupId: 'mygroup' });

// WebSocket server configuration
var wss = new WebSocket.Server({ port: 9999 });

// Store WebSocket clients
var clients = new Set();

// Handle incoming WebSocket connections
wss.on('connection', function connection(ws) {
  console.log('WebSocket client connected');
  
  // Add the client to the set
  clients.add(ws);

  // Handle WebSocket client disconnection
  ws.on('close', function close() {
    console.log('WebSocket client disconnected');
    
    // Remove the client from the set
    clients.delete(ws);
  });

  // Handle WebSocket errors
  ws.on('error', function error(error) {
    console.error('WebSocket error:', error);
  });
});

// Handle incoming Kafka messages
consumer.on('message', function (message) {
  console.log('Received message:', message.value);
  
  // Broadcast the Kafka message to all connected WebSocket clients
  clients.forEach(function (client) {
    client.send(message.value);
  });
});

// Handle Kafka consumer errors
consumer.on('error', function (error) {
  console.error('Consumer error:', error);
});

console.log('WebSocket server started on port 9999');
