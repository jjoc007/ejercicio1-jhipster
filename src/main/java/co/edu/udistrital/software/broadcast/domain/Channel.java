package co.edu.udistrital.software.broadcast.domain;

import io.swagger.annotations.ApiModel;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * Channel entity.
 */
@ApiModel(description = "Channel entity.")
@Entity
@Table(name = "channel")
public class Channel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    private String type;

    @OneToMany(mappedBy = "channels")
    private Set<Day> days = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public Channel type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Day> getDays() {
        return days;
    }

    public Channel days(Set<Day> days) {
        this.days = days;
        return this;
    }

    public Channel addDay(Day day) {
        this.days.add(day);
        day.setChannels(this);
        return this;
    }

    public Channel removeDay(Day day) {
        this.days.remove(day);
        day.setChannels(null);
        return this;
    }

    public void setDays(Set<Day> days) {
        this.days = days;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Channel)) {
            return false;
        }
        return id != null && id.equals(((Channel) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Channel{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            "}";
    }
}
