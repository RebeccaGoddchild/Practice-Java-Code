package com.techelevator.auctions.services;

import org.springframework.web.client.RestTemplate;

import com.techelevator.auctions.model.Auction;
import org.springframework.web.util.UriComponentsBuilder;

public class AuctionService {

    public static String API_BASE_URL = "http://localhost:3000/auctions/";
    private RestTemplate restTemplate = new RestTemplate();


    public Auction[] getAllAuctions() {
        // call api here
        // Create a RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Make the API request and deserialize the response into an array of Auction objects
        Auction[] auctions = restTemplate.getForObject(API_BASE_URL, Auction[].class);

        return auctions;
    }

    public Auction getAuction(int id) {
        // call api here
        // Create a RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Build the URL for the specific auction by appending the ID
        String auctionUrl = API_BASE_URL + "/" + id;

        // Make the API request and deserialize the response into an Auction object
        Auction auction = restTemplate.getForObject(auctionUrl, Auction.class);

        return auction;
    }

    public Auction[] getAuctionsMatchingTitle(String title) {
        // call api here
        // Create a RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Build the URL for the auctions with the specified title using UriComponentsBuilder
        String auctionUrl = UriComponentsBuilder.fromHttpUrl(API_BASE_URL)
                .queryParam("title", title)
                .toUriString();

        // Make the API request and deserialize the response into an array of Auction objects
        Auction[] auctions = restTemplate.getForObject(auctionUrl, Auction[].class);

        return auctions;
    }

    public Auction[] getAuctionsAtOrBelowPrice(double price) {
        // call api here
        // Create a RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Build the URL for the auctions with prices at or below the specified value using UriComponentsBuilder
        String auctionUrl = UriComponentsBuilder.fromHttpUrl(API_BASE_URL)
                .queryParam("maxPrice", price)
                .toUriString();

        // Make the API request and deserialize the response into an array of Auction objects
        Auction[] auctions = restTemplate.getForObject(auctionUrl, Auction[].class);

        return auctions;
    }

}
