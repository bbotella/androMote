#!/usr/bin/python
from modules.Sender import Sender
from flask import Flask, request, json
import modules.constants as constants
import time


app = Flask(__name__)

sender = Sender.Sender()
sender.startSender()

################################################################################################################
################################################################################################################
################################################################################################################
#########
#########       Mote methods
#########
################################################################################################################
################################################################################################################
################################################################################################################
@app.route('/mote/getBattery', methods=['POST'])
def getBattery():
    destination = request.form['moteId']
    sender.sendMessage(destination, '{"operation_type": "mote", "operation": "getBattery", "parameters":[]}')
    i=0
    while not constants.dictThreads.has_key(destination):
        time.sleep(constants.TIMEOUT_TIME_REPETITION)
        i=i+1
        if i==constants.MAX_REPETITIONS_TIMEOUT:
            return '{"exitCode":-2, "msg":"Sending thread not working"}'
    i=0
    if constants.dictThreads.has_key(destination):
        while constants.dictThreads[destination]['responseReceived']==False:
            time.sleep(constants.TIMEOUT_TIME_REPETITION)
            i=i+1
            if i==constants.MAX_REPETITIONS_TIMEOUT:
                constants.dictThreads.pop(destination, None)
                return '{"exitCode":-3, "msg":"Timeout waiting answer"}'
        resp = constants.dictThreads[destination]['response']
        constants.dictThreads.pop(destination, None)
        return resp
    else:
        return '{"exitCode":-1, "msg":"Device not registered"}'
    
    
@app.route('/mote/getTemperature', methods=['POST'])
def getTemperature():
    destination = request.form['moteId']
    sender.sendMessage(destination, '{"operation_type": "mote", "operation": "getTemperature", "parameters":[]}')
    i=0
    while not constants.dictThreads.has_key(destination):
        time.sleep(constants.TIMEOUT_TIME_REPETITION)
        i=i+1
        if i==constants.MAX_REPETITIONS_TIMEOUT:
            return '{"exitCode":-2, "msg":"Sending thread not working"}'
    i=0
    if constants.dictThreads.has_key(destination):
        while constants.dictThreads[destination]['responseReceived']==False:
            time.sleep(constants.TIMEOUT_TIME_REPETITION)
            i=i+1
            if i==constants.MAX_REPETITIONS_TIMEOUT:
                constants.dictThreads.pop(destination, None)
                return '{"exitCode":-3, "msg":"Timeout waiting answer"}'
        resp = constants.dictThreads[destination]['response']
        constants.dictThreads.pop(destination, None)
        return resp
    else:
        return '{"exitCode":-1, "msg":"Device not registered"}'
    
    
@app.route('/mote/getPreassure', methods=['POST'])
def getPreassure():
    destination = request.form['moteId']
    sender.sendMessage(destination, '{"operation_type": "mote", "operation": "getPreassure", "parameters":[]}')
    i=0
    while not constants.dictThreads.has_key(destination):
        time.sleep(constants.TIMEOUT_TIME_REPETITION)
        i=i+1
        if i==constants.MAX_REPETITIONS_TIMEOUT:
            return '{"exitCode":-2, "msg":"Sending thread not working"}'
    i=0
    if constants.dictThreads.has_key(destination):
        while constants.dictThreads[destination]['responseReceived']==False:
            time.sleep(constants.TIMEOUT_TIME_REPETITION)
            i=i+1
            if i==constants.MAX_REPETITIONS_TIMEOUT:
                constants.dictThreads.pop(destination, None)
                return '{"exitCode":-3, "msg":"Timeout waiting answer"}'
        resp = constants.dictThreads[destination]['response']
        constants.dictThreads.pop(destination, None)
        return resp
    else:
        return '{"exitCode":-1, "msg":"Device not registered"}'


################################################################################################################
################################################################################################################
################################################################################################################
#########
#########       Calculator methods
#########
################################################################################################################
################################################################################################################
################################################################################################################
@app.route('/computate/sumFloat', methods=['POST'])
def sumFloat():
    destination = request.form['moteId']
    num1 = request.form['num1']
    num2 = request.form['num2']
    sender.sendMessage(destination, '{"operation_type": "computate", "operation": "sumFloat", "parameters":[{"name": "num1", "value": '+str(num1)+'}, {"name": "num2", "value": '+str(num2)+'}]}')
    i=0
    while not constants.dictThreads.has_key(destination):
        time.sleep(constants.TIMEOUT_TIME_REPETITION)
        i=i+1
        if i==constants.MAX_REPETITIONS_TIMEOUT:
            return '{"exitCode":-2, "msg":"Sending thread not working"}'
    i=0
    if constants.dictThreads.has_key(destination):
        while constants.dictThreads[destination]['responseReceived']==False:
            time.sleep(constants.TIMEOUT_TIME_REPETITION)
            i=i+1
            if i==constants.MAX_REPETITIONS_TIMEOUT:
                constants.dictThreads.pop(destination, None)
                return '{"exitCode":-3, "msg":"Timeout waiting answer"}'
        resp = constants.dictThreads[destination]['response']
        constants.dictThreads.pop(destination, None)
        return resp
    else:
        return '{"exitCode":-1, "msg":"Device not registered"}'



################################################################################################################
################################################################################################################
################################################################################################################
#########
#########       Notirifications methods
#########
################################################################################################################
################################################################################################################
################################################################################################################



if __name__ == '__main__':
    app.run()



