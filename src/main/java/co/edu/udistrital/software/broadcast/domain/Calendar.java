package co.edu.udistrital.software.broadcast.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * Calendar entity.
 */
@ApiModel(description = "Calendar entity.")
@Entity
@Table(name = "calendar")
public class Calendar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "initial_hour")
    private String initialHour;

    @Column(name = "final_hour")
    private String finalHour;

    @OneToMany(mappedBy = "calendars")
    private Set<Day> days = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("calendars")
    private Transmition transmitions;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInitialHour() {
        return initialHour;
    }

    public Calendar initialHour(String initialHour) {
        this.initialHour = initialHour;
        return this;
    }

    public void setInitialHour(String initialHour) {
        this.initialHour = initialHour;
    }

    public String getFinalHour() {
        return finalHour;
    }

    public Calendar finalHour(String finalHour) {
        this.finalHour = finalHour;
        return this;
    }

    public void setFinalHour(String finalHour) {
        this.finalHour = finalHour;
    }

    public Set<Day> getDays() {
        return days;
    }

    public Calendar days(Set<Day> days) {
        this.days = days;
        return this;
    }

    public Calendar addDay(Day day) {
        this.days.add(day);
        day.setCalendars(this);
        return this;
    }

    public Calendar removeDay(Day day) {
        this.days.remove(day);
        day.setCalendars(null);
        return this;
    }

    public void setDays(Set<Day> days) {
        this.days = days;
    }

    public Transmition getTransmitions() {
        return transmitions;
    }

    public Calendar transmitions(Transmition transmition) {
        this.transmitions = transmition;
        return this;
    }

    public void setTransmitions(Transmition transmition) {
        this.transmitions = transmition;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Calendar)) {
            return false;
        }
        return id != null && id.equals(((Calendar) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Calendar{" +
            "id=" + getId() +
            ", initialHour='" + getInitialHour() + "'" +
            ", finalHour='" + getFinalHour() + "'" +
            "}";
    }
}
