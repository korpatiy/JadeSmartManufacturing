package org.manufacture.Agents;

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

  /*  private void startWorking() {

        System.out.println("[" + getLocalName() +
                "]: начал производство детали...");

        FSMBehaviour fsmBehaviour = new FSMBehaviour(this) {
            @Override
            public int onEnd() {
                System.out.println("FSM Behavior completed successfully!");
                return 0;
            }
        };

        fsmBehaviour.registerFirstState(new WakerBehaviour(this, 1000) {
            @Override
            protected void handleElapsedTimeout() {
                System.out.println("lel");
            }

            @Override
            public int onEnd() {
                return 5;
            }
        }, "L");

        fsmBehaviour.registerState(new SimpleBehaviour(this) {

            int step = 0;

            @Override
            public void action() {
                System.out.println("[" + getLocalName() +
                        "]: Отправляю на проверку...");
                ACLMessage msg = new ACLMessage(ACLMessage.QUERY_IF);
                step++;
            }

            @Override
            public boolean done() {
                return true;
            }

            @Override
            public int onEnd() {
                return (step > 4 ? 1 : 0);
            }
        }, "A");

        fsmBehaviour.registerState(new OneShotBehaviour(this) {

            @Override
            public void action() {
                System.out.println("[" + getLocalName() +
                        "]: Не удалось сделать...");
            }

            @Override
            public int onEnd() {
                return 2;
            }
        }, "C");

        fsmBehaviour.registerLastState(new OneShotBehaviour(this) {
            @Override
            public void action() {
                System.out.println("[" + getLocalName() +
                        "]: Оповещаю менеджера...");
            }
        }, "B");

        fsmBehaviour.registerTransition("L", "A", 5);
        fsmBehaviour.registerTransition("A", "C", 0);
        fsmBehaviour.registerTransition("A", "B", 1);
        fsmBehaviour.registerDefaultTransition("C", "L", new String[]{"L", "C"});
        addBehaviour(fsmBehaviour);
    }*/
