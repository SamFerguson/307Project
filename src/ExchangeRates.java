
import org.json.JSONObject;
import org.omg.CORBA.portable.InputStream;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class ExchangeRates {


    private String currencyTag;
    private static String[] currencies = {"usd", "jpy", "chf", "eur", "gbp", "aoa", "yer", "pyg"};

    public static void main(String[] args) throws Exception{

        for(String currency: currencies) {
            System.out.println("this is the currency that is currently being compared    " + currency);
            //make temporary currency array without the one you're currently on
            String[] otherCurrencies = new String[currencies.length-1];
            int index = 0;
            for(int i = 0; i<currencies.length;i++){
                //if the current currency in array isn't the one currently processed
                //add it to the otherCurrencies array and incrememnt index
                if(!currencies[i].equals(currency)){
                    otherCurrencies[index] = currencies[i];
                    index++;
                }
            }
            URL url = null;
            try {
                url = new URL("http://www.floatrates.com/daily/"+currency+".json");
            } catch (Exception e) {
            }
            //open the connection
            URLConnection con = url.openConnection();
            //get input stream
            InputStreamReader read = new InputStreamReader((con.getInputStream()));
            //get a result from the stream
            BufferedReader read2 = new BufferedReader(read);

            //the first line of the stream ought to be the json from the webpage
            String rates = read2.readLine();

            while (read2.readLine() != null) {
            }
            read2.close();

            //for each currency that you're using that isn't the one you're comparing to
            for(String otherCurrency:otherCurrencies){
                //if the json isn't empty
                if (rates != null && rates.length() > 0) {
                    //make the json object for the whole plaintext
                    JSONObject tempJSON = new JSONObject(rates);
                    //get the object from that file with that name
                    //make this string variable against the other ones
                    JSONObject s = tempJSON.getJSONObject(otherCurrency);
                    System.out.print(s.getString("code")+":  ");
                    System.out.println(s.getFloat("rate"));
                }
            }
        }

    }


}
