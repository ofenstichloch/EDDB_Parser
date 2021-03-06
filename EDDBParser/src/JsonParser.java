import java.io.IOException;
import java.io.Reader;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;


public class JsonParser extends JsonReader {

	public JsonParser(Reader in) {
		super(in);
	}

	public int nextOptionalInt() throws IOException{
		try {
			JsonToken peek = this.peek();
			if(peek==JsonToken.NULL){
				this.skipValue();
				return -1;
			}else if(peek == JsonToken.NUMBER){
				return this.nextInt();
			}else{
				java.lang.System.out.println("NULL/NUMBER "+this.nextString());
				return -2;
			}
		} catch (IOException e) {
		}
		throw new IOException();
	}
	
	public String nextOptionalString() throws IOException{
		try{
			JsonToken peek = this.peek();
			if(peek == JsonToken.STRING){
				return this.nextString();
			}else if(peek==JsonToken.NULL){
				this.skipValue();
				return "";
			}else{
				java.lang.System.out.println("NULL/STRING "+this.nextString());
				return "SCHEISSE";
			}
		} catch (IOException e) {
		}
		throw new IOException();
	}

	public long nextOptionalLong() throws IOException {
		try {
			JsonToken peek = this.peek();
			if(peek==JsonToken.NULL){
				this.skipValue();
				return -1;
			}else if(peek == JsonToken.NUMBER){
				return this.nextLong();
			}else{
				java.lang.System.out.println(this.nextString());
				return -2;
			}
		} catch (IOException e) {
		}
		throw new IOException();
	}
	
}
