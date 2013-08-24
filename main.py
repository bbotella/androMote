#!/usr/bin/python
from modules.Sender import Sender

sender = Sender.Sender()
sender.startSender()
sender.sendMessage('{"operation_type": "mote", "operation": "getBattery", "parameters":[]}')



