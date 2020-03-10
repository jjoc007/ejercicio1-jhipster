package co.edu.udistrital.software.broadcast.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * Transmition entity.
 */
@ApiModel(description = "Transmition entity.")
@Entity
@Table(name = "transmition")
public class Transmition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "transmitions")
    private Set<Calendar> calendars = new HashSet<>();

    @ManyToMany(mappedBy = "ids")
    @JsonIgnore
    private Set<Category> ids = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Transmition name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Calendar> getCalendars() {
        return calendars;
    }

    public Transmition calendars(Set<Calendar> calendars) {
        this.calendars = calendars;
        return this;
    }

    public Transmition addCalendar(Calendar calendar) {
        this.calendars.add(calendar);
        calendar.setTransmitions(this);
        return this;
    }

    public Transmition removeCalendar(Calendar calendar) {
        this.calendars.remove(calendar);
        calendar.setTransmitions(null);
        return this;
    }

    public void setCalendars(Set<Calendar> calendars) {
        this.calendars = calendars;
    }

    public Set<Category> getIds() {
        return ids;
    }

    public Transmition ids(Set<Category> categories) {
        this.ids = categories;
        return this;
    }

    public Transmition addId(Category category) {
        this.ids.add(category);
        category.getIds().add(this);
        return this;
    }

    public Transmition removeId(Category category) {
        this.ids.remove(category);
        category.getIds().remove(this);
        return this;
    }

    public void setIds(Set<Category> categories) {
        this.ids = categories;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transmition)) {
            return false;
        }
        return id != null && id.equals(((Transmition) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Transmition{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
