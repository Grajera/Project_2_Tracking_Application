# Tracking Application --- Server
In this assignment, you will be extending your Shipment Tracker.

## Objectives
* Learn to apply design patterns to existing systems

## Requirements

This project should meet the following requirements:
### UML
* You should do your conceptual model using UML first before you write any code.
* Your UML diagram should include all classes and should correctly model their relationships, attributes, and methods. Be sure to indicate which methods are public or private (all attributes should be private).
* Your UML should correctly model the design patterns you plan on implementing.
### Implementation
* Change your system to be a tracking server rather than a tracking simulator.
* You should build a simple client application (can be a jetpack compose application or simple web application) that makes requests to your tracking server (this can be simple, mine is a text box where paste one of those strings from the simulation file and make a request to the server with that text)
* You must maintain the existing functionality of the tracker application (a user of the tracking server should still be able to type in a shipment ID and observe it for updates)
* You should fix any issues with your existing system and improve upon them by using new design patterns that we have talked about
* Extend the system to 4 types of shipments: StandardShipment, ExpressShipment, OvernightShipment, and BulkShipment
  An express shipment cannot have an expected delivery date of more than 3 days after the shipment was created
  An overnight shipment must have an expected delivery date of the day after it was created.
  A BulkShipment should not have an expected delivery date sooner than 3 days after it was created.
  StandardShipment has no special conditions
  In the event that an update occurs that validates one of these rules then you should still apply the update but change the state of the shipment to indicate that something abnormal has happened. If a user is tracking one of these shipments then the UI should indicate to the user that the shipment had something unexpected happen and indicate what happened to the user (for example you might display some text saying: "An overnight shipment was updated to include a delivery date later than 24 hours after it was created.") They should also behave appropriately with the existing update types. (For example, if an overnight shipment is delayed, then it is expected that the delivery date will be after 24 hours so there is no reason to display a message to the user about that. Simply changing the status to delayed is fine)
* Look for opportunities to refactor your system using the Factory and Singleton patterns.
### Unit Tests
* Ideally, you should not break too many of your existing unit tests (this can't be entirely avoided but if you find your breaking a lot of your tests then you might not be following the open/closed principle very well)
* Add new unit tests for any new behavior added.
