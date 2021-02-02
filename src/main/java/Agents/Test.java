package Agents;

import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SimpleBehaviour;

public class Test extends Agent {
    protected void setup() {

        FSMBehaviour compFSM = new FSMBehaviour(this) {
            public int onEnd() {
                System.out.println("FSM Behavior completed successfully!");
                return 0;
            }
        };

        // The first state registration - Behavior X
        compFSM.registerFirstState(new SimpleBehaviour(this) {
            int c = 0;

            public void action() {
                System.out.println("complement all the X Behavior");
                c++;
            }

            @Override
            public boolean done() {
                return true;
            }

            public int onEnd() {
                return (c > 4 ? 1 : 0);
            }
        }, "X");

        // The second state registration - Behavior Z
        compFSM.registerState(new OneShotBehaviour(this) {

            public void action() {
                System.out.println("complement all of Z Behavior");
            }

            public int onEnd() {
                return 2;
            }
        }, "Z");

        // The third and final state registration - Behavior Z
        compFSM.registerLastState(new OneShotBehaviour(this) {

            public void action() {
                System.out.println("Running my last behavior.");
            }
        }, "Y");

        // Definition of transactions
        // Transaction behavior X to Z, if onEnd () X behavior returns 0
        compFSM.registerTransition("X", "Z", 0);

        // Transaction behavior X to Y if onEnd () X behavior returns 1
        compFSM.registerTransition("X", "Y", 1);


        // Default transition setting (no matter what type of return)
        // As the state machine is finite, need to reset the states of X and Z Sring [] { "X", "Z"}
        compFSM.registerDefaultTransition("Z", "X", new String[]{"X", "Z"});

        // Trigger the behavior as state machine
        addBehaviour(compFSM);
    }
}
