package models;

import java.util.List;

public class Report {

  private String project;
  private int numApps;
  private double acceptRate;
  private List<String> topMajors;

  public Report(String project, int numApps, double acceptRate, List<String> topMajors) {
    this.project = project;
    this.numApps = numApps;
    this.acceptRate = acceptRate;
    this.topMajors = topMajors;
  }

  public String getProject() {
    return project;
  }

  public int getNumApps() {
    return numApps;
  }

  public double getAcceptRate() {
    return acceptRate;
  }

  public List<String> getTopMajors() {
    return topMajors;
  }
}
