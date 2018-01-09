# Multi Agent System

## Task
The main task was to implement the multi-agent system which includes heaters: agent A and agent B and controller: agent C. Agent A and agent B should maintain the temperature in the room, using the energy from agent C, who gets the energy from sunlight and power-line. In the case of being in range agents A and B get reward, contra versa - penalty from agent C.

The ceiling of agent C, which changes every 6 hours is the total amount of energy, which should be divided between two agents. As instrument for this division we used English auction and Dutch auction. The demands of heaters is also changes every 6 hours and depend of current weather outside. At the beginning, agent A has 500 credits, agent B has the same amount of credits, agent C has 10000 credits. In order to provide competition between agent A and agent B we created English auction and Dutch auction. Heaters do not cooperate during their work, but trying to be in range, using Bellman equation.

## Structure of the model
Designed model consists of several classes:

* Main class
* Weather class
* Auction class
* Solar Panel class
* Heater class
* Controller class

Main class is used to run the simulation. In this class we  ll in the weather set for the particular amount of time steps using objects of Weather class, create agents and run simulation in accordance to amount of size of weather set.

Weather class represents the outdoor weather for 6 hours. It includes the information about sun level and outdoor temperature. We use this class to implement di erent states of weather and calculate parameters of agents such as temperature and ceiling according these states.

Auction class is used to provide English and Dutch auctions. Using these auctions we organize competition between two heater agents in order to buy the amount of energy needed to provide necessary service.

Solar Panel class represents solar panel element of the model. It generates the energy in the case of shining the sun.

Heater class is used to implement two heater agents: A and B. It has the information about current temperature and starting position of agent, limits, amount of credits, consumption coeficient, Q-matrix, Reward matrix and Probability matrix. We use Bellman equation to establish movement of agents and update their Q-matrices. Agent traverse through the Q-matrix which we update every step using Bellman equation. Finding the state to that agent wants to come, according its Q-matrix, we compute amount of energy demand to come to the state.

Controller class is used to implement agent C, which updates the energy ceiling level (Total amount of energy), that depends on amount of energy from power-line (Additional energy) and sun level (Solar energy) and sells this energy to agents A and B conducting auctions and rewarding/penalize agents A and agent B and in accordance to Reward matrix of each of them.

All the information concerning each step of work of agents we output to the console to let follow what happening on each step.

UML diagram of classes presented on the following screenshot.


![image](https://user-images.githubusercontent.com/12521579/34738835-9e282394-f57a-11e7-9201-1c82a087bfcc.jpg)

## Simulation
At the start of program we create agents and environment. The amount of iterations depends on the size of array of weather dataset, where each weather item is the weather for 6 hours step.

In the beginning of simulation we are creating agents and set their’s initial parameters. We also create initial Q-matrix, R-matrix and P-matrix for each of agents. The screenshot below demonstrates the log of program that shows initialization of agents.

Example of data representation during simulation run can be seen on the following screenshots.
![image](https://user-images.githubusercontent.com/12521579/34739251-fe1d0264-f57b-11e7-9705-5728654120bc.png)

As could be seen from the example above, initial Q-matrices of agents populated by zero values.
Example below demonstrates data representation after  rst step of simulation(0 step).

![image](https://user-images.githubusercontent.com/12521579/34739011-38ecad82-f57b-11e7-8b64-1e929c832ab4.png)

As could be seen from this example, both agents bought the energy without auction, because total demand of agent A and agent B is less than current ceiling. Both agents changed initial state and updated their Q-matrices in accordance to their demands.

In case of providing English or Dutch auction both agents make bids and  nally, one of them wins auction. Agent who starts bids at auction is selected randomly. To de ne who’s won the auction we use the knowledge of who’s started and number of last bid. Example of the running auction can be seen on the following screenshot.

![image](https://user-images.githubusercontent.com/12521579/34739847-e0540b7c-f57d-11e7-9a57-6e6e2443b17a.png)

In the case of providing the auction one of agents wins, updates its Q-matrix and changes its state, another agent just updates its Q-matrix without changing the state.

In the end, both Q-matrices of agents A and B were updated according to their reward/penalty. The following screenshot shows the  nal state of Q-matrices of agents A and B.

![image](https://user-images.githubusercontent.com/12521579/34739655-3d8b1ac0-f57d-11e7-9f8a-bba8f702bcce.png)

Researching the results of di erent simulations we have not manage to clearly de ne whether or not agent A or agent B outperforms the other in the end. Some results show that agent A goes bankrupt very fast and lose all credits on  fth or sixth iteration, other results show that agent B going to be bankrupt. Being or not being bankrupt depends on amount of auctions won particular agent and amount of rewards and penalties it gets throughout the simulation.


