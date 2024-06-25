package org.maperz.scoreboard.domain.model;

public class Team {
    private final String name;

    public Team(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Team team = (Team) obj;
        return name.equals(team.name);
    }
}
