import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import vd.Data;
import vd.VD;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Main {
    public static void main(String[] args) throws IOException {
        ReadExcel readExcel = new ReadExcel();
        List<String> vergiNo = readExcel.readExcel();
        vergiNo.listIterator().next();


        try{
            String il = "034";

            //String daireNo = "038104";
            //istanbul vergi dairesi index => 203-298
            //bursa 105-260

            List<Integer> vdList = new ArrayList<>();

            for(int i=203; i<299; i++){
                vdList.add(i);
            }

            ObjectMapper mapper = new ObjectMapper();
            for(String vkn : vergiNo){
                System.out.println("||Vergi Kimlik||"+vkn+"\t");
                AtomicReference<Boolean> isFound = new AtomicReference<>(false);
                vdList.parallelStream().forEach((Integer i) -> {
                    System.out.println("HERE=>"+i);
                    HttpResponse jsonResponse = Unirest.post("https://ivd.gib.gov.tr/tvd_server/dispatch")
                            .body("cmd=vergiNoIslemleri_vergiNumarasiSorgulama&callid=1e3cfc764dfbb-11&pageName=R_INTVRG_INTVD_VERGINO_DOGRULAMA&token=d1078f5e3dc646b78d5d4e5842f21e97feb48d366bc7617458b6679dec12675154a01fccc42292bb04d926bc259dbc75e39dd8e202535fd70a7098396c74a6f7&jp=%7B%22dogrulama%22%3A%7B%22vkn1%22%3A%22"+vkn+"%22%2C%22tckn1%22%3A%22%22%2C%22iller%22%3A%22"+il+"%22%2C%22vergidaireleri%22%3A%22"+il+i+"%22%7D%2C%22dyntab2%22%3A%7B%22tckn%22%3A%22%22%2C%22vkn%22%3A%22%22%2C%22unvan%22%3A%22%22%2C%22vergidairesi%22%3A%22%22%2C%22faaliyetdurum%22%3A%22%22%7D%2C%22dyntab3%22%3A%7B%22tckn%22%3A%22%22%2C%22vkn%22%3A%22%22%2C%22unvan%22%3A%22%22%2C%22vergidairesi%22%3A%22%22%2C%22faaliyetdurum%22%3A%22%22%7D%2C%22dyntab4%22%3A%7B%22tckn%22%3A%22%22%2C%22vkn%22%3A%22%22%2C%22unvan%22%3A%22%22%2C%22vergidairesi%22%3A%22%22%2C%22faaliyetdurum%22%3A%22%22%7D%2C%22dyntab5%22%3A%7B%22tckn%22%3A%22%22%2C%22vkn%22%3A%22%22%2C%22unvan%22%3A%22%22%2C%22vergidairesi%22%3A%22%22%2C%22faaliyetdurum%22%3A%22%22%7D%7D")
                            .header("accept", "application/json")
                            .header("accept-language", "en-US,en;q=0.9")
                            .header("content-type", "application/x-www-form-urlencoded; charset=UTF-8")
                            .asJson();


                    String currentString =  jsonResponse.getBody().toString();
                    try {
                        VD vd = mapper.readValue(currentString, new TypeReference<VD>() {});
                        if(vd == null || vd.getData() == null || vd.toString().startsWith("null$")) return;
                        isFound.set(true);
                        System.out.println(jsonResponse.getBody());

                        writeFile(vd.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                if(!isFound.get()){
                    VD vd = new VD();
                    Data data = new Data();
                    data.setDurum("N/A");
                    data.setDurum_text("N/A");
                    data.setVkn(vkn);
                    vd.setData(data);
                    System.out.println(vd.toString());
                    writeFile(vd.toString());
                }
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public static void writeFile(String str) throws IOException {
        File fileName = new File("C:\\Users\\geatalay\\Desktop\\Vergi_numarasi_kontrol\\f.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName,true));
        writer.append(str);
        writer.append(System.getProperty("line.separator"));
        writer.close();
    }

}
