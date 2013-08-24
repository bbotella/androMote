#!/usr/bin/python
from modules.Sender import Sender
from flask import Flask, request, json
import modules.constants as constants
import time


app = Flask(__name__)

sender = Sender.Sender()
sender.startSender()


@app.route('/getBattery', methods=['POST'])
def getBattery():
    destination = request.form['moteId']
    sender.sendMessage(destination, '{"operation_type": "mote", "operation": "getBattery", "parameters":[]}')
    time.sleep(0.5)
    if constants.dictThreads.has_key(destination):
        while constants.dictThreads[destination]['responseReceived']==False:
            time.sleep(0.3)
        resp = constants.dictThreads[destination]['response']
        constants.dictThreads.pop(destination, None)
        return resp
    else:
        return '{"exitCode":-1, "msg":"Device not registered"}'



if __name__ == '__main__':
    app.run()



