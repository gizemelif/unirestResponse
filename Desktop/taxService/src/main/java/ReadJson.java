import java.io.FileReader;
import java.io.StringReader;
import java.util.Iterator;
import java.util.Map;

import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.json.Json;
import javax.json.stream.JsonParser;

public class ReadJson
{
    public static void main(String[] args) throws Exception
    {
        JSONParser parse = new JSONParser();
//Type caste the parsed json data in json object
        JSONObject jobj = (JSONObject)parse.parse(inline);
//Store the JSON object in JSON array as objects (For level 1 array element i.e Results)
        JSONArray jsonarr_1 = (JSONArray) jobj.get("results");
//Get data for Results array
        for(int i=0;i<jsonarr_1.length();i++)
        {
            //Store the JSON objects in an array
            //Get the index of the JSON object and print the values as per the index
            JSONObject jsonobj_1 = (JSONObject)jsonarr_1.get(i);
            //Store the JSON object in JSON array as objects (For level 2 array element i.e Address Components)
            JSONArray jsonarr_2 = (JSONArray) jsonobj_1.get("address_components");
            System.out.println("Elements under results array");
            System.out.println("\nPlace id: " +jsonobj_1.get("place_id"));
            System.out.println("Types: "  +jsonobj_1.get("types"));
            //Get data for the Address Components array
            System.out.println("Elements under address_components array");
            System.out.println("The long names, short names and types are:");
            for(int j=0;j<jsonarr_2.length();j++)
            {
                //Same just store the JSON objects in an array
                //Get the index of the JSON objects and print the values as per the index
                JSONObject jsonobj_2 = (JSONObject) jsonarr_2.get(j);
                //Store the data as String objects
                String str_data1 = (String) jsonobj_2.get("long_name");
                System.out.println(str_data1);
                String str_data2 = (String) jsonobj_2.get("short_name");
                System.out.println(str_data2);
                System.out.println(jsonobj_2.get("types"));
                System.out.println("\n");
            }
        }
    }
}