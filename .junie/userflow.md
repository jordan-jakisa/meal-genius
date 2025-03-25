graph TD;
  A[Welcome Screen] -->|Click Get Started| B[Dishes Selection]
  B -->|Select Dishes & Next| C[Goals Selection]
  C -->|Select Goals & Next| D[Activity Level Selection]
  D -->|Select Activity & Next| E[Completion Screen]
  E -->|Click Get Started| F[Home Screen]

%% Main App Flow with Bottom Navigation
  F -->|View Meal Plan| G[Meal Plan Details]
F -->|Shuffle Meals| H[Shuffle Meals]
F -->|New Plan| I[Generate New Meal Plan]
F -->|Bottom Nav: Chat| J[Chatbot Screen]
F -->|Bottom Nav: History| K[Meal Plan History]
F -->|Settings Icon| L[Settings Screen]
F -->|Bottom Nav: Shopping| M[Shopping List]

  %% Meal Plan Interaction
  G -->|View Ingredients| G1[Ingredient List]
  G -->|Add to Shopping List| M
  G -->|Back| F

%% Chatbot Flow
J -->|Type Message| J
J -->|Quick Action: Customize Plan| J1[Customize Meal Plan]
J -->|Quick Action: Add New Dish| J2[Add New Dish]
J -->|Quick Action: Get Advice| J3[Receive Tips]
J -->|Back| F

%% History Flow
K -->|Reuse Plan| F
K -->|Back| F

%% Shopping List Flow
  M -->|Check Off Items| M
M -->|Export List| M1[Share/Download List]
M -->|Back| F

%% Settings Flow
L -->|Notification Settings| P[Notification Settings]
L -->|Back| F

%% Notification Settings
P -->|Toggle Settings| P
P -->|Back| L
