package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static java.lang.Thread.sleep;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.deathnote.impl.DeathNoteImplementation;

public class TestDeathNote {

    private static final String NAME_1 = "Marco Aresu";
    private static final String NAME_2 = "Andrea Fronzi";
    private static final String EMPTY_NAME = "";
    private static final int TIME_1 = 100;
    private static final int TIME_2 = 6400;
    private DeathNoteImplementation note1;
    private DeathNoteImplementation note2;
    private DeathNoteImplementation note3;

    @BeforeEach
    void setup() {
        this.note1 = new DeathNoteImplementation();
        this.note2 = new DeathNoteImplementation();
        this.note3 = new DeathNoteImplementation();
    }

    @Test
    void testRule() {
        try {
            this.note1.getRule(-1);
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
            assertFalse(e.getMessage().isEmpty());
            assertFalse(e.getMessage().isBlank());
        }
    }

    @Test
    void testAllRule() {
        for (int i = 1; i < 14; i++) {
            final String tmp = note1.getRule(i);
            assertNotNull(tmp);
            assertFalse(tmp.isBlank());
        }
    }

    @Test
    void nameIsWritten() {
        assertFalse(note1.isNameWritten(NAME_1));
        note1.writeName(NAME_1);
        assertTrue(note1.isNameWritten(NAME_1));
        assertFalse(note1.isNameWritten(NAME_2));
        assertFalse(note1.isNameWritten(EMPTY_NAME));
    }

    @Test
    void testDeathCause() throws InterruptedException{
        try {
            note2.writeDeathCause("coccolone");
        } catch (IllegalStateException e) {
            note2.writeName(NAME_1);
            assertEquals( "heart attack", note2.getDeathCause(NAME_1));
            note2.writeName(NAME_2);
            note2.writeDeathCause("kart accident");
            assertEquals("kart accident", note2.getDeathCause(NAME_2));
            sleep(TIME_1);
            note2.writeDeathCause("coccolone pure a te");
            assertEquals("kart accident", note2.getDeathCause(NAME_2));
        }
    }

    @Test
    void testDeathDetails() throws InterruptedException{
        try {
            note3.writeDetails("saltato da un burrone");
        } catch (Exception e) {
            note3.writeName(NAME_1);
            assertEquals("", note3.getDeathDetails(NAME_1));
            note3.writeDetails("ran for too long");
            assertEquals("ran for too long", note3.getDeathDetails(NAME_1));
            note3.writeName(NAME_2);
            sleep(TIME_2);
            note3.writeDetails("pestato a morte");
            assertEquals("", note3.getDeathDetails(NAME_2));
        }
    }
}