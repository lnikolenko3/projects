package models;

public class PopularProject {

  private String name;
  private int numApps;

  public PopularProject(String name, int numApps) {
    this.name = name;
    this.numApps = numApps;
  }

  public String getName() {
    return name;
  }

  public int getNumApps() {
    return numApps;
  }
}
