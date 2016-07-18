/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.amazonrandomizer.util;

import java.util.Arrays;
import java.util.List;

/**
 * i imagine a better way of populating these lists would include pulling all
 * the words from a file. but i didn't want to mess with it for when it comes to
 * pushing the app to openshift.
 *
 * @author nick
 */
class GeneralUtils {

    /**
     * I left out a few that I think would be things that don't get shipped,
     * like mp3 downloads and movies
     *
     * @return
     */
    static List<String> getSearchIndexes() {
        return Arrays.asList(
                "Appliances",
                "ArtsAndCrafts",
                "Automotive",
                "Baby",
                "Beauty",
                "Music",
                "Wireless",
                "Fashion",
                "FashionBaby",
                "FashionBoys",
                "FashionGirls",
                "FashionMen",
                "FashionWomen",
                "Collectibles",
                "PCHardware",
                "Electronics",
                "GiftCards",
                "Grocery",
                "HealthPersonalCare",
                "HomeGarden",
                "Industrial",
                "Luggage",
                "Magazines",
                "MusicalInstruments",
                "OfficeProducts",
                "LawnAndGarden",
                "PetSupplies",
                "Pantry",
                "SportingGoods",
                "Tools",
                "Toys"
        );
    }

    /**
     * this list was generated from a website on the internet
     * http://listofrandomwords.com/index.cfm
     *
     * @return
     */
    static List<String> getKeywords() {
        return Arrays.asList(
                "corp",
                "sink",
                "caff",
                "sill",
                "inby",
                "dani",
                "gray",
                "chug",
                "coho",
                "reik",
                "pogy",
                "atma",
                "plan",
                "jinx",
                "gash",
                "unef",
                "corf",
                "oona",
                "dewy",
                "boil",
                "feme",
                "wrns",
                "bari",
                "doat",
                "jauk",
                "barr",
                "lang",
                "cond",
                "jebb",
                "lger",
                "phar",
                "lira",
                "code",
                "sang",
                "wast",
                "pent",
                "bane",
                "ball",
                "fima",
                "days",
                "hilt",
                "tobe",
                "mage",
                "prof",
                "olin",
                "nane",
                "buoy",
                "into",
                "quot",
                "braw",
                "chap",
                "rgen",
                "caul",
                "eula",
                "weyl",
                "save",
                "zino",
                "nejd",
                "lave",
                "abiu",
                "abel",
                "bile",
                "gish",
                "buoy",
                "data",
                "tutu",
                "punk",
                "biog",
                "seta",
                "elev",
                "vivo",
                "mark",
                "hunt",
                "leet",
                "freq",
                "meow",
                "govt",
                "sumo",
                "raab",
                "stow",
                "rtty",
                "lass",
                "cyma",
                "sunn",
                "baal",
                "tuff",
                "cavy",
                "cask",
                "cade",
                "lory",
                "feat",
                "opai",
                "crab",
                "back",
                "okla",
                "rind",
                "alep",
                "mogo",
                "coth",
                "fast"
        );
    }

    /**
     * so... we feel weird putting this list in here, but we pulled it from
     * google. it is the list they use to redirect searches with these words to
     * videos of kittens apparently. anyway, we hope it will help prevent us
     * from sending inappropriate things to people. we might need to add words
     * to this list if we end up finding items that get through this list.
     *
     * @return
     */
    static List<String> getBlacklist() {
        List<String> blacklist = Arrays.asList(
                " anal ",
                " anus ",
                " arse ",
                " ass ",
                " ballsack ",
                " balls ",
                " bastard ",
                " bitch ",
                " biatch ",
                " bloody ",
                " blowjob ",
                " blow job ",
                " bollock ",
                " bollok ",
                " boner ",
                " boob ",
                " bugger ",
                " bum ",
                " butt ",
                " buttplug ",
                " clitoris ",
                " cock ",
                " coon ",
                " crap ",
                " cunt ",
                " damn ",
                " dick ",
                " dildo ",
                " dyke ",
                " fag ",
                " feck ",
                " fellate ",
                " fellatio ",
                " felching ",
                " fuck ",
                " f u c k ",
                " fudgepacker ",
                " fudge packer ",
                " flange ",
                " Goddamn ",
                " God damn ",
                " hell ",
                " homo ",
                " jerk ",
                " jizz ",
                " knobend ",
                " knob end ",
                " labia ",
                " lingerie ",
                " lmao ",
                " lmfao ",
                " muff ",
                " nigger ",
                " nigga ",
                " omg ",
                " penis ",
                " piss ",
                " poop ",
                " prick ",
                " pube ",
                " pussy ",
                " porn ",
                " queer ",
                " scrotum ",
                " sex ",
                " shit ",
                " s hit ",
                " sh1t ",
                " slut ",
                " smegma ",
                " spunk ",
                " swear ",
                " tit ",
                " tosser ",
                " turd ",
                " twat ",
                " vagina ",
                " wank ",
                " whore ",
                " wtf "
        );

        return blacklist;
    }

}
