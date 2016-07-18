/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.amazonrandomizer.util;

import java.util.Arrays;
import java.util.List;

/**
 * so... we feel weird putting this list in here, but we pulled it from google.
 * it is the list they use to redirect searches with these words to videos of 
 * kittens apparently. anyway, we hope it will help prevent us from sending 
 * inappropriate things to people.
 * @author nick
 */
class GeneralUtils {

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
