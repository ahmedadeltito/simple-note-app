package com.ahmedadel.noteapp.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Ahmed Adel on 3/28/18.
 * <p>
 * Note is the actual model of note object
 */

public class Note extends RealmObject {

    @PrimaryKey
    @SerializedName("_id")
    private String id;

    @SerializedName("note_name")
    private String name;

    @SerializedName("note_description")
    private String description;

    @SerializedName("note_priority")
    private String priority;

    @SerializedName("created_at")
    private String createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Note)) return false;

        Note note = (Note) o;

        return id.equals(note.id) &&
                name.equals(note.name) &&
                description.equals(note.description) &&
                priority.equals(note.priority) &&
                createdAt.equals(note.createdAt);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + priority.hashCode();
        result = 31 * result + createdAt.hashCode();
        return result;
    }

    public String toJsonString() {
        return "{"
                + "\"id\":\"" + id + "\""
                + ", \"note_name\":\"" + name + "\""
                + ", \"note_description\":\"" + description + "\""
                + ", \"note_priority\":\"" + priority + "\""
                + ", \"created_at\":\"" + createdAt + "\""
                + "}";
    }
}
