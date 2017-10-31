package xrate;

import java.net.URL;

import javax.sql.rowset.spi.XmlReader;
import javax.xml.stream.XMLInputFactory;

import org.omg.CORBA.portable.InputStream;
import org.w3c.dom.NodeList;

import com.sun.xml.internal.txw2.Document;

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
    public float getExchangeRate(String currencyCode, int year, int month, int day) {
    	URL url = new URL(URLbase+"/"+year+"/"+month+"/"+day+".xml");
    	InputStream xmlStream=url.openStream();
    	
    	XMLInputFactor factory = XMLInputFactory.newInstance();
    	XMLStreamReader reader = factory.createXMLStreamReader(xmlStream);
        
        Document doc = createDocument("yayy");
        
        String send;
        while((send = reader.read()) != null)
        {
        	doc.write(send);
        }
    	
    	NodeList nodeList = doc.getElementsByTagName("fx");
    	NodeList children;
    	
    	for (int i = 0; i < nodeList.getLength(); i++)
    	{
    		children = nodeList.item(i).getChildNodes(); 
    		if(currencyCode.equals(children.item(0).getNodeValue()))
    		{
    			return children.item(1).getNodeValue();
    		}
    	}
        
        throw new UnsupportedOperationException();
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