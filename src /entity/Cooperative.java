package gr.hua.club.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
@Table(name = "associations")
public class Association {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String name;

    @Column
    private String registrationNumber;

    @Column
    private String status;

    @Column
    private String notes;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "manager_id")
    @JsonBackReference
    private User manager;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name="association_members",
            joinColumns = @JoinColumn(name="association_id"),
            inverseJoinColumns = @JoinColumn(name="member_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames={"member_id", "association_id"})}
    )
    private List<User> members;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name="association_events",
            joinColumns = @JoinColumn(name="association_id"),
            inverseJoinColumns = @JoinColumn(name="event_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames={"event_id", "association_id"})}
    )
    private List<Event> events;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name="association_venues",
            joinColumns = @JoinColumn(name="association_id"),
            inverseJoinColumns = @JoinColumn(name="venue_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames={"venue_id", "association_id"})}
    )
    private List<Venue> venues;

    public Association() {
        this.status = "active";
        this.notes = "";
    }

    public Association(String name, String registrationNumber) {
        this();
        this.name = name;
        this.registrationNumber = registrationNumber;
    }

    // Getters and Setters...

    public void addMember(User member) {
        members.add(member);
    }

    public void removeMember(User member) {
        members.remove(member);
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public void removeEvent(Event event) {
        events.remove(event);
    }

    public void addVenue(Venue venue) {
        venues.add(venue);
    }

    public void removeVenue(Venue venue) {
        venues.remove(venue);
    }

    public String validate() {
        if (status.equalsIgnoreCase("inactive")) {
            return "Association is inactive.";
        }
        if (!(members.isEmpty() || events.isEmpty() || venues.isEmpty()) && isRegistrationValid()) {
            return "Association is valid.";
        } else {
            return validationErrorString();
        }
    }

    private String validationErrorString() {
        String validationErrors = "";
        if (members.isEmpty()) {
            validationErrors += "No members,";
        }
        if (events.isEmpty()) {
            validationErrors += "No events,";
        }
        if (venues.isEmpty()) {
            validationErrors += "No venues,";
        }
        if (!isRegistrationValid()) {
            validationErrors += "Invalid registration number.";
        }
        return validationErrors;
    }

    private boolean isRegistrationValid() {
        return registrationNumber != null && registrationNumber.matches("\\d{6}");
    }

    @PreRemove
    private void preRemove() {
        if (manager != null) {
            manager.removeAssociation(this);
        }
        for (User member : members) {
            member.removeAssociation(this);
        }
        for (Event event : events) {
            event.removeAssociation(this);
        }
        for (Venue venue : venues) {
            venue.removeAssociation(this);
        }
    }

    @Override
    public String toString() {
        return "Association{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", status='" + status + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}

