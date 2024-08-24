import socketserver
import random

import tensorflow as tf
import keras

class MyTCPHandler(socketserver.BaseRequestHandler):
    """
    The request handler class for our server.

    It is instantiated once per connection to the server, and must
    override the handle() method to implement communication to the
    client.
    """

    def handle(self):
        # self.request is the TCP socket connected to the client
        self.data = self.request.recv(1024).strip()
        print("{} wrote:".format(self.client_address[0]))
        print(self.data)
        action = random.randint(1,4)
        actionBytes = action.to_bytes(4, 'big')
        self.request.sendall(actionBytes)

if __name__ == "__main__":
    HOST, PORT = "localhost", 9999
    print("Starting server on: {host}:{port}".format(host = HOST, port = PORT))

    # Create the server, binding to localhost on port 9999
    server = socketserver.TCPServer((HOST, PORT), MyTCPHandler)

    # Activate the server; this will keep running until you
    # interrupt the program with Ctrl-C
    server.serve_forever()