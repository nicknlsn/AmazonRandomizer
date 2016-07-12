/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.amazonrandomizer.util;

import com.amazon.advertising.api.sample.SignedRequestsHelper;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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
    private static String requestUrl; // the url that returns the first page of search results
    private static int totalPages;
    private static List<String> items = new ArrayList<String>(); // all the items to choose from
    private static String maxPrice = "500"; // TODO replace this with the price set by the user, also factor in the percentage paypal takes by subracting it
    private static String minPrice = "400"; // TODO replace this with a price that a dollar or so below the maxPrice
    private static String searchIndex = "ArtsAndCrafts"; // TODO make this a funtion that returns a random search index
    private static String keywords = "words"; // TODO make this a function that returns random keywords

    /**
     * the main guts of this class
     *
     * this method will first call the api with some random parameters to get
     * the first page of results, it also gets the first 10 results. according
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
     * @return
     */
    public static String getRandomItem() { // TODO maybe pass the list of categories in through here so we can do another search if the item returned is no good
        String item = null;

        // get the first results and the number of pages of results
        getFirstPage();

        // if there is more than one page, get the rest of the results
        if (totalPages > 1) {
            getAllPages();
        }
        
        // test items list
        System.out.println("number of items: " + items.size());

        // TODO choose one item at random
        
        
        // TODO make sure this item is a good one to buy, new item, new category for user
        
        
        // finally, return the item
        return item;

//        List<String> items = getItems(requestUrl);
//        return item;
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
            Map<String, String> params = new HashMap<String, String>();
            params.put("Service", "AWSECommerceService");
            params.put("AWSAccessKeyId", AWS_ACCESS_KEY_ID);
            params.put("AssociateTag", AWS_ASSOCIATE_TAG);
            params.put("Operation", "ItemSearch");
            params.put("MaximumPrice", maxPrice);
            params.put("MinimumPrice", minPrice);
            params.put("SearchIndex", searchIndex);
            params.put("Keywords", keywords);
            params.put("ResponseGroup", "Small"); // TODO look into different response groups that might suit our needs better
            params.put("Timestamp", new SimpleDateFormat("YYYYMMDD").format(new Date()));

            // this is the url that returns items
            requestUrl = helper.sign(params);
            System.out.println("request url: " + requestUrl);

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(requestUrl);
            if (doc != null) {

                // get total pages first
                Node totalPagesNode = doc.getElementsByTagName("TotalPages").item(0); // first one should be the only one
                totalPages = Integer.parseInt(totalPagesNode.getTextContent());
                System.out.println("total pages: " + totalPages);

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
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(AmazonAPIUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AmazonAPIUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("Service", "AWSECommerceService");
                params.put("AWSAccessKeyId", AWS_ACCESS_KEY_ID);
                params.put("AssociateTag", AWS_ASSOCIATE_TAG);
                params.put("Operation", "ItemSearch");
                params.put("MaximumPrice", maxPrice);
                params.put("MinimumPrice", minPrice);
                params.put("SearchIndex", searchIndex);
                params.put("ItemPage", Integer.toString(i));
                params.put("Keywords", keywords);
                params.put("ResponseGroup", "Small"); // TODO look into different response groups that might suit our needs better
                params.put("Timestamp", new SimpleDateFormat("YYYYMMDD").format(new Date()));

                // this is the url that returns items
                requestUrl = helper.sign(params);
                System.out.println("request url: " + requestUrl);

                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
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

        } catch (IllegalArgumentException ex) {
            Logger.getLogger(AmazonAPIUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(AmazonAPIUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AmazonAPIUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(AmazonAPIUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(AmazonAPIUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AmazonAPIUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(AmazonAPIUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
