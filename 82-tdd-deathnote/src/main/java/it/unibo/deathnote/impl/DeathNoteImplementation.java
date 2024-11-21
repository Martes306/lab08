package it.unibo.deathnote.impl;

import java.util.Map;
import java.util.HashMap;
import java.util.Objects;
import it.unibo.deathnote.api.DeathNote;

public class DeathNoteImplementation implements DeathNote {

    private static final int INDEX = 1;
    private static final long TIME_NS = 1_000_000L;
    private static final long TIME_CAUSE_DEATH = 40L * TIME_NS;
    private static final long TIME_DETAIL_DEATH = 64L * TIME_NS;
    private long time;
    private String lastName;
    private final Map<String, Death> deathNote;

    enum choice {
        DETAIL {
            @Override
            void apply(Death death, String input) {
                death.setDetail(input);
            }
        },
        DEATH {
            @Override
            void apply(Death death, String input) {
                death.setDeath(input);
            }
        };

        abstract void apply(Death death, String input);
    }

    public DeathNoteImplementation() {
        this.deathNote = new HashMap<>();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
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
        if (deathNote == null) {
            if (other.deathNote != null)
                return false;
        } else if (!deathNote.equals(other.deathNote))
            return false;
        return true;
    }

    @Override
    public String getRule(int ruleNumber) {
        if (ruleNumber < 1 || ruleNumber > RULES.size()) {
            throw new IllegalArgumentException("Rule index " + ruleNumber + " does not exist");
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
        choice.apply(tmpDeath, input);
    }

    private void inputAndDeathTest(String input) {
        if (Objects.requireNonNull(input) == null || deathNote.isEmpty()) {
            throw new IllegalStateException("The death note is empty or the input is null");
        }
    }

    private void createDeathIfNotExist() {
        if (deathNoteIsNull()) {
            deathNote.put(lastName, new Death());
        }
    }

    private boolean deathNoteIsNull() {
        return deathNote.get(lastName) == null;
    }

    private boolean timeTest(long timeTest) {
        return Long.compare(System.nanoTime() - this.time, timeTest) <= 0;
    }

    @Override
    public String getDeathCause(String name) {
        nameInMap(name);
        if (deathNoteIsNull() || deathNote.get(name).getDeath() == null) {
            return "heart attack";
        } else {
            return deathNote.get(name).getDeath();
        }
    }

    @Override
    public String getDeathDetails(String name) {
        nameInMap(name);
        if (deathNoteIsNull() || deathNote.get(name).getDetail() == null) {
            return "";
        } else {
            return deathNote.get(name).getDetail();
        }
    }

    private void nameInMap(String name) {
        if (!isNameWritten(name)) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean isNameWritten(String name) {
        return deathNote.containsKey(name);
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
