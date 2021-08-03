public class ScriptEntityDescription {

  public int health;
  
  public float height;
  
  public float width;
  
  public float moveSpeed;
  
  public String texture;
  
  public String onCreated;
  
  public String onUpdate;
  
  public String onAttacked;
  
  public String onPathReached;
  
  public String onDeath;
  
  public String onInteraction;
  
  public ScriptEntityDescription(String descName) {
    this.health = 10;
    this.height = 1.8F;
    this.width = 0.6F;
    this.moveSpeed = 0.7F;
    this.onCreated = "";
    this.onUpdate = "";
    this.onAttacked = "";
    this.onPathReached = "";
    this.onDeath = "";
    this.onInteraction = "";
    EntityDescriptions.addDescription(descName, this);
  }
}
