package dev.gustavpersson.thorincompanies.business_logic_layer.models;

import java.util.Date;
import java.util.UUID;

public class Company {

    public int id;

    //The display name for the company
    public String name;

    //The original founder
    public UUID founderUUID;
    private Date createdAt;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getFounderUUID() {
        return founderUUID;
    }

    public void setFounderUUID(UUID founderUUID) {
        this.founderUUID = founderUUID;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

}
