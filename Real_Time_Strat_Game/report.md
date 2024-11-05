# Report

 Jan Berk Kara (s5558727) & Oleg Baronov (s5833930)

## Introduction

The program will present a Real Time Simulation (RTS), which was developed as a component of the OOP graphical view. 
Our program allows users to create maps with locations and routes, add armies, and simulate wars. 
We used Java Swing to create a GUI that followed the MVC approach. Users can interact with nodes, edges, 
and armies to add, delete, and alter them. The simulation features movement, and random events. 
The saving capabilities that allowed you to export the simulation state to a JSON file is also included.  
Additionally, there is a playlist that player can select a music to listen.

## Program design

The design of the program attempts to follow the Model View Controller (MVC) design in combination with the
Observer pattern. Nevertheless, some part of the program have been designed by our team differently in order to 
accommodate all the necessary functionality that the program was required as well as to meet the deadline of the 
given assignment. Hence, we approached this project by prioritizing functionality, readability and appropriate level
of flexibility. 

In our MVC approach, the role of the controller is assumed by the NodeMouseListener Class, which corresponds to 
the actions of the mouse being clicked (short press), pressed (long press), released and dragged. By manipulating these 
actions, the user is able to interact with the program i.e. GraphPanel Class. GraphPanel class contains a number of
functions and fields related to visual presentation of the program that the user sees. Furthermore, this class 
instantiates appropriate objects of custom classes, like ButtonInitializer, that contain additional functionality and 
fields that the user observers. When the user interacts with the panel, the changes are passed on from the GraphPanel
to the appropriate Models (i.e. classes like Army, Graph, Node and so on) using an Observer pattern approach. 
This means that the actions performed by the user lead to changes within appropriate Models and, therefore, result
in changes in View (i.e. GraphPanel). 

As can be noted, the observer pattern utilised in our program takes a different approach as it makes the Model as the 
observer of the View than other way round. This is done due to the fact that changes captured by the MouseListener
are presented within the GraphPanel class. Hence, this made it hard to implement Model as observers as MouseListener
should have been implemented within the Model from the start (e.g. implementing MouseListener inside GraphDrawer class).
Nevertheless, we ensured that our Model is appropriately separated from the View by establishing the View as an observer.
As a result, the View is sufficiently separated from the Model, thus achieving similar level of independence. 

Another design choice that we implemented is the separation of code into multiple smaller classes, like position and 
random event, in order to facilitate the readability of the code. It also assisted us in saving more space and reducing 
complexity by dividing the code into multiple specialised parts. Nevertheless, some classes can still be broken down 
into smaller classes (like GraphPanel) to further reduce the complexity and improve readability. 

Hence, the design of our program allows for the efficient execution of the RTS program. No major bugs or significant 
problems have been identified.

## Evaluation of the program

Overall, our program works well and is able to execute the desired function of the code (i.e. to simulate the battle
of armies). Implemented buttons, selection of nodes/edges and combat process are executed well and present no major 
bugs preventing it from full functionality. In addition, audio effects and in-game music have been implemented to 
support the simulation immersion. The code that executes all implemented functionality attempts to follow MVC and 
observer design patters, hence, facilitating readability of code and implements necessary updates when the program
is running. 

However, due to time constraints, not all of initially planned features have been implemented. For example,
placing a stronger emphasise on concurring the nodes by each army has been the initial idea of our team. Likewise, it 
was initially planned to present the user with a selection of events present on the edge/node that can be deleted. 
However, due to time constraints, the task was simplified to a single button that deletes all events from the selected
node/edge. 

As our team progressed through the assignment steps, the complexity of the code became a major challenge.
Maintaining an 'easy-to-read' code became an apparent issue, which we attempted to resolve by breaking down the code
into multiple classes. Furthermore, our code became high in coupling and low in cohesion as classes began to intertwine.
This forced us to change our initial plan to complete a full functioning code before improving it by utilising 
observer and MVC design patterns immediately. As a result, our code experienced major changes, which could have been 
avoided and reduce our time spend on debugging. This, in turn, lead us to conclude that our initial approach did not 
account for the complexity of the task, thus, projects of similar complexity should be approached with the design plan 
in mind from the start.

## Questions

Please answer the following questions:

1. In this assignment, the program should follow the Model View Controller (MVC) pattern. Please explain the design of 
the program in terms of the MVC pattern. Specifically try to answer the following questions:
   - MVC consists of three components: Model, view and controller. Can you please explain the role of each component? 
   Please provide examples of these roles from the assignment. How are these three roles (i.e. Model, view and 
   controller) are implemented in the assignment?
   - MVC enforces special constraints on the dependencies between its three components: Model, view and controller. 
   Please explain these constraints, and why are they important?

___

Answer:

In terms of the Model View Controller pattern, the idea according to program can be divided into three independent 
parts:

The Model contains the application's data and operational logic. It contains the data, controls the state, and executes 
the logic that impacts it. In the program, the model might be expressed through the data structures and classes that 
store and handle the program's information. As an instance, if the programme deals with maintaining and organising 
teacher data, the Teacher class could act as the Model. As an example provided within our project, classes such as Army,
Unit, Edge and Node are considered to be Models since they maintain and organise the data related to 
their specified object. This, in turn, allows other parts of the program to update and store information about each
Army, Node or edge during program execution.

The part known as View is in charge of displaying data to the user and receiving user input. It refers to the graphical 
user interface (GUI) or another approach by which the user communicates with the programme. In the assignment, the View 
represents the graphical interface elements built with Swing, like JPanels and JButtons. These components display data 
to the user and accept user input. For example, Buttoninitilizer class used in our assignment creates and joins 
appropriate functions to buttons, that the user then can interact with within GraphPanel class (during execution of 
the program). Thus, Buttoninitilizer class gives a visual representation of each function that the user can easily 
understand and interact with. 

The Controller serves as a changing input for Model and therefore it changes View. It accepts user input, modifies the 
Model, and changes the View appropriately. In the program, the Controller might be implemented as event processors or 
listener classes that react to user interactions with GUI components. For example, if a user mouse clicks to submit 
an application, the Controller is going to handle the operation, check the data and edit the Model appropriately. In 
our project, controller role is taken by the NodeMouseListener class that initiates appropriate actions, such as node
dragging or creation of edges, through mouse clicking, pressing and dragging.

The connections between these components in MVC correspond to specified limitations:

The View observes modifications to the Model but does not actively control it. Instead, it gets modifications from the 
Model and modifies its presentation accordingly. This guarantees that the View is independent of the underlying data 
logic. Hence, the flow of the program has a clear distinction of each step. This improves the readability and allows 
for easier implementation of new methods without major changes to other parts of the code.

The Controller acts as a bridge between the View and the Model. It collects user input from the View perspective, 
evaluates it, and changes the Model as needed. However, the Controller does not have direct contact with the View's 
components. This separation enables maintaining and evaluation of each component in independence, which is particularly 
useful when additional implementations of controllers are required at later stages of the development.

The Model functions independently of the View and Controller. It separates the program's data and logic, which makes 
it more flexible, reusable and easy to operate. This is particularly useful when the View or Controller are not 
completely developed for the program to run. It allows the user to work on the model and apply newly developed
changes whenever the logic component of the program is ready to be utilised. This improves productivity as all parts
can be worked on separately and combined at the later stages. 

These limitations are critical to determining flexibility, reliability, and evaluation in the program. MVC encourages 
a well-organized and structured construction by separating issues and creating explicit relationships between 
components, making complicated programmes easier to construct and manage.

___

2. The Swing library provides the ability to create nested user interface components. In this assignment, you created 
multiple JPanel components on the user interface. These contain other user interface components to build-up a tree of 
user interface components. Which design pattern does Swing implement to create a hierarchy of user interface 
components? Please explain this pattern and how it is implemented in Swing.

___

Answer:

Swing uses the Composite design pattern to build a hierarchy of user interface components. The Composite pattern is a 
pattern for structural design that enables objects to be joined into tree structures that demonstrate part to whole 
hierarchy. It handles individual components and object compositions identically, allowing users to communicate with 
them in a consistent manner.

In Swing, aspects such as JPanel may include additional parts that create a tree structure. As an instance, Panel 
can include buttons, labels, text fields, and other JPanels. This hierarchy enables the building of sophisticated user 
interfaces through the integration of simple components. A good example of it is a GraphPanel class within our project
that integrates JLabel and JSplitPane components in a single panel. This creates a sophisticated user interface that
is made using 'easy-to-understand' blueprint for other programmers.

In addition, Swing utilises the Composite design via the inheritance structure of its component classes. Each Swing 
aspect inherits from the same base class, which specifies the typical behaviour of all aspects. This enables Swing 
components to be handled identically, whether they are leaf parts (JButton or JLabel) or composite parts (JPanel).

Swing also includes methods for adding and removing components from containers, which allows for the dynamic building 
of challenging hierarchies at runtime. The Composite design stands out by its adaptability and uniformity when dealing 
with distinct parts and structures made of composites.

___

3. The Observer pattern is useful to implement the MVC pattern. Can you please explain the relationship between the 
Observer pattern and the MVC pattern? Please provide an example from the assignment on how the Observer pattern 
supports implementing the MVC pattern.

___

Answer: 

MVC design and the Observer pattern are closely connected, especially when it involves helping the MVC parts 
communicate with one another. The View contains the display layer, the Model provides the data and operations logic, 
and the Controller serves as the changing operation for Model, processing user input and updating the Model (and 
therefore the View) as necessary in the MVC pattern.

The observer design enables objects to receive notifications to changes in another object's state, resulting in a 
flexibly linked interaction method. In the overall scheme of MVC, the Model is whatever is being observed, 
while the Views operate as the observers. Whenever the Model's status shifts, it tells every associated Views, who 
modify their displays according to the new state.

As an instance, in our GraphPanel implementation, the Model could symbolise the data structure using nodes 
and edges. Anytime a user adds or removes a node, the model's state changes. The Views, which include the visual 
illustration of the graph shown to the user, must be modified appropriately. Using the Observer design, the View 
registers the changes made to the Model. If the Model changes, the View then updates its visuals to show the user's 
changes which made by controller.

As a result, Observer pattern splits the Model from the Views, making the programme simpler to update and develop. 
It additionally encourages a division of problems, as every part focuses on its own role in the MVC design. 

___

## Process evaluation

The graph editor was developed via repeated design steps and continuous integration of multiple elements.
At first, conceptualising the fundamental structure of nodes and edges was simple,
but combining them with graphical features became challenging. Designing the GraphPanel and implementing node-edge 
interactions necessitated thoughtful consideration of mouse events and mathematical calculations.

One interesting error was an improper calculation in the edge boundary logic,
which resulted in a wrong representation of armies, with one army portrayed on top of the second.
This emphasised the significance of extensive testing and debugging, particularly for graphical programmes.
Another problem was maintaining the drag process for nodes, as they must be flexible when moving.
Finally, when an army was added to a node from a random location of a node, the node moved somewhere else in the graph.
Mistakes are typical in such large scale projects, it was nice to recognise and learn from them this mistakes.

This work taught us many valuable lessons in programming through events, graphical design for user interfaces, 
and mathematical procedures. It emphasised the need of flexible design and explicit distinction between issues, 
which make the program simpler to manage and reusable. The work also demonstrated the importance of patience,
tenacity and a good team work in fixing complicated interactive programs.

## Conclusions

This assignment required creating an interactive graph editor that could handle nodes and edges, as well as 
showing ability in programming with geometrical computations, and visual design. Although earlier difficulties 
in merging visual elements and adjusting interactions with users, the MVC design approach resulted in an effective 
and understandable programme. This learning and programming task highlighted the significance of flexible structure, 
careful testing, and making checks, debugs while developing complicated systematic works. The project gave us valuable
knowledge about how to develop graphical programming and highlighted important programming ideas, which significantly 
enhanced our knowledge and abilities set for developing program.
