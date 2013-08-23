#!/usr/bin/python
import xmpp
import sys, json, random, string

SERVER = 'gcm.googleapis.com'
PORT = 5235
USERNAME = '84815785587'
PASSWORD = 'AIzaSyCu4GJ5fW9iJ9sYRg9jQTiqCTJWFiZt914'
REGISTRATION_ID =EGISTRATION_ID = 'APA91bEKm4jeTgOEQc7RFwRfJAuggwiE9WhCbYgK7BxMkDUH7lpVQA0Q4-Asl0TDwJf8K1CDQYauxa7WHWBxTfJwwI5b7ZKrT65vkp4XB5ysIsnmpnhLfdyrKb1xb-33-lY7TT2rXSTO0mULXXkqtzUQTD72Fk9hSg'

unacked_messages_quota = 1000
send_queue = []

# Return a random alphanumerical id
def random_id():
  rid = ''
  for x in range(8): rid += random.choice(string.ascii_letters + string.digits)
  return rid

def message_callback(session, message):
  global unacked_messages_quota
  gcm = message.getTags('gcm')
  if gcm:
    gcm_json = gcm[0].getData()
    msg = json.loads(gcm_json)
    if not msg.has_key('message_type'):
      # Acknowledge the incoming message immediately.
      send({'to': msg['from'],
            'message_type': 'ack',
            'message_id': msg['message_id']})
      # Queue a response back to the server.
      if msg.has_key('from'):
        # Send a dummy echo response back to the app that sent the upstream message.
        print msg['data']['message']
        send_queue.append({'to': msg['from'],
                           'message_id': random_id(),
                           'data': {'server_message': '{"operation_type": "mote", "operation": "ack", "parameters":[]}'}})
    elif msg['message_type'] == 'ack' or msg['message_type'] == 'nack':
      unacked_messages_quota += 1

def send(json_dict):
  template = ("<message><gcm xmlns='google:mobile:data'>{1}</gcm></message>")
  client.send(xmpp.protocol.Message(
      node=template.format(client.Bind.bound[0], json.dumps(json_dict))))

def flush_queued_messages():
  global unacked_messages_quota
  while len(send_queue) and unacked_messages_quota > 0:
    send(send_queue.pop(0))
    unacked_messages_quota -= 1

client = xmpp.Client('gcm.googleapis.com', debug=[])
client.connect(server=(SERVER,PORT), secure=1, use_srv=False)
auth = client.auth(USERNAME, PASSWORD)
if not auth:
  print 'Authentication failed!'
  sys.exit(1)

client.RegisterHandler('message', message_callback)

send_queue.append({'to': REGISTRATION_ID,
                   'message_id': 'reg_id',
                   'data': {'server_message': '{"operation_type": "mote", "operation": "getBattery", "parameters":[]}', 'message_destination': 'RegId',
                            'message_id': random_id()}})

while True:
  client.Process(1)
  flush_queued_messages()