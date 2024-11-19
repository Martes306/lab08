package it.unibo.deathnote;

import it.unibo.deathnote.impl.DeathNoteImplementation;

public class Prova {
    public static void main(String[] args) {
        DeathNoteImplementation test = new DeathNoteImplementation();
        System.out.println(test.getRule(1));
        test.writeName("ettore");
        test.writeDeathCause("un coccolone");
        System.out.print(test.getDeathCause("ettore"));
    }
}
