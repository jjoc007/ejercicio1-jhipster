package co.edu.udistrital.software.broadcast.domain;

import io.swagger.annotations.ApiModel;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * Category entity.
 */
@ApiModel(description = "Category entity.")
@Entity
@Table(name = "category")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(name = "category_id",
               joinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "id_id", referencedColumnName = "id"))
    private Set<Transmition> ids = new HashSet<>();

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

    public Category name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Transmition> getIds() {
        return ids;
    }

    public Category ids(Set<Transmition> transmitions) {
        this.ids = transmitions;
        return this;
    }

    public Category addId(Transmition transmition) {
        this.ids.add(transmition);
        transmition.getIds().add(this);
        return this;
    }

    public Category removeId(Transmition transmition) {
        this.ids.remove(transmition);
        transmition.getIds().remove(this);
        return this;
    }

    public void setIds(Set<Transmition> transmitions) {
        this.ids = transmitions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Category)) {
            return false;
        }
        return id != null && id.equals(((Category) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Category{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
