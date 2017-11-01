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
import org.w3c.dom.Element;
import org.w3c.dom.Node;

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
    public float getExchangeRate(String currencyCode, int year, int month, int day) 
    		throws IOException, ParserConfigurationException, SAXException {
    	//initial value set to -1 for ease of error recognition
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
    	
    	//Prepare inputStream and document
    	InputStream inputStream=url.openStream();
    	
    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    	DocumentBuilder db = dbf.newDocumentBuilder();
    	Document doc = db.parse(new InputSource(inputStream));
    	doc.getDocumentElement().normalize();

    	//Get complete NodeList of all currencies
    	NodeList nodeList = doc.getElementsByTagName("fx");
    	
    	//prepare for individual node examinations
    	Node fxnode;
    	Element fxresult = null;
    	NodeList fxCurCode;
    	NodeList fxrate;
    	
    	//examine each node individually for currency code match
    	//if a match is found, return the appropriate currency conversion rate
    	for (int i = 0; i < nodeList.getLength(); i++)
    	{
    		fxnode = nodeList.item(i);
    		if(fxnode.getNodeType() == fxnode.ELEMENT_NODE){
    			fxresult = (Element) fxnode;
    		}
    		
    		fxCurCode = fxresult.getElementsByTagName("currency_code");
    		fxrate = fxresult.getElementsByTagName("rate");
    		
    		if(currencyCode.equals(fxCurCode.item(0).getTextContent()))
    		{
    			ret = new Float(fxrate.item(0).getTextContent());
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
    public float getExchangeRate(String fromCurrency, String toCurrency, int year, int month, int day) 
    		throws IOException, ParserConfigurationException, SAXException {
    	//initial values set to -1 for ease of error recognition
    	float ret = -1;
    	float rate1 = -1;
    	float rate2 = -1;
    	
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
    	
    	//Prepare inputStream and document
    	InputStream inputStream=url.openStream();
    	
    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    	DocumentBuilder db = dbf.newDocumentBuilder();
    	Document doc = db.parse(new InputSource(inputStream));
    	doc.getDocumentElement().normalize();

    	//Get complete NodeList of all currencies
    	NodeList nodeList = doc.getElementsByTagName("fx");
    	
    	//prepare for individual node examinations
    	Node fxnode;
    	Element fxresult = null;
    	NodeList fxCurCode;
    	NodeList fxrate;
    	
    	//examine each node individually for currency code match
    	//if a match is found, save the value to the appropriate rate variable
    	for (int i = 0; i < nodeList.getLength(); i++)
    	{
    		fxnode = nodeList.item(i);
    		if(fxnode.getNodeType() == fxnode.ELEMENT_NODE){
    			fxresult = (Element) fxnode;
    		}
    		
    		fxCurCode = fxresult.getElementsByTagName("currency_code");
    		fxrate = fxresult.getElementsByTagName("rate");
    		
    		if(fromCurrency.equals(fxCurCode.item(0).getTextContent()))
    		{
    			rate1 = new Float(fxrate.item(0).getTextContent());
    		}
    		if(toCurrency.equals(fxCurCode.item(0).getTextContent()))
    		{
    			rate2 = new Float(fxrate.item(0).getTextContent());
    		}
    	}
    	
    	//final calculation
    	ret = rate1/rate2;
    	return ret;
    }
}