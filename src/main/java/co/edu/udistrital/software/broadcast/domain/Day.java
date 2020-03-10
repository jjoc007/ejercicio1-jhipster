package co.edu.udistrital.software.broadcast.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Day entity.
 */
@ApiModel(description = "Day entity.")
@Entity
@Table(name = "day")
public class Day implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private String date;

    @ManyToOne
    @JsonIgnoreProperties("days")
    private Channel channels;

    @ManyToOne
    @JsonIgnoreProperties("days")
    private Calendar calendars;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public Day date(String date) {
        this.date = date;
        return this;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Channel getChannels() {
        return channels;
    }

    public Day channels(Channel channel) {
        this.channels = channel;
        return this;
    }

    public void setChannels(Channel channel) {
        this.channels = channel;
    }

    public Calendar getCalendars() {
        return calendars;
    }

    public Day calendars(Calendar calendar) {
        this.calendars = calendar;
        return this;
    }

    public void setCalendars(Calendar calendar) {
        this.calendars = calendar;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Day)) {
            return false;
        }
        return id != null && id.equals(((Day) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Day{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
