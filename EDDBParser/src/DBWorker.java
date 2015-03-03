import java.sql.*;
public class DBWorker {
	
	private Connection conn = null;
	
	public DBWorker() throws SQLException{
		conn = DriverManager.getConnection("jdbc:mysql://localhost/admin_EDDN?user=EDDN&password=");
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
					conn.prepareStatement("INSERT INTO MarketData "
							+ "(eddbSystemID, eddbStationID, eddbCommodityID, buyPrice, sellPrice, supply, demand, timestamp, eddb) "
							+ "VALUES (?,?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE buyPrice=?, sellPrice=?, supply=?, demand=?, timestamp=?, eddb=?");
			statement.setInt(1, data.eddbSystemID);
			statement.setInt(2, data.eddbStationID);
			statement.setInt(3, data.eddbCommodityID);
			statement.setInt(4, data.buyPrice);
			statement.setInt(5, data.sellPrice);
			statement.setInt(6, data.supply);
			statement.setInt(7, data.demand);
			statement.setLong(8, data.timestamp);
			statement.setBoolean(9, true);
			statement.setInt(10, data.buyPrice);
			statement.setInt(11, data.sellPrice);
			statement.setInt(12, data.supply);
			statement.setInt(13, data.demand);
			statement.setLong(14, data.timestamp);
			statement.setBoolean(15, true);
			statement.executeUpdate();
			statement.close();
			return true;
		}catch (SQLException e){
			java.lang.System.out.println(e);
		}
		return false;
	}
	
	public boolean insertSystemData(System sys){
		try{
			PreparedStatement statement = 
					conn.prepareStatement("INSERT INTO Systems (eddbID, name, x, y, z) VALUES (?,?,?,?,?) ON DUPLICATE KEY UPDATE name=?, x=?, y=?, z=?");
			
			statement.setInt(1, sys.eddbID);
			statement.setString(2, sys.name);
			statement.setDouble(3, sys.x);
			statement.setDouble(4, sys.y);
			statement.setDouble(5, sys.z);
			statement.setString(6, sys.name);
			statement.setDouble(7, sys.x);
			statement.setDouble(8, sys.y);
			statement.setDouble(9, sys.z);
			statement.executeUpdate();
			return true;
		}catch (SQLException e){
			java.lang.System.out.println(e);
		}
		return false;
		
	}
	
}
