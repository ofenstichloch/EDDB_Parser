/**
 * Represents one commodity in one station.
 *  Required fields are: systemName, stationName, itemName, stationStock, sellPrice, demand, timestamp.
 *  See http://schemas.elite-markets.net/eddn/commodity/1 for mor information.
*/
public class MarketData {
	public int eddbSystemID;
	public int eddbStationID;
	public int eddbCommodityID;
	public Integer buyPrice;
	public Integer supply;
	public Integer sellPrice;
	public Integer demand;
	public long timestamp;
	
	public void print(){
		java.lang.System.out.println(eddbCommodityID + " " +eddbStationID + " "+ buyPrice);
	}

}
