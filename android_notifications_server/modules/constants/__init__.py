SERVER = 'gcm.googleapis.com'
PORT = 5235
USERNAME = 'GOOGLE_API_USERNAME'
PASSWORD = 'GOOGLE_API_PASS'

MAX_REPETITIONS_TIMEOUT = 40
TIMEOUT_TIME_REPETITION=0.3

#dictThreads is a dictionary to keep track of all the outgoing connections sent by the server
dictThreads={}

unacked_messages_quota = 1000
send_queue = []