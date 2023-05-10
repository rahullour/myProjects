import consumer from "channels/consumer"

consumer.subscriptions.create("ChatroomChannel", {
  connected() {
    // Called when the subscription is ready for use on the server
  },

  disconnected() {
    // Called when the subscription has been terminated by the server
  },

  received(data) {
    
    // Called when there's incoming data on the websocket for this channel
   document.getElementById('message-container').innerHTML = document.getElementById('message-container').innerHTML + data.mod_message;
   document.getElementById('message_body').value = "" ;

   $('#messages').scrollTop($('#messages')[0].scrollHeight);
   console.log($('#messages')[0].scrollHeight);
   

  }
});
