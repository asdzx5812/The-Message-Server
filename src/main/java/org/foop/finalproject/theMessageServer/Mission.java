package org.foop.finalproject.theMessageServer;

public abstract class Mission{
    protected String description;
    abstract protected boolean isCompleted();

    public String getDescription() {
        return description;
    }
}