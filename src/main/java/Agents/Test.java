package Agents;

import jade.core.Agent;
import jade.core.behaviours.*;

import static jade.core.behaviours.ParallelBehaviour.WHEN_ALL;

public class Test extends Agent {
    protected void setup() {

        System.out.println("Hi! I am the agent " + getLocalName());
        System.out.println("I'll execute three behaviors concurrently");

        ParallelBehaviour s = new ParallelBehaviour(this, WHEN_ALL){

            public int onEnd() {
                System.out.println("Behavior finalized with success!");
                return 0;
            }
        };

        addBehaviour(s);

        s.addSubBehaviour(new SimpleBehaviour(this){
            int quantity =1;
            public void action(){
                System.out.println("Behavior 1: Executing for the "+ quantity + " time");
                quantity = quantity +1;
            }

            public boolean done(){
                if(quantity==4) {
                    System.out.println("Behavior 1 - Finalized");
                    return true;
                }else
                    return false;
            }
        });

        s.addSubBehaviour(new SimpleBehaviour(this) {
            int quantity =1;
            public void action() {
                System.out.println("Behavior 2: Executing for the " + quantity + " time");
                quantity = quantity +1;
            }

            public boolean done() {
                if(quantity==8) {
                    System.out.println("Behavior 2 - Finalized");
                    return true;
                }else
                    return false;
            }
        });

        s.addSubBehaviour(new SimpleBehaviour(this){
            int quantity =1;
            public void action() {
                System.out.println("Behavior 3: Executing for the"+ quantity + " time");
                quantity = quantity +1;
            }

            public boolean done() {
                if( quantity==10) {
                    System.out.println(" Behavior 3 - Finalized");
                    return true;
                }else
                    return false;
            }
        });
    }
}
