import java.io.*;
import java.sql.SQLException;

public class DataParser {

	private JsonParser reader;
	private FileReader file;
	private DBWorker dbworker;

	public DataParser(String filepath, int mode){
		boolean go = true;
		try {
			file = new FileReader(new File(filepath));
			reader = new JsonParser(file);
			
		} catch (FileNotFoundException e) {
			go=false;
			e.printStackTrace();
		}

		try {
			dbworker = new DBWorker();
		} catch (SQLException e1) {
			go=false;
			e1.printStackTrace();
		}
		
		if(go){
			try{
			if(mode==0){parseSystems();}
			else if (mode==1){parseCommodities();}
			else if (mode==2){parseStations();}
			}
			catch(IOException e){java.lang.System.out.println(e);}
		}
	}
	
	/**
	 * Parses the EDDB Systems.json
	 * Expecting this schema:
	 * 	[
			{
				"id":int,
				"name":string,
				"x":double,
				"y":double,
				"z":double,
				"faction":string or null,		
				"population":long or null,
				"government":string or null,
				"allegiance":string or null,
				"state":string or null,
				"security":string or null,
				"primary_economy":string or null
			}
		]		
	 * All fields are required
	 * @throws IOException
	 */
	public void parseSystems() throws IOException{
		reader.beginArray();
		while(reader.hasNext()){
			System sys = new System();
			reader.beginObject();
				reader.skipValue();
				sys.eddbID = reader.nextInt();
				reader.skipValue();
				sys.name = reader.nextString();
				reader.skipValue();
				sys.x = reader.nextDouble();
				reader.skipValue();
				sys.y = reader.nextDouble();
				reader.skipValue();
				sys.z = reader.nextDouble();
				reader.skipValue();
				sys.faction = reader.nextOptionalString();
				reader.skipValue();
				sys.population = reader.nextOptionalLong();
				reader.skipValue();
				sys.gov = reader.nextOptionalString();
				reader.skipValue();
				sys.allegiance = reader.nextOptionalString();
				reader.skipValue();
				sys.state = reader.nextOptionalString();
				reader.skipValue();
				sys.security = reader.nextOptionalString();
				reader.skipValue();
				sys.eco = reader.nextOptionalString();
			reader.endObject();
			dbworker.insertSystemData(sys);

			
		}
		reader.endArray();
		reader.close();
		
	}
	
	/**
	 * Expected schema:
	 * [
			{
			"id":int,
			"name":string,
			"system_id":int,
			"max_landing_pad_size":int or null,
			"distance_to_star":int or null,
			"faction":string or null,		skipped
			"government":string or null,	skipped
			"allegiance":string or null,	skipped
			"state":string or null,			skipped
			"type":string or null,			skipped
			"has_blackmarket":int or null,	skipped
			"has_commodities":int or null,	skipped
			"has_refuel":int or null,		skipped
			"has_repair":int or null,		skipped
			"has_rearm":int or null,		skipped
			"has_outfitting":int or null,	skipped
			"has_shipyard":int or null,		skipped
			"import_commodities":[],		skipped
			"export_commodities":[],		skipped
			"prohibited_commodities":[],	skipped
			"economies":[],					skipped
			"listings":[{
				"id":int,
				"station_id":int,
				"commodity_id":int,
				"supply":int,
				"buy_price":int,
				"sell_price":int,
				"demand":int,
				"collected_at":long
				}]
			}
		]
	 * @throws IOException
	 */
	public void parseStations() throws IOException{
		reader.beginArray();
		while(reader.hasNext()){
			Station station = new Station();
			reader.beginObject();
				while(reader.hasNext()){
					int tmp;
					switch (reader.nextName()) {
					case "id":
						station.eddbID = reader.nextInt();
						break;
					case "name":
						station.name = reader.nextString();
						break;
					case "system_id":
						station.eddbSystemID = reader.nextInt();
						break;
					case "max_landing_pad_size":
						station.maxLandingPadSize = reader.nextOptionalInt();
						break;
					case "distance_to_star":
						station.distanceToStar = reader.nextOptionalLong();
						break;
					case "faction":
						station.faction = reader.nextOptionalString();
						break;
					case "government":
						station.gov = reader.nextOptionalString(); 
						break;
					case "allegiance":
						station.allegiance = reader.nextOptionalString();
						break;
					case "state":
						station.state = reader.nextOptionalString();
						break;
					case "type":
						station.type = reader.nextOptionalString();
						break;
					case "has_blackmarket":
						tmp = reader.nextOptionalInt();
						if(tmp < 1){station.hasBlackmarket = false;}
						else{station.hasBlackmarket = true;}
						break;
					case "has_commodities":
						tmp = reader.nextOptionalInt();
						if(tmp < 1){station.hasCommodities= false;}
						else{station.hasCommodities = true;}
						break;
					case "has_refuel":
						tmp = reader.nextOptionalInt();
						if(tmp < 1){station.hasRefuel= false;}
						else{station.hasRefuel = true;}
						break;
					case "has_repair":
						tmp = reader.nextOptionalInt();
						if(tmp < 1){station.hasRepair= false;}
						else{station.hasRepair = true;}
						break;
					case "has_rearm":
						tmp = reader.nextOptionalInt();
						if(tmp < 1){station.hasRearm= false;}
						else{station.hasRearm = true;}
						break;
					case "has_outfitting":
						tmp = reader.nextOptionalInt();
						if(tmp < 1){station.hasOutfitting= false;}
						else{station.hasOutfitting = true;}
						break;
					case "has_shipyard":
						tmp = reader.nextOptionalInt();
						if(tmp < 1){station.hasShipyard= false;}
						else{station.hasShipyard = true;}
						break;
					case "import_commodities":
						reader.skipValue();
						break;
					case "export_commodities":
						reader.skipValue();
						break;
					case "prohibited_commodities":
						reader.skipValue();
						break;
					case "economies":
						reader.skipValue();
						break;
					case "listings":
						reader.beginArray();
						dbworker.insertStationData(station);
						while(reader.hasNext()){
							MarketData data = new MarketData();
							data.eddbStationID = station.eddbID;
							reader.beginObject();
							reader.skipValue();reader.skipValue();
							reader.skipValue();reader.skipValue();
							reader.skipValue();
							data.eddbCommodityID = reader.nextInt();
							reader.skipValue();
							data.supply = reader.nextOptionalInt();
							reader.skipValue();
							data.buyPrice = reader.nextOptionalInt();
							reader.skipValue();
							data.sellPrice = reader.nextOptionalInt();
							reader.skipValue();
							data.demand = reader.nextOptionalInt();
							reader.skipValue();
							data.timestamp = reader.nextOptionalLong();
							reader.endObject();
							dbworker.insertMarketData(data);
						}
						reader.endArray();
						break;
					default:
						reader.skipValue();
						break;
					}

				}
			reader.endObject();
		}
		reader.endArray();
		
		reader.close();
	}
	
	public void parseCommodities() throws IOException{
		reader.beginArray();
		while(reader.hasNext()){
			Commodity comm = new Commodity();
			reader.beginObject();
				reader.skipValue();
				comm.eddbID = reader.nextInt();
				reader.skipValue();
				comm.name = reader.nextString();
				reader.skipValue();reader.skipValue();
				reader.skipValue();
				comm.average = reader.nextOptionalInt();
				reader.skipValue();
				reader.beginObject();
					reader.skipValue();
					comm.categoryID = reader.nextInt();
					reader.skipValue();
					comm.category = reader.nextOptionalString();
				reader.endObject();
			reader.endObject();
			dbworker.insertCommodityData(comm);
		}
	
		reader.endArray();
		reader.close();
		
	}
}
