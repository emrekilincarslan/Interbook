# InterBook 

[![N|Solid](https://koenig-media.raywenderlich.com/uploads/2019/06/layers-architecture.png)](https://nodesource.com/products/nsolid)

  
  ### JetPackComphonents
   I used as an architecture 
      Databinding
      lifecycles
      livedata 
      navigation
      paging
      room
      viewmodel
   
   for the UI 
      Fragment
      layout
      material design
      
   Behaviour 
      permissions
   and andoid ktx 
   
  
  ### Clean architecture pattern 
    Single Responsibility: Each software component should have only one reason to change – one responsibility.
    Open-closed: You should be able to extend the behavior of a component, without breaking its usage, or modifying its extensions.
    Liskov Substitution: If you have a class of one type, and any subclasses of that class, you should be able to represent the base class usage with the subclass, without breaking the app.
    Interface Segregation: It’s better to have many smaller interfaces than a large one, to prevent the class from implementing the methods that it doesn’t need.
    Dependency Inversion: Components should depend on abstractions rather than concrete implementations. Also higher level modules shouldn’t depend on lower level modules.
       -
  Presentation: A layer that interacts with the UI.
  Use cases: Sometimes called interactors. Defines actions the user can trigger.
  Domain: Contains the business logic of the app.
  Data: Abstract definition of all the data sources.
  Framework: Implements interaction with the Android SDK and provides concrete implementations for the data layer.
  
        
   ### Google Oauth 
   auth2
