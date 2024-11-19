package it.unibo.deathnote.impl;

import java.util.Map;
import java.util.HashMap;
import java.util.Objects;
import it.unibo.deathnote.api.DeathNote;

public class DeathNoteImplementation implements DeathNote {

    private static final int INDEX = 1;
    private static final long TIME_CAUSE_DEATH = 40L;
    private static final long TIME_DETAIL_DEATH = 6400L;
    private long time;
    private String lastName;
    private final Map<String, Death> deathNote;

    enum choice {
        DEATH,
        DETAIL
    }

    public DeathNoteImplementation() {
        this.deathNote = new HashMap<>();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + ((deathNote == null) ? 0 : deathNote.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DeathNoteImplementation other = (DeathNoteImplementation) obj;
        if (lastName == null) {
            if (other.lastName != null)
                return false;
        } else if (!lastName.equals(other.lastName))
            return false;
        if (deathNote == null) {
            if (other.deathNote != null)
                return false;
        } else if (!deathNote.equals(other.deathNote))
            return false;
        return true;
    }

    @Override
    public String getRule(int ruleNumber) {
        if (ruleNumber < 1 || ruleNumber > 13) {
            throw new IllegalArgumentException();
        }
        return RULES.get(ruleNumber - INDEX);
    }

    @Override
    public void writeName(String name) {
        if (Objects.requireNonNull(name) == null) {
            throw new NullPointerException();
        }
        this.lastName = name;
        this.deathNote.put(name, null);
        this.time = System.nanoTime();
    }

    @Override
    public boolean writeDeathCause(String cause) {
        inputAndDeathTest(cause);
        if (timeTest(TIME_CAUSE_DEATH)) {
            createDeathIfNotExist();
            setDeathDetails(cause, choice.DEATH);
            return true;
        }
        return false;
    }

    @Override
    public boolean writeDetails(String details) {
        inputAndDeathTest(details);
        if (timeTest(TIME_DETAIL_DEATH)) {
            createDeathIfNotExist();
            setDeathDetails(details, choice.DETAIL);
            return true;
        }
        return false;
    }

    private void setDeathDetails(String input, choice choice) {
        Death tmpDeath = deathNote.get(lastName);
        if (choice == DeathNoteImplementation.choice.DETAIL) {
            tmpDeath.setDetail(input);
        } else {
            tmpDeath.setDeath(input);
        }
    }

    private void inputAndDeathTest(String input) {
        if (Objects.requireNonNull(input) == null || deathNote.keySet() == null) {
            throw new IllegalStateException();
        }
    }

    private void createDeathIfNotExist() {
        if (deathNote.get(lastName) == null) {
            deathNote.put(lastName, new Death());
        }
    }

    private boolean timeTest(long timeTest) {
        return Long.compare(this.time - System.nanoTime(), timeTest) <= 0;
    }

    @Override
    public String getDeathCause(String name) {
        return deathNote.get(name).getDeath();
    }

    @Override
    public String getDeathDetails(String name) {
        return deathNote.get(name).getDetail();
    }

    @Override
    public boolean isNameWritten(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isNameWritten'");
    }

    private static final class Death {
        private String deathCause;
        private String deathDetail;

        private void setDeath(String deathCause) {
            this.deathCause = deathCause;
        }

        private void setDetail(String deathDetail) {
            this.deathDetail = deathDetail;
        }

        private String getDeath() {
            return this.deathCause;
        }

        private String getDetail() {
            return this.deathDetail;
        }

    }

}
