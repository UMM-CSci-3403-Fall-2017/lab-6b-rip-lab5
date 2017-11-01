package xrate;

import java.net.MalformedURLException;
import java.net.URL;

import javax.sql.rowset.spi.XmlReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import java.io.IOException;
//import org.omg.CORBA.portable.InputStream;
import java.io.InputStream;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;

//import com.sun.xml.internal.txw2.Document;

/**
 * Provide access to basic currency exchange rate services.
 * 
 * @author lab-6b-rip-lab5
 * Xaitheng Yang
 * Marshall Hoffmann
 */
public class ExchangeRateReader {

	String URLbase = null;
    /**
     * Construct an exchange rate reader using the given base URL. All requests
     * will then be relative to that URL. If, for example, your source is Xavier
     * Finance, the base URL is http://api.finance.xaviermedia.com/api/ Rates
     * for specific days will be constructed from that URL by appending the
     * year, month, and day; the URL for 25 June 2010, for example, would be
     * http://api.finance.xaviermedia.com/api/2010/06/25.xml
     * 
     * @param baseURL
     *            the base URL for requests
     */
    public ExchangeRateReader(String baseURL) {
        URLbase = baseURL;
    }

    /**
     * Get the exchange rate for the specified currency against the base
     * currency (the Euro) on the specified date.
     * 
     * @param currencyCode
     *            the currency code for the desired currency
     * @param year
     *            the year as a four digit integer
     * @param month
     *            the month as an integer (1=Jan, 12=Dec)
     * @param day
     *            the day of the month as an integer
     * @return the desired exchange rate
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public float getExchangeRate(String currencyCode, int year, int month, int day) throws IOException, ParserConfigurationException, SAXException {
    	float ret = -1;
    	
    	//formatting the URL properly
    	String Syear = Integer.toString(year);
    	String Smonth = Integer.toString(month);
    	String Sday = Integer.toString(day);
    	if(month < 10){
    		Smonth = "0" + Integer.toString(month);
    	}
    	
    	if(day < 10){
    		Sday = "0" + Integer.toString(day);
    	}	
    		
    	URL url = new URL(URLbase+Syear+"/"+Smonth+"/"+Sday+".xml");
    	
    	InputStream inputStream=url.openStream();
    	
    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    	DocumentBuilder db = dbf.newDocumentBuilder();
    	
    	Document doc;
    	
    	doc = db.parse(new InputSource(inputStream));
    	
    	doc.getDocumentElement().normalize();
    	
//    	NodeList nodeList = doc.getElementsByTagName("fx basecurrency=\"EUR\"");
    	NodeList nodeList = doc.getElementsByTagName("fx");
    	System.out.println(nodeList.item(0));
    	System.out.println(nodeList.item(1));
    	
    	NodeList children;
    	for (int i = 0; i < nodeList.getLength(); i++)
    	{
    		children = nodeList.item(i).getChildNodes();
    		System.out.println("---");
    		System.out.println(children.item(0).getNodeValue());
    		System.out.println(children.item(1).getNodeValue());
    		if(currencyCode.equals(children.item(0).getNodeValue()))
    		{
    			ret = new Float(children.item(1).getNodeValue());
    			return ret;
    		}
    	}
    	return ret;
    }

    /**
     * Get the exchange rate of the first specified currency against the second
     * on the specified date.
     * 
     * @param currencyCode
     *            the currency code for the desired currency
     * @param year
     *            the year as a four digit integer
     * @param month
     *            the month as an integer (1=Jan, 12=Dec)
     * @param day
     *            the day of the month as an integer
     * @return the desired exchange rate
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public float getExchangeRate(
            String fromCurrency, String toCurrency,
            int year, int month, int day) {
        
    	
        throw new UnsupportedOperationException();
    }
}