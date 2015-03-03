
public class Station {
	public String name;
	public int eddbSystemID;
	public int eddbID;
	public int maxLandingPadSize;
	public long distanceToStar;
	public String faction;
	public String gov;
	public String allegiance;
	public String state;
	public String type;
	public boolean hasBlackmarket;
	public boolean hasCommodities;
	public boolean hasRefuel;
	public boolean hasRepair;
	public boolean hasRearm;
	public boolean hasOutfitting;
	public boolean hasShipyard;

	public void print(){
		java.lang.System.out.println(name+" "+eddbSystemID);
	}
}
