Record and play was implemented using a similar architecture to the rest of the program. Recordings are represented by a class that implements **Persistable** which has an associated factory class which allows the class to be written as JSON and back.

The class contains an initial state where the recording stated (as a **GameState**), and a List of **ChapsAction**. The actions are the only thing the controller sends to the model, so having a starting point and a list of actions is sufficient to reconstruct the entire session. 

The events are captured by the **RecnplayProxy** class which acts as a **Proxy** for **ChapsModel**. This allows it to capture all the through-communication between the two packages.

The Controller manages this by using the start and stop methods.