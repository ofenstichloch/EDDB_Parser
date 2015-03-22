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
				while(reader.hasNext()){
					switch(reader.nextName()){
					case "id":
						sys.eddbID = reader.nextInt();
						break;
					case "name":
						sys.name = reader.nextString();
						break;
					case "x":
						sys.x = reader.nextDouble();
						break;
					case "y":
						sys.y = reader.nextDouble();
						break;
					case "z":
						sys.z = reader.nextDouble();
						break;
					case "faction":
						sys.faction = reader.nextOptionalString();
						break;
					case "population":
						sys.population = reader.nextOptionalLong();
						break;
					case "government":
						sys.gov = reader.nextOptionalString();
						break;
					case "allegiance":
						sys.allegiance = reader.nextOptionalString();
						break;
					case "state":
						sys.state = reader.nextOptionalString();
						break;
					case "security":
						sys.security = reader.nextOptionalString();
						break;
					case "primary_economy":
						sys.eco = reader.nextOptionalString();
						break;
					case "needs_permit":
						sys.permit = reader.nextOptionalInt();
						break;
					case "updated_at":
						reader.skipValue();
						break;
					default:
						reader.skipValue();
						break;
					}
				}

			reader.endObject();
			dbworker.insertSystemData(sys);

			
		}
		reader.endArray();
		reader.close();
		java.lang.System.out.println("Systems done");
		
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
						station.maxLandingPadSize = reader.nextOptionalString();
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
							while(reader.hasNext()){
								switch(reader.nextName()){
								case "id":
									reader.skipValue();
									break;
								case "station_id":
									reader.skipValue();
									break;
								case "commodity_id":
									data.eddbCommodityID = reader.nextInt();
									break;
								case "supply":
									data.supply = reader.nextOptionalInt();
									break;
								case "buy_price":
									data.buyPrice = reader.nextOptionalInt();
									break;
								case "demand":
									data.demand = reader.nextOptionalInt();
									break;
								case "sell_price":
									data.sellPrice = reader.nextOptionalInt();
									break;
								case "collected_at":
									data.timestamp = reader.nextOptionalLong();
									break;
								case "update_count":
									reader.skipValue();
									break;
								default:
									reader.skipValue();
									break;
								}
							}
					
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
