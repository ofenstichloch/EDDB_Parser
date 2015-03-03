import java.sql.*;
public class DBWorker {
	
	private Connection conn = null;
	
	public DBWorker(){
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/admin_EDDN?user=EDDN&password=");
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	/**
	 *  Fill in a commodity price listing
	 * MySQL Statement: 
	 * INSERT INTO Commodities (eddbSystemID, eddbStationID, eddbCommodityID, buyPrice, sellPrice, supply, demand, timestamp, eddb) 
	 * VALUES ($1,$2,$3,$4,$5,$6,$7,$8,true) ON DUPLICATE KEY UPDATE buyPrice=$9, sellPrice=$10, supply=$11, demand=$12, timestamp=$13, true
	 */
	
	public boolean insertMarketData(MarketData data){
		//Check if system and station is listed
		
		//Fill in data
	
		try{
			PreparedStatement statement = 
					conn.prepareStatement("INSERT INTO Commodities "
							+ "(eddbSystemID, eddbStationID, eddbCommodityID, buyPrice, sellPrice, supply, demand, timestamp, eddb) "
							+ "VALUES (?,?,?,?,?,?,?,?,true) ON DUPLICATE KEY UPDATE buyPrice=?, sellPrice=?, supply=?, demand=?, timestamp=?, true");
			statement.setInt(1, data.eddbSystemID);
			statement.setInt(2, data.eddbStationID);
			statement.setInt(3, data.eddbCommodityID);
			statement.setInt(4, data.buyPrice);
			statement.setInt(5, data.sellPrice);
			statement.setInt(6, data.supply);
			statement.setInt(7, data.demand);
			statement.setLong(8, data.timestamp);
			
			statement.executeUpdate();
			
		}catch (SQLException e){
		}
		return false;
	}
	
}
