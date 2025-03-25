graph TD;
  A[Welcome Screen] -->|Click Get Started| B[Dishes Selection]
  B -->|Select Dishes & Next| C[Goals Selection]
  C -->|Select Goals & Next| D[Activity Level Selection]
  D -->|Select Activity & Next| E[Completion Screen]
  E -->|Click Get Started| F[Home Screen]

  %% Main App Flow
  F -->|View Meal Plan| G[Meal Plan Details]
  F -->|Shuffle Meals| H[Shuffle Screen]
  F -->|Refresh Plan| I[Generate New Meal Plan]
  F -->|Chat with Assistant| J[Chatbot Screen]
  F -->|View History| K[Meal Plan History]
  F -->|Open Settings| L[Settings Screen]
  F -->|View Shopping List| M[Shopping List]

  %% Meal Plan Interaction
  G -->|View Ingredients| G1[Ingredient List]
  G -->|Add to Shopping List| M
  G -->|Edit Meal| J

  %% Shuffle Flow
  H -->|Shuffle All Meals| F
  H -->|Shuffle Specific Days| F
  H -->|Shuffle Meal Types| F
  
  %% Chatbot Flow
  J -->|Customize Meal Plan| F
  J -->|Add New Dishes| N[Add Dish to Profile]
  J -->|Ask Dietary Advice| O[Receive Tips]
  
  %% History Flow
  K -->|Reuse Old Plan| F

  %% Settings Flow
  L -->|Edit Preferences| C
  L -->|Manage Notifications| P[Notification Settings]
  L -->|Privacy & Data| Q[Privacy Policy]
  
  %% Shopping List Flow
  M -->|Check Off Items| M
  M -->|Export List| R[Share/Download List]

  %% Notifications
  P -->|Weekly Reminder| I  