
import org.json.JSONObject;
import org.omg.CORBA.portable.InputStream;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class ExchangeRates {


    private String currencyTag;


    public static void main(String[] args) throws Exception{
        URL url = null;
        try {
            url = new URL("http://www.floatrates.com/daily/usd.json");
        }catch(Exception e){

        }

        URLConnection con = url.openConnection();

        InputStreamReader read = new InputStreamReader((con.getInputStream()));

        BufferedReader read2 = new BufferedReader(read);

        String rates = read2.readLine();

        while(read2.readLine() != null){

        }

        read2.close();

        if(rates != null && rates.length() >0 ){

            JSONObject s = null;
            try{
                //get the big file
                JSONObject temp = new JSONObject(rates);
                //get the object ffrom that big file
                s = temp.getJSONObject("eur");
            }catch(Exception e){
            }
            System.out.println(s.getFloat("rate"));

            System.out.println(rates);
        }




    }


}
