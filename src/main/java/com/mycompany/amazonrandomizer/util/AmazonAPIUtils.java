/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.amazonrandomizer.util;

import com.amazon.advertising.api.sample.SignedRequestsHelper;
import com.mycompany.amazonrandomizer.Pages.TestApiCalls;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * This class holds methods to get random items from amazon.
 *
 * Got some help with signing requests from here:
 * https://aws.amazon.com/code/Product-Advertising-API/2478
 *
 * @author nick
 */
public class AmazonAPIUtils {

    private static final String AWS_ACCESS_KEY_ID = Constants.awsAccessKeyId;
    private static final String AWS_SECRET_KEY = Constants.awsSecretKey;
    private static final String AWS_ASSOCIATE_TAG = Constants.awsAssociateTag;
    private static final String AWS_ENDPOINT = Constants.awsEndpoint;

    // the url that returns the first page of search results
    private static String requestUrl;

    private static int totalPages;

    // all the items to choose from
    private static final List<String> items = new ArrayList<>();

    // TODO replace this with the price set by the user, also factor in the 
    // percentage paypal takes by subracting it
    private static String maxPrice = null;
    // TODO replace this with a price that a dollar or so below the maxPrice
    private static String minPrice = null;

    // TODO make this a function that returns random keywords
    private static List<String> keywords = GeneralUtils.getKeywords();
    private static String keyword;
    private static final List<String> searchIndexList = GeneralUtils.getSearchIndexes();
    private static String searchIndex;
    
    public static void buy(String theMaxPrice) {
        // first get random item
        // then place order through zinc
//        ZincUtils.placeOrder(getRandomItem(theMaxPrice), theMaxPrice); // do it like this

        // test like this
        ZincUtils.placeOrder("B01587FRPM", "971");
        
    }

    /**
     * the main guts of this class
     *
     * this method will first call the method that searches for items. according
     * to the documentation, pages hold 10 results, and you can only get the
     * first 10 pages of results, even if there are more. so we can get a
     * maximum of 100 random items to choose from. if none of those work we will
     * just have to do another search with new random parameters.
     *
     * the parameters that are random are the search index and keywords. it
     * would be nice to not have to use keywords, but the api seems to not work
     * without it, even though the docs say it is not required. the search index
     * is just the category. this works nice for us because we can use it to
     * make sure we don't buy an mp3 or something else that we can't really ship
     * to the user. we can also try to use it to prevent us from shipping things
     * that the user probably wouldn't appreciate, like porn...
     *
     * the Thread.sleep(1000) are all set to one second because the amazon API
     * is picky about how many calls you make per second, and if you go over, it
     * will return a 503 error:
     * https://affiliate-program.amazon.com/gp/advertising/api/detail/faq.html
     * @param theMaxPrice
     * @return
     */
    public static String getRandomItem(String maxPrice) {
        String item = null;
        String itemDetails = null;
        List<String> items = new ArrayList<>();
        Boolean goodItem = false;
        Double shipPrice;
        String productPrice =  null;

        try {
            // 1. continue to loop until a good item is found
            while (!goodItem) {
                // 2. get a bunch of random items
                System.out.println("\n\n\tgetting random items");
                // this will return at most 100 items
                items = getRandomItems(maxPrice);

                // 3. loop through these 
                System.out.println("found " + items.size() + " items");
                for (int i = 0; i < items.size(); i++) {
                    // now do all that stuff
                    item = items.get(i);
                    System.out.println("\n\n\titem: " + item);

                    // 4. make sure this item has not been purchased before
                    System.out.println("checking database for duplicate item");
                    Boolean duplicateItem = false;
                    ResultSet rs = JDBCUtils.checkForItem(item);
                    while (rs != null && rs.next()) {
                        if (rs.getString("productId").equals(item)) {
                            duplicateItem = true;
                            System.out.println("duplicate item found: " + item);
                            break;
                        }
                    }
                    if (duplicateItem) {
                        continue; // try next item in items list
                    }

                    // 5. make sure this item is appropriate to send
                    itemDetails = AmazonAPIUtils.getItemDetails(item);
                    if (itemDetails != null) {
                        System.out.println("checking for bad words");
                        Boolean badWordFound = false;
                        for (String word : Constants.blacklist) {
                            if (itemDetails.contains(word)) {
                                System.out.println("found a bad word: " + word);
                                badWordFound = true;
                                break;
                            }
                        }

                        if (badWordFound) {
                            continue; // move on to next item
                        }
                    }

                    // 6. make sure this item has free shipping not not prime
                    System.out.println("checking prices");
                    JSONObject productPrices = ZincUtils.getProductPrices(item);
                    if (productPrices != null && productPrices.has("offers")) {
                        JSONArray offers = productPrices.getJSONArray("offers");

                        // not sure if it should do this. but when there is more
                        // than one seller we could run into issues with getting
                        // free shipping info for an item, and then trying to 
                        // buy it from someone who does not offer free shipping.
                        if (offers.length() == 1) {

                            // we just check the first one in the array. not sure if
                            // this is the best thing to do here but it seems that 
                            // all the json returned from zinc puts the one with 
                            // free shipping first
                            Boolean prime = offers.getJSONObject(0).getBoolean("prime");

                            if (prime) {
                                System.out.println("item requires prime");
                                continue;
                            }

                            // if prime is false then this should never be null
                            // if it is it will throw an exception, but it does
                            // not stop the program from running and eventually a 
                            // good item will be found
                            shipPrice = offers.getJSONObject(0).getDouble("ship_price");
                            if (shipPrice > 0) {
                                System.out.println("item has shipping charge of " + Double.toString(shipPrice));
                                continue;
                            }

                            // 7. make sure the price does not exceed the maxPrice
                            System.out.println("checking price");
                            productPrice = offers.getJSONObject(0).getString("price").replace(".", "");
                            if (Double.parseDouble(productPrice) > Double.parseDouble(maxPrice)) {
                                System.out.println("item exceeds max price");
                                continue;
                            }
                        } else {
                            // lets try to stick with items that have only one
                            // seller
                            System.out.println("more than one seller");
                            continue;
                        }
                    }

                    // if we get here then we have a good item
                    goodItem = true;
                    break;
                }
            }

            // the while loop is over, so a good item has been found
            System.out.println("good item found: " + item);
            System.out.println("search index: " + searchIndex);
            System.out.println("keyword: " + keyword);
            System.out.println("price: " + productPrice);
            

        } catch (SQLException ex) {
            Logger.getLogger(TestApiCalls.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(TestApiCalls.class.getName()).log(Level.SEVERE, null, ex);
        }

        return item;
    }

    /**
     *
     * @return
     */
    public static List<String> getRandomItems(String theMaxPrice) {
        String item = null;
        items.clear();

        // set random search parameters: searchIndex and keyword
        Random rand = new Random();
        searchIndex = searchIndexList.get(rand.nextInt(searchIndexList.size())); // set range with list size so things can be added or removed
        keyword = keywords.get(rand.nextInt(keywords.size())); // TODO how to get random keywords?
        System.out.println("search index: " + searchIndex);
        System.out.println("keyword: " + keyword);

        // set price variables, we need to account for paypal fees
        double price = Integer.parseInt(theMaxPrice);
        // this formula may or may not be correct
        maxPrice = Double.toString(price - (price * 0.029) + 0.3);
        // in order for the maxPrice parameter to work with amazon it needs to 
        // be in pennies and be a whole number
        int lastIndexOfDec = maxPrice.lastIndexOf(".");
        if (lastIndexOfDec != -1) {
            maxPrice = maxPrice.substring(0, lastIndexOfDec);
        }
        System.out.println("max price: " + maxPrice);
        // take off four dollars to give the api a good range to search within
        minPrice = Integer.toString(Integer.parseInt(maxPrice) - 400);
        System.out.println("min price: " + minPrice);

        // get the first results and the number of pages of results
        getFirstPage();

        // if there is more than one page, get the rest of the results
        if (totalPages > 1) {
            getAllPages();
        }

        // finally, return the list of items
        return items;
    }

    private static List<String> getItems(String requestUrl) {
        List<String> items = new ArrayList<String>();

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(requestUrl);
            if (doc != null) {
                NodeList asinNodes = doc.getElementsByTagName("ASIN");
                if (asinNodes != null && asinNodes.getLength() > 0) {
                    for (int i = 0; i < asinNodes.getLength(); i++) {
                        if (asinNodes.item(i).getNodeType() == Node.ELEMENT_NODE && asinNodes.item(i).getNodeName().contains("ASIN")) {
                            items.add(asinNodes.item(i).getTextContent());
                        }
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException | DOMException e) {
            throw new RuntimeException(e);
        }

//        for (String item : items) {
//            System.out.println("item: " + item);
//        }
        return items;
    }

    /**
     * Gets the number of pages in the search result.
     *
     * @param requestUrl
     * @return
     */
    private static int getTotalPages(String requestUrl) {
        int pages = 0;

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(requestUrl);
            if (doc != null) {
                Node totalPagesNode = doc.getElementsByTagName("TotalPages").item(0); // first one should be the one we want
                pages = Integer.parseInt(totalPagesNode.getTextContent());
            }
        } catch (ParserConfigurationException | SAXException | IOException | DOMException e) {
            throw new RuntimeException(e);
        }

        return pages;
    }

    /**
     * gets the first results, sets the totalPages variable
     */
    private static void getFirstPage() {
        try {
            SignedRequestsHelper helper;
            helper = SignedRequestsHelper.getInstance(AWS_ENDPOINT, AWS_ACCESS_KEY_ID, AWS_SECRET_KEY);

            // this is for the initial search
            Map<String, String> params = new HashMap<>();
            params.put("Service", "AWSECommerceService");
            params.put("AWSAccessKeyId", AWS_ACCESS_KEY_ID);
            params.put("AssociateTag", AWS_ASSOCIATE_TAG);
            params.put("Operation", "ItemSearch");
            params.put("MaximumPrice", maxPrice);
            params.put("MinimumPrice", minPrice);
            params.put("SearchIndex", searchIndex);
            params.put("Keywords", keyword);
            params.put("ResponseGroup", "ItemIds"); // TODO look into different response groups that might suit our needs better
            params.put("Timestamp", new SimpleDateFormat("YYYYMMDD").format(new Date()));

            // this is the url that returns items
            requestUrl = helper.sign(params);
//            System.out.println("request url: " + requestUrl);

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Thread.sleep(1000);
            Document doc = db.parse(requestUrl);
            if (doc != null) {

                // get total pages first
                Node totalPagesNode = doc.getElementsByTagName("TotalPages").item(0); // first one should be the only one
                totalPages = Integer.parseInt(totalPagesNode.getTextContent());
//                System.out.println("total pages: " + totalPages);

                // get items 
                NodeList asinNodes = doc.getElementsByTagName("ASIN");
                if (asinNodes != null && asinNodes.getLength() > 0) {
                    for (int i = 0; i < asinNodes.getLength(); i++) {
                        if (asinNodes.item(i).getNodeType() == Node.ELEMENT_NODE
                                && asinNodes.item(i).getNodeName().contains("ASIN")
                                && !items.contains(asinNodes.item(i).getTextContent())) {
                            items.add(asinNodes.item(i).getTextContent());

                        }
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException | DOMException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException | NoSuchAlgorithmException | InvalidKeyException ex) {
            Logger.getLogger(AmazonAPIUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(AmazonAPIUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void getAllPages() {
        try {
            SignedRequestsHelper helper;
            helper = SignedRequestsHelper.getInstance(AWS_ENDPOINT, AWS_ACCESS_KEY_ID, AWS_SECRET_KEY);

            // we already have page one, start at page two
            // the api will only return up to 10 pages
            for (int i = 2; i <= totalPages && i <= 10; i++) {
                // this is for searching the rest of the pages
                Map<String, String> params = new HashMap<>();
                params.put("Service", "AWSECommerceService");
                params.put("AWSAccessKeyId", AWS_ACCESS_KEY_ID);
                params.put("AssociateTag", AWS_ASSOCIATE_TAG);
                params.put("Operation", "ItemSearch");
                params.put("MaximumPrice", maxPrice);
                params.put("MinimumPrice", minPrice);
                params.put("SearchIndex", searchIndex);
                params.put("ItemPage", Integer.toString(i));
                params.put("Keywords", keyword);
                params.put("ResponseGroup", "ItemIds"); // TODO look into different response groups that might suit our needs better
                params.put("Timestamp", new SimpleDateFormat("YYYYMMDD").format(new Date()));

                // this is the url that returns items
                requestUrl = helper.sign(params);
//                System.out.println("request url: " + requestUrl);

                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Thread.sleep(1000);
                Document doc = db.parse(requestUrl);
                if (doc != null) {

                    // get items 
                    NodeList asinNodes = doc.getElementsByTagName("ASIN");
                    if (asinNodes != null && asinNodes.getLength() > 0) {
                        for (int j = 0; j < asinNodes.getLength(); j++) {
                            if (asinNodes.item(j).getNodeType() == Node.ELEMENT_NODE
                                    && asinNodes.item(j).getNodeName().contains("ASIN")
                                    && !items.contains(asinNodes.item(j).getTextContent())) {
                                items.add(asinNodes.item(j).getTextContent());
                            }
                        }
                    }
                }
            }

        } catch (IllegalArgumentException | NoSuchAlgorithmException | InvalidKeyException | SAXException | ParserConfigurationException | InterruptedException ex) {
            Logger.getLogger(AmazonAPIUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(AmazonAPIUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AmazonAPIUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * this function returns a string of all the xml that is returned from the
     * item lookup api. it basically holds a lot of words that describe the
     * item, which will be used to test against the blacklist to make sure the
     * item is ok to send.
     *
     * @param item
     * @return
     */
    public static String getItemDetails(String item) {
        String details = null;

        try {
            SignedRequestsHelper helper;
            helper = SignedRequestsHelper.getInstance(AWS_ENDPOINT, AWS_ACCESS_KEY_ID, AWS_SECRET_KEY);

            // make a call to the ItemLookup Amazon API
            Map<String, String> params = new HashMap<>();
            params.put("Service", "AWSECommerceService");
            params.put("AWSAccessKeyId", AWS_ACCESS_KEY_ID);
            params.put("AssociateTag", AWS_ASSOCIATE_TAG);
            params.put("Operation", "ItemLookup");
            params.put("ItemId", item);
            params.put("ResponseGroup", "Large"); // get as much info back so we can test for bad words
            params.put("Timestamp", new SimpleDateFormat("YYYYMMDD").format(new Date()));

            // this is the url that retruns all the items details
            requestUrl = helper.sign(params);
//            System.out.println("item details request url: " + requestUrl);

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Thread.sleep(1000);
            Document doc = db.parse(requestUrl);

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            details = writer.getBuffer().toString().replaceAll("\n|\r", "");

        } catch (IllegalArgumentException | UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException ex) {
            Logger.getLogger(AmazonAPIUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(AmazonAPIUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(AmazonAPIUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(AmazonAPIUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return details;
    }
}
