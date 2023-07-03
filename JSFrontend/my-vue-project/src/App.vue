<template>
  <div>
    <ul>
      <li v-for="message in messages" :key="message">{{ message }}</li>
    </ul>
  </div>
</template>

<script>
export default {
  data() {
    return {
      messages: []
    };
  },
  mounted() {
    this.setupWebSocket();
  },
  methods: {
    setupWebSocket() {
      const socket = new WebSocket('ws://localhost:9999');

      socket.onopen = () => {
        console.log('WebSocket connection established');
      };

      socket.onmessage = event => {
        const message = event.data;
        this.messages.push(message);
      };

      socket.onclose = () => {
        console.log('WebSocket connection closed');
      };

      socket.onerror = error => {
        console.error('WebSocket error:', error);
      };
    }
  }
};
</script>

