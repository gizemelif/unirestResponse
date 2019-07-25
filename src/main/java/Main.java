
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.JSONArray;
import org.json.JSONObject;

import vd.Data;
import vd.VD;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;
public class Main {
    public static void main(String[] args) throws IOException{

        String filePath = "C:\\Users\\geatalay\\Desktop\\Vergi_numarasi_kontrol\\ankara_tc_no.json";
        String fileString = new String(Files.readAllBytes(Paths.get(filePath)),StandardCharsets.UTF_8);


        JSONArray jsonArray = new JSONArray(fileString);
        String a="";
       // String iller = new String(Files.readAllBytes(Paths.get("C:\\Users\\geatalay\\Desktop\\vergi_liste")), StandardCharsets.UTF_8);

       // JSONObject citiesArr = new JSONObject(iller);

        ObjectMapper mapper = new ObjectMapper();

        nextNum:
        for(int i=0; i<jsonArray.length(); i++) {

            JSONObject object = jsonArray.getJSONObject(i);
            //String taxNumber = "";
            String gvgUid = "";
            String governmentNumber = "";
            String cityName = "";

            /*try {

                taxNumber = valueToStringOrEmpty(object, "TAX_NUMBER");

            } catch (Exception e) {
                e.printStackTrace();
            }*/

            try{
                gvgUid = valueToStringOrEmpty(object,"GVG_UID");
            }catch (Exception e){
                e.printStackTrace();
            }
            try{
                governmentNumber = valueToStringOrEmpty(object,"GOVERNMENT_NUMBER");
            }catch (Exception e){e.printStackTrace();}
            try {
                cityName = object.getString("NAME");
            } catch (Exception e) {
                e.printStackTrace();
            }

            //plaka bulma
           /* String plaka = "";
            for(int j=1; j<82; j++){
                if(citiesArr.getString(Integer.toString(j)).equalsIgnoreCase(cityName)){
                    plaka = "0"+Integer.toString(j);
                    break;
                }
            }*/
            String taxApt = new String(Files.readAllBytes(Paths.get("C:\\Users\\geatalay\\Desktop\\Vergi_numarasi_kontrol\\ankaraVD")), StandardCharsets.UTF_8);
            JSONObject vergiDaireleriKod = new JSONObject(taxApt);

            Iterator<?> keys = vergiDaireleriKod.keys();
            String key = "";

            while (keys.hasNext()) {
                key = (String) keys.next();

                AtomicReference<Boolean> isFound = new AtomicReference<>(false);

                HttpResponse jsonResponse = Unirest.post("https://ivd.gib.gov.tr/tvd_server/dispatch")
                        .body("cmd=vergiNoIslemleri_vergiNumarasiSorgulama&callid=1e3cfc764dfbb-11&pageName=R_INTVRG_INTVD_VERGINO_DOGRULAMA&token=d1078f5e3dc646b78d5d4e5842f21e97feb48d366bc7617458b6679dec12675154a01fccc42292bb04d926bc259dbc75e39dd8e202535fd70a7098396c74a6f7&jp=%7B%22dogrulama%22%3A%7B%22vkn1%22%3A%22%22%2C%22tckn1%22%3A%22"+governmentNumber+"%22%2C%22iller%22%3A%22006%22%2C%22vergidaireleri%22%3A%22"+ key +"%22%7D%2C%22dyntab2%22%3A%7B%22tckn%22%3A%22%22%2C%22vkn%22%3A%22%22%2C%22unvan%22%3A%22%22%2C%22vergidairesi%22%3A%22%22%2C%22faaliyetdurum%22%3A%22%22%7D%2C%22dyntab3%22%3A%7B%22tckn%22%3A%22%22%2C%22vkn%22%3A%22%22%2C%22unvan%22%3A%22%22%2C%22vergidairesi%22%3A%22%22%2C%22faaliyetdurum%22%3A%22%22%7D%2C%22dyntab4%22%3A%7B%22tckn%22%3A%22%22%2C%22vkn%22%3A%22%22%2C%22unvan%22%3A%22%22%2C%22vergidairesi%22%3A%22%22%2C%22faaliyetdurum%22%3A%22%22%7D%2C%22dyntab5%22%3A%7B%22tckn%22%3A%22%22%2C%22vkn%22%3A%22%22%2C%22unvan%22%3A%22%22%2C%22vergidairesi%22%3A%22%22%2C%22faaliyetdurum%22%3A%22%22%7D%7D")
                        .header("accept", "application/json")
                        .header("accept-language", "en-US,en;q=0.9")
                        .header("content-type", "application/x-www-form-urlencoded; charset=UTF-8")
                        .socketTimeout(120000)
                        .asJson();


                String responseString = jsonResponse.getBody().toString();

                try {
                    VD vd = mapper.readValue(responseString, new TypeReference<VD>() {
                    });

                    if(vd == null || vd.getData() == null || vd.toString().startsWith("null$")){

                        if(!isFound.get()){
                            vd = new VD();
                            Data data = new Data();
                            data.setDurum("N/A");
                            data.setDurum_text("N/A");
                            //data.setVkn(taxNumber);
                            data.setTckn(governmentNumber);
                            data.setVdkodu(cityName);
                            vd.setData(data);

                            String jsonBody = jsonResponse.getBody().toString();
                            System.out.println(jsonBody);

                            String statusText = (String.valueOf(jsonResponse.getStatus()));

                            System.out.println(statusText);

                            writeFile("TCKN SORGU"+"$"+gvgUid+"$"+vd.toString()+"$"+cityName+"$"+jsonBody+"$"+statusText);

                        }
                        continue;

                    }
                    isFound.set(true);
                    writeFile("TCKN_SORGU"+"$"+gvgUid+"$"+vd.toString());

                    continue nextNum;

                } catch (Exception e) {
                    System.out.println("PATLADI....");
                    e.printStackTrace();
                }


                }

            }

    }

    public static String valueToStringOrEmpty(JSONObject map, String key) {
        Object value = map.get(key);
        return value == null ? "" : value.toString();
    }

    public static void writeFile(String str){
        File fileName = new File("C:\\Users\\geatalay\\Desktop\\Vergi_numarasi_kontrol\\ankaraTcknSonuc.txt");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName,true));
            writer.append(str);
            writer.newLine();
            writer.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
