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
							+ "(eddbStationID, eddbCommodityID, buyPrice, sellPrice, supply, demand, timestamp, eddb) "
							+ "VALUES (?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE buyPrice=?, sellPrice=?, supply=?, demand=?, timestamp=?, eddb=?");
			statement.setInt(1, data.eddbStationID);
			statement.setInt(2, data.eddbCommodityID);
			statement.setInt(3, data.buyPrice);
			statement.setInt(4, data.sellPrice);
			statement.setInt(5, data.supply);
			statement.setInt(6, data.demand);
			statement.setLong(7, data.timestamp);
			statement.setBoolean(8, true);
			statement.setInt(9, data.buyPrice);
			statement.setInt(10, data.sellPrice);
			statement.setInt(11, data.supply);
			statement.setInt(12, data.demand);
			statement.setLong(13, data.timestamp);
			statement.setBoolean(14, true);
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
			statement.close();
			return true;
		}catch (SQLException e){
			java.lang.System.out.println(e);
		}
		return false;
		
	}

	public boolean insertStationData(Station station) {
		
		try{
			PreparedStatement statement = 
					conn.prepareStatement("INSERT INTO Stations (eddbID, eddbSystemID, name, maxLandingPadSize, distanceToStar, faction, hasBlackmarket, hasRefuel, hasRepair) VALUES (?,?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE name=?,maxLandingPadSize=?,distanceToStar=?,faction=?,hasBlackmarket=?,hasRefuel=?,hasRepair=?");
			
			statement.setInt(1, station.eddbID);
			statement.setInt(2, station.eddbSystemID);
			statement.setString(3, station.name);
			statement.setString(4, station.maxLandingPadSize);
			statement.setLong(5, station.distanceToStar);
			statement.setString(6, station.faction);
			statement.setBoolean(7, station.hasBlackmarket);
			statement.setBoolean(8, station.hasRefuel);
			statement.setBoolean(9, station.hasRepair);
			statement.setString(10, station.name);
			statement.setString(11, station.maxLandingPadSize);
			statement.setLong(12, station.distanceToStar);
			statement.setString(13, station.faction);
			statement.setBoolean(14, station.hasBlackmarket);
			statement.setBoolean(15, station.hasRefuel);
			statement.setBoolean(16, station.hasRepair);
			statement.executeUpdate();
			statement.close();
			return true;
		}catch (SQLException e){
			java.lang.System.out.println(e);
			java.lang.System.out.println(station.hasRefuel);
		}
		return false;
	}
	
	public boolean insertCommodityData(Commodity com){
		try{
			PreparedStatement statement = 
					conn.prepareStatement("INSERT INTO Commodities (eddbID, name, average, categoryID) VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE name=?,average=?, categoryID=?");
			
			statement.setInt(1, com.eddbID);
			statement.setString(2, com.name);
			statement.setInt(3, com.average);
			statement.setInt(4, com.categoryID);
			statement.setString(5, com.name);
			statement.setInt(6, com.average);
			statement.setInt(7, com.categoryID);
			statement.executeUpdate();
			statement.close();
			
			statement = conn.prepareStatement("INSERT INTO Categories (eddbID, name) VALUES (?,?) ON DUPLICATE KEY UPDATE name=?");
			statement.setInt(1, com.categoryID);
			statement.setString(2, com.category);
			statement.setString(3, com.category);
			statement.executeUpdate();
			statement.close();
			return true;
		}catch (SQLException e){
			java.lang.System.out.println(e);
		}
		return false;
	}
}
