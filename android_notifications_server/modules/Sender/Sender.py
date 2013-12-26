import xmpp
import sys, json, random, string
import threading
import modules.constants as constants

class SendThread(threading.Thread):
    def __init__(self, event, tMessage, sender):
        threading.Thread.__init__(self)
        self.event = event
        self.tMessage = tMessage
        self.sender = sender

    def run(self):
        self.sender.send(self.tMessage)
        self.event.wait()
        #Once an event has been received after sending a message, we check if we were waiting for a response
        if constants.dictThreads.has_key(self.remoteFrom):
            #If so, we update the dict
            constants.dictThreads[self.remoteFrom]['responseReceived']=True
            constants.dictThreads[self.remoteFrom]['response']=self.remoteResponse
        self.event.clear()
    
    def setResponse(self, remoteResponse):
        self.remoteResponse = remoteResponse
    
    def setFrom(self, remoteFrom):
        self.remoteFrom = remoteFrom
    


class Sender:
    def __init__(self):
        #We create a xmpp client/server to communicate with the Android device
        self.client = xmpp.Client(constants.SERVER, debug=[])
        self.client.connect(server=(constants.SERVER, constants.PORT), secure=1, use_srv=False)
        self.auth = self.client.auth(constants.USERNAME, constants.PASSWORD)
        self.senderThread = threading.Thread(target = self.iteration)
    
        if not self.auth:
            print 'Authentication failed!'
            sys.exit(1)

        self.client.RegisterHandler('message', self.message_callback)
        
        
    def message_callback(self, session, message):
        global unacked_messages_quota
        gcm = message.getTags('gcm')
        if gcm:
            gcm_json = gcm[0].getData()
            msg = json.loads(gcm_json)
            if not msg.has_key('message_type'):
                # Acknowledge the incoming message immediately.
                self.send({'to': msg['from'],
                      'message_type': 'ack',
                      'message_id': msg['message_id']})
                # Queue a response back to the server.
                if msg.has_key('from'):
                    # Send a dummy echo response back to the app that sent the upstream message.
                    constants.send_queue.append({'to': msg['from'],
                                       'message_id': self.random_id(),
                                       'data': {'server_message': '{"operation_type": "mote", "operation": "ack", "parameters":[]}'}})
                    if constants.dictThreads.has_key(msg['from']):
                        thread = constants.dictThreads[msg['from']]['thread']
                        thread.setResponse(msg['data']['message'])
                        thread.setFrom(msg['from'])
                        event = constants.dictThreads[msg['from']]['event']
                        event.set()
            elif msg['message_type'] == 'ack' or msg['message_type'] == 'nack':
                constants.unacked_messages_quota += 1
    
    
    # Return a random alphanumerical id
    def random_id(self):
        rid = ''
        for x in range(8): rid += random.choice(string.ascii_letters + string.digits)
        return rid
    
    def send(self, json_dict):
        template = ("<message><gcm xmlns='google:mobile:data'>{1}</gcm></message>")
        self.client.send(xmpp.protocol.Message(
            node=template.format(self.client.Bind.bound[0], json.dumps(json_dict))))
        
    
    def sendMessage(self, destination, mess):
        constants.send_queue.append({'to': destination,
                   'message_id': 'reg_id',
                   'data': {'server_message': mess, 'message_destination': 'RegId',
                            'message_id': self.random_id()}})
    
    
    def flush_queued_messages(self):
        while len(constants.send_queue) and constants.unacked_messages_quota > 0:
            tMessage = constants.send_queue.pop(0)
            event=threading.Event()
            sendThread = SendThread(event, tMessage, self)
            to=tMessage['to']
            dictAux={}
            dictAux['event']=event
            dictAux['thread']=sendThread
            dictAux['responseReceived']=False
            dictAux['response']=None
            constants.dictThreads[to]=dictAux
            constants.unacked_messages_quota -= 1
            sendThread.start()
    
    
    def startSender(self):
        self.senderThread.start()
        
    def stopSender(self):
        self.senderThread.stop()
    
    def iteration(self):
        while True:
            self.client.Process(1)
            self.flush_queued_messages()
