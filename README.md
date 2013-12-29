AndroMote
=========
The aim of this project is to turn Android devices into a IoT (Internet of Things) mote via a standard API RESTFul interface. Its functionallity is divided in three main parts.

Parts
-----

###Mote
It publishes the Android device sensors and actuators. With this Api calls a user can have access to phone's temperature sensor, accelerometers, vibration, etc.

###Remote compute
The main idea of this part is to provide an interface to perform mathematical operations remotedly on the device. The project comes with a demo api call which sums to numbers and returns the result via REST.

###Notifications agent
This part turns the device into a push notifications receiver.


Components
----------
###Server
It is made with python language using Flask microframework:

```
http://flask.pocoo.org/
```

To use it, you should install flask with this command:

```
pip install Flask
```

And run it:
```
python main.py
```

It will open a server on http://localhost:5000.


###Android Agent
Just import the project into Android Studio suite and compile it.



Future Works
------------
- Make a wiki to help to use the whole system
- Add functionallity to the three main parts
