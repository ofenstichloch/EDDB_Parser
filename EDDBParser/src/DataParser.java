import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.io.*;

public class DataParser {

	private JsonParser reader;
	private FileReader file;
	
	public DataParser(String filepath, int mode){
		try {
			file = new FileReader(new File(filepath));
			reader = new JsonParser(file);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		try{
		if(mode==0){parseSystems();}
		else if (mode==1){parseCommodities();}
		else if (mode==2){parseStations();}
		}
		catch(IOException e){}
	}
	
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
			sys.print();
		}
		reader.endArray();
		reader.close();
	}
	
	public void parseStations() throws IOException{
		reader.beginArray();
		while(reader.hasNext()){
			Station station = new Station();
			reader.beginObject();
				reader.skipValue();
				station.eddbID = reader.nextInt();
				reader.skipValue();
				station.name = reader.nextString();
				reader.skipValue();
				station.eddbSystemID = reader.nextInt();
				reader.skipValue();
				station.maxLandingPadSize = reader.nextOptionalString();
				reader.skipValue();
				station.distanceToStar = reader.nextOptionalLong();
				reader.skipValue();reader.skipValue(); //faction
				reader.skipValue();reader.skipValue(); //gov
				reader.skipValue();reader.skipValue(); //allegiance
				reader.skipValue();reader.skipValue(); //state
				reader.skipValue();reader.skipValue(); //type
				reader.skipValue();reader.skipValue(); //BM
				reader.skipValue();reader.skipValue(); //Commodities
				reader.skipValue();reader.skipValue(); // Refuel
				reader.skipValue();reader.skipValue(); //Repair
				reader.skipValue();reader.skipValue(); //Rearm
				reader.skipValue();reader.skipValue(); //outfitting
				reader.skipValue();reader.skipValue(); //shipyard
				reader.skipValue();reader.skipValue(); //import Comm
				reader.skipValue();reader.skipValue(); //export comm
				reader.skipValue();reader.skipValue(); //prohibited comm
				reader.skipValue();reader.skipValue(); //ecos
				reader.skipValue();
				reader.beginArray();
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
					reader.skipValue();reader.skipValue();
					reader.endObject();
				}
				reader.endArray();
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
					reader.skipValue();
					reader.skipValue();
					comm.category = reader.nextOptionalString();
				reader.endObject();
			reader.endObject();
			comm.print();
		}
	
		reader.endArray();
		reader.close();
		
	}
}
