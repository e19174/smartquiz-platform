// src/services/socket.js
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";

let stompClient = null;
let isConnected = false;

export const connectSocket = (onConnected) => {

  //  If already connected, reuse connection
  if (stompClient && isConnected) {
    console.log(" Already connected");
    onConnected();
    return;
  }

  const socket = new SockJS("http://localhost:8080/ws");

  stompClient = new Client({
    webSocketFactory: () => socket,
    debug: (str) => console.log(str),

    reconnectDelay: 5000, //  auto reconnect

    onConnect: () => {
      console.log(" Connected to WebSocket");
      isConnected = true;
      onConnected();
    },

    onDisconnect: () => {
      console.log(" WebSocket Disconnected");
      isConnected = false;
    },

    onStompError: (frame) => {
      console.error(" Broker error:", frame);
    }
  });

  stompClient.activate();
};

export const subscribe = (topic, callback) => {
  if (!stompClient || !isConnected) {
    console.error(" Cannot subscribe, not connected");
    return;
  }

  console.log("📡 Subscribing:", topic);

  stompClient.subscribe(topic, (msg) => {

  try {

    callback(JSON.parse(msg.body));

  } catch {

    callback(msg.body);

  }

});
};

export const sendMessage = (destination, body) => {
  if (!stompClient || !isConnected) {
    console.error(" WebSocket not connected");
    return;
  }

  console.log(" Sending:", destination, body);

  stompClient.publish({
    destination,
    body: JSON.stringify(body),
  });
};


export const disconnectSocket = () => {
  if (stompClient) {
    stompClient.deactivate();
    stompClient = null;
    isConnected = false;
    console.log(" WebSocket manually disconnected");
  }
};