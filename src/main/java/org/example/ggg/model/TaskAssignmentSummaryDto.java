package org.example.ggg.model;

public class TaskAssignmentSummaryDto {
    private int totalTasks;
    private int completedTasks;
    private int pendingTasks;
    private int daysUntilNextDue;

    public TaskAssignmentSummaryDto(int totalTasks, int completedTasks, int pendingTasks, int daysUntilNextDue) {
        this.totalTasks = totalTasks;
        this.completedTasks = completedTasks;
        this.pendingTasks = pendingTasks;
        this.daysUntilNextDue = daysUntilNextDue;
    }

    // Getters here
    public int getTotalTasks() { return totalTasks; }
    public int getCompletedTasks() { return completedTasks; }
    public int getPendingTasks() { return pendingTasks; }
    public int getDaysUntilNextDue() { return daysUntilNextDue; }
}

