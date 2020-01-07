package com.hnq.toolkit.consts;

import com.google.common.collect.Maps;
import com.hnq.toolkit.util.RegexUtils;
import com.hnq.toolkit.util.StrUtils;
import com.hnq.toolkit.util.http.HttpService;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @author henengqiang
 * @date 2019/04/02
 */
public final class BannerConsts {

    private BannerConsts() {}

    /**
     * 控制台输出字符串颜色前后缀
     */
    public static final String WHITE_PREFIX = "\033[30;4m";
    public static final String RED_PREFIX = "\033[31;4m";
    public static final String YELLOW_GREEN_PREFIX = "\033[32;4m";
    public static final String YELLOW_PREFIX = "\033[33;4m";
    public static final String BLUE_PREFIX = "\033[34;4m";
    public static final String PINK_PREFIX = "\033[35;4m";
    public static final String CADET_BLUE_PREFIX = "\033[36;4m";
    public static final String GRAY_PREFIX = "\033[37;4m";

    public static final String COLOR_SUFFIX = "\033[0m";

    public static final String NUMBER_0 =
            "  #####   \n" +
            " ##   ##  \n" +
            "##     ## \n" +
            "##     ## \n" +
            "##     ## \n" +
            " ##   ##  \n" +
            "  #####   \n";

    public static final String NUMBER_1 =
            "    ##    \n" +
            "  ####    \n" +
            "    ##    \n" +
            "    ##    \n" +
            "    ##    \n" +
            "    ##    \n" +
            "  ######  \n";

    public static final String NUMBER_2 =
            " #######  \n" +
            "##     ## \n" +
            "       ## \n" +
            " #######  \n" +
            "##        \n" +
            "##        \n" +
            "######### \n";

    public static final String NUMBER_3 =
            " #######  \n" +
            "##     ## \n" +
            "       ## \n" +
            " #######  \n" +
            "       ## \n" +
            "##     ## \n" +
            " #######  \n";

    public static final String NUMBER_4 =
            "##        \n" +
            "##    ##  \n" +
            "##    ##  \n" +
            "##    ##  \n" +
            "######### \n" +
            "      ##  \n" +
            "      ##  \n";

    public static final String NUMBER_5 =
            " ######## \n" +
            " ##       \n" +
            " ##       \n" +
            " #######  \n" +
            "       ## \n" +
            " ##    ## \n" +
            "  ######  \n";

    public static final String NUMBER_6 =
            " #######  \n" +
            "##     ## \n" +
            "##        \n" +
            "########  \n" +
            "##     ## \n" +
            "##     ## \n" +
            " #######  \n";

    public static final String NUMBER_7 =
            " ######## \n" +
            " ##    ## \n" +
            "     ##   \n" +
            "    ##    \n" +
            "   ##     \n" +
            "   ##     \n" +
            "   ##     \n";

    public static final String NUMBER_8 =
            " #######  \n" +
            "##     ## \n" +
            "##     ## \n" +
            " #######  \n" +
            "##     ## \n" +
            "##     ## \n" +
            " #######  \n";

    public static final String NUMBER_9 =
            " #######  \n" +
            "##     ## \n" +
            "##     ## \n" +
            " ######## \n" +
            "       ## \n" +
            "##     ## \n" +
            " #######  \n";

    public static final String[] NUMBERS = {NUMBER_0, NUMBER_1, NUMBER_2, NUMBER_3, NUMBER_4, NUMBER_5, NUMBER_6, NUMBER_7, NUMBER_8, NUMBER_9};

    /**
     * 把numStr转化为bannerStr
     * 暂时最高支持四位数
     *
     * @param num 目标值Str
     * @return    bannerStr
     */
    public static String stitchingNumbers(int num) {
        String x = String.valueOf(num);
        if (StringUtils.isBlank(x)) {
            return "";
        }
        int len = x.trim().length();
        switch (len) {
            case 1: return stitchingNumbersLen1(num);
            case 2: return stitchingNumbersLen2(num);
            case 3: return stitchingNumbersLen3(num);
            case 4: return stitchingNumbersLen4(num);
            default: return "";
        }
    }

    // ------------------------------------ ------------------------------------ //

    private static String stitchingNumbersLen1(int num) {
        return NUMBERS[num];
    }

    private static String stitchingNumbersLen2(int num) {
        String digitStr = NUMBERS[num % 10];
        String tenStr = NUMBERS[num / 10];
        int times = StrUtils.calCharTimes1(digitStr, "\n");
        int width = digitStr.substring(0, digitStr.indexOf("\n")).length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times ; i++) {
            int startIndex = i * width + i;
            int endIndex = digitStr.indexOf("\n", startIndex);
            String headStr = tenStr.substring(startIndex, endIndex);
            String tailStr = digitStr.substring(startIndex, endIndex);
            sb.append(headStr).append(tailStr).append("\n");
        }
        return sb.toString();
    }

    private static String stitchingNumbersLen3(int num) {
        String digitStr = NUMBERS[num % 10];
        String tenStr = NUMBERS[num % 100 / 10];
        String hundreds = NUMBERS[num / 100];
        int times = StrUtils.calCharTimes1(digitStr, "\n");
        int width = digitStr.substring(0, digitStr.indexOf("\n")).length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times; i++) {
            int startIndex = i * width + i;
            int endIndex = digitStr.indexOf("\n", startIndex);
            String headStr = hundreds.substring(startIndex, endIndex);
            String midStr = tenStr.substring(startIndex, endIndex);
            String tailStr = digitStr.substring(startIndex, endIndex);
            sb.append(headStr).append(midStr).append(tailStr).append("\n");
        }
        return sb.toString();
    }

    private static String stitchingNumbersLen4(int num) {
        String digitStr = NUMBERS[num % 10];
        String tenStr = NUMBERS[num % 100 / 10];
        String hundreds = NUMBERS[num % 1000 / 100];
        String tenHundred = NUMBERS[num / 1000];
        int times = StrUtils.calCharTimes1(digitStr, "\n");
        int width = digitStr.substring(0, digitStr.indexOf("\n")).length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times; i++) {
            int startIndex = i * width + i;
            int endIndex = digitStr.indexOf("\n", startIndex);
            String headStr = tenHundred.substring(startIndex, endIndex);
            String midStr1 = hundreds.substring(startIndex, endIndex);
            String midStr2 = tenStr.substring(startIndex, endIndex);
            String tailStr = digitStr.substring(startIndex, endIndex);
            sb.append(headStr).append(midStr1).append(midStr2).append(tailStr).append("\n");
        }
        return sb.toString();
    }

    // ------------------------------------ ------------------------------------ //

    // ---- 字体名称 ---- //

    /**
     *   :::::::::::       ::::::::::       ::::::::   :::::::::::
     *      :+:           :+:             :+:    :+:      :+:
     *     +:+           +:+             +:+             +:+
     *    +#+           +#++:++#        +#++:++#++      +#+
     *   +#+           +#+                    +#+      +#+
     *  #+#           #+#             #+#    #+#      #+#
     * ###           ##########       ########       ###
     */
    public static final String FONT_ALLIGATOR = "alligator";

    /**
     * ::::::::::: ::::::::::  ::::::::  :::::::::::
     *     :+:     :+:        :+:    :+:     :+:
     *     +:+     +:+        +:+            +:+
     *     +#+     +#++:++#   +#++:++#++     +#+
     *     +#+     +#+               +#+     +#+
     *     #+#     #+#        #+#    #+#     #+#
     *     ###     ##########  ########      ###
     */
    public static final String FONT_ALLIGATOR2 = "alligator2";

    /**
     *
     * ##### ######  ####  #####
     *   #   #      #        #
     *   #   #####   ####    #
     *   #   #           #   #
     *   #   #      #    #   #
     *   #   ######  ####    #
     *
     */
    public static final String FONT_BANNER = "banner";

    /**
     * '########:'########::'######::'########:
     * ... ##..:: ##.....::'##... ##:... ##..::
     * ::: ##:::: ##::::::: ##:::..::::: ##::::
     * ::: ##:::: ######:::. ######::::: ##::::
     * ::: ##:::: ##...:::::..... ##:::: ##::::
     * ::: ##:::: ##:::::::'##::: ##:::: ##::::
     * ::: ##:::: ########:. ######::::: ##::::
     * :::..:::::........:::......::::::..:::::
     */
    public static final String FONT_BANNER3_D = "banner3-D";

    /**
     * ######## ########  ######  ########
     *    ##    ##       ##    ##    ##
     *    ##    ##       ##          ##
     *    ##    ######    ######     ##
     *    ##    ##             ##    ##
     *    ##    ##       ##    ##    ##
     *    ##    ########  ######     ##
     */
    public static final String FONT_BANNER3 = "banner3";

    /**
     * .########.########..######..########
     * ....##....##.......##....##....##...
     * ....##....##.......##..........##...
     * ....##....######....######.....##...
     * ....##....##.............##....##...
     * ....##....##.......##....##....##...
     * ....##....########..######.....##...
     */
    public static final String FONT_BANNER4 = "banner4";

    /**
     *   .                   .
     *  _/_     ___    ____ _/_
     *   |    .'   `  (      |
     *   |    |----'  `--.   |
     *   \__/ `.___, \___.'  \__/
     *
     */
    public static final String FONT_BELL = "bell";

    /**
     *    _     _     _     _
     *   / \   / \   / \   / \
     *  ( t ) ( e ) ( s ) ( t )
     *   \_/   \_/   \_/   \_/
     */
    public static final String FONT_BUBBLE = "bubble";

    /**
     * .%%%%%%..%%%%%%...%%%%...%%%%%%.
     * ...%%....%%......%%........%%...
     * ...%%....%%%%.....%%%%.....%%...
     * ...%%....%%..........%%....%%...
     * ...%%....%%%%%%...%%%%.....%%...
     * ................................
     */
    public static final String FONT_CONTRAST = "contrast";

    /**
     * ___ ____ ____ ___
     *  |  |___ [__   |
     *  |  |___ ___]  |
     *
     */
    public static final String FONT_CYBERMEDIUM = "cybermedium";

    /**
     *  +-+ +-+ +-+ +-+
     *  |t| |e| |s| |t|
     *  +-+ +-+ +-+ +-+
     */
    public static final String FONT_DIGITAL = "digital";

    /**
     *
     *
     *          tttt                                                        tttt
     *       ttt:::t                                                     ttt:::t
     *       t:::::t                                                     t:::::t
     *       t:::::t                                                     t:::::t
     * ttttttt:::::ttttttt        eeeeeeeeeeee        ssssssssss   ttttttt:::::ttttttt
     * t:::::::::::::::::t      ee::::::::::::ee    ss::::::::::s  t:::::::::::::::::t
     * t:::::::::::::::::t     e::::::eeeee:::::eess:::::::::::::s t:::::::::::::::::t
     * tttttt:::::::tttttt    e::::::e     e:::::es::::::ssss:::::stttttt:::::::tttttt
     *       t:::::t          e:::::::eeeee::::::e s:::::s  ssssss       t:::::t
     *       t:::::t          e:::::::::::::::::e    s::::::s            t:::::t
     *       t:::::t          e::::::eeeeeeeeeee        s::::::s         t:::::t
     *       t:::::t    tttttte:::::::e           ssssss   s:::::s       t:::::t    tttttt
     *       t::::::tttt:::::te::::::::e          s:::::ssss::::::s      t::::::tttt:::::t
     *       tt::::::::::::::t e::::::::eeeeeeee  s::::::::::::::s       tt::::::::::::::t
     *         tt:::::::::::tt  ee:::::::::::::e   s:::::::::::ss          tt:::::::::::tt
     *           ttttttttttt      eeeeeeeeeeeeee    sssssssssss              ttttttttttt
     *
     *
     *
     *
     *
     *
     *
     */
    public static final String FONT_DOH = "doh";

    /**
     * _________ _______  _______ _________
     * \__   __/(  ____ \(  ____ \\__   __/
     *    ) (   | (    \/| (    \/   ) (
     *    | |   | (__    | (_____    | |
     *    | |   |  __)   (_____  )   | |
     *    | |   | (            ) |   | |
     *    | |   | (____/\/\____) |   | |
     *    )_(   (_______/\_______)   )_(
     *
     */
    public static final String FONT_EPIC = "epic";

    /**
     *       ___           ___           ___           ___
     *      /\  \         /\  \         /\  \         /\  \
     *      \:\  \       /::\  \       /::\  \        \:\  \
     *       \:\  \     /:/\:\  \     /:/\ \  \        \:\  \
     *       /::\  \   /::\~\:\  \   _\:\~\ \  \       /::\  \
     *      /:/\:\__\ /:/\:\ \:\__\ /\ \:\ \ \__\     /:/\:\__\
     *     /:/  \/__/ \:\~\:\ \/__/ \:\ \:\ \/__/    /:/  \/__/
     *    /:/  /       \:\ \:\__\    \:\ \:\__\     /:/  /
     *    \/__/         \:\ \/__/     \:\/:/  /     \/__/
     *                   \:\__\        \::/  /
     *                    \/__/         \/__/
     */
    public static final String FONT_ISOMETRIC1 = "isometric1";

    /**
     *                     ___           ___
     *                    /\__\         /\__\
     *       ___         /:/ _/_       /:/ _/_         ___
     *      /\__\       /:/ /\__\     /:/ /\  \       /\__\
     *     /:/  /      /:/ /:/ _/_   /:/ /::\  \     /:/  /
     *    /:/__/      /:/_/:/ /\__\ /:/_/:/\:\__\   /:/__/
     *   /::\  \      \:\/:/ /:/  / \:\/:/ /:/  /  /::\  \
     *  /:/\:\  \      \::/_/:/  /   \::/ /:/  /  /:/\:\  \
     *  \/__\:\  \      \:\/:/  /     \/_/:/  /   \/__\:\  \
     *       \:\__\      \::/  /        /:/  /         \:\__\
     *        \/__/       \/__/         \/__/           \/__/
     */
    public static final String FONT_ISOMETRIC2 = "isometric2";

    /**
     *                   ___           ___
     *       ___        /  /\         /  /\          ___
     *      /  /\      /  /:/_       /  /:/_        /  /\
     *     /  /:/     /  /:/ /\     /  /:/ /\      /  /:/
     *    /  /:/     /  /:/ /:/_   /  /:/ /::\    /  /:/
     *   /  /::\    /__/:/ /:/ /\ /__/:/ /:/\:\  /  /::\
     *  /__/:/\:\   \  \:\/:/ /:/ \  \:\/:/~/:/ /__/:/\:\
     *  \__\/  \:\   \  \::/ /:/   \  \::/ /:/  \__\/  \:\
     *       \  \:\   \  \:\/:/     \__\/ /:/        \  \:\
     *        \__\/    \  \::/        /__/:/          \__\/
     *                  \__\/         \__\/
     */
    public static final String FONT_ISOMETRIC3 = "isometric3";

    /**
     *                     ___           ___
     *       ___          /  /\         /  /\          ___
     *      /__/\        /  /::\       /  /::\        /__/\
     *      \  \:\      /  /:/\:\     /__/:/\:\       \  \:\
     *       \__\:\    /  /::\ \:\   _\_ \:\ \:\       \__\:\
     *       /  /::\  /__/:/\:\ \:\ /__/\ \:\ \:\      /  /::\
     *      /  /:/\:\ \  \:\ \:\_\/ \  \:\ \:\_\/     /  /:/\:\
     *     /  /:/__\/  \  \:\ \:\    \  \:\_\:\      /  /:/__\/
     *    /__/:/        \  \:\_\/     \  \:\/:/     /__/:/
     *    \__\/          \  \:\        \  \::/      \__\/
     *                    \__\/         \__\/
     */
    public static final String FONT_ISOMETRIC4 = "isometric4";

    /**
     *  _               _
     * | |_   ___  ___ | |_
     * | __| / _ \/ __|| __|
     * | |_ |  __/\__ \| |_
     *  \__| \___||___/ \__|
     *
     */
    public static final String FONT_OGRE = "ogre";

    /**
     *
     * @@@@@@@  @@@@@@@@   @@@@@@   @@@@@@@
     * @@@@@@@  @@@@@@@@  @@@@@@@   @@@@@@@
     *   @@!    @@!       !@@         @@!
     *   !@!    !@!       !@!         !@!
     *   @!!    @!!!:!    !!@@!!      @!!
     *   !!!    !!!!!:     !!@!!!     !!!
     *   !!:    !!:            !:!    !!:
     *   :!:    :!:           !:!     :!:
     *    ::     :: ::::  :::: ::      ::
     *    :     : :: ::   :: : :       :
     *
     */
    public static final String FONT_POISON = "poison";

    /**
     *   |                   |
     *   __|    _ \    __|   __|
     *   |      __/  \__ \   |
     *  \__|  \___|  ____/  \__|
     *
     */
    public static final String FONT_SHADOW = "shadow";

    /**
     *    __                  __
     *   / /_  ___    _____  / /_
     *  / __/ / _ \  / ___/ / __/
     * / /_  /  __/ (__  ) / /_
     * \__/  \___/ /____/  \__/
     *
     */
    public static final String FONT_SLANT = "slant";

    /**
     *  ____  ____  ____  ____
     * ||t ||||e ||||s ||||t ||
     * ||__||||__||||__||||__||
     * |/__\||/__\||/__\||/__\|
     */
    public static final String FONT_SMKEYBOARD = "smkeyboard";

    /**
     * _____               _____
     * __  /______ __________  /_
     * _  __/_  _ \__  ___/_  __/
     * / /_  /  __/_(__  ) / /_
     * \__/  \___/ /____/  \__/
     *
     */
    public static final String FONT_SPEED = "speed";

    /**
     * .___________. _______      _______..___________.
     * |           ||   ____|    /       ||           |
     * `---|  |----`|  |__      |   (----``---|  |----`
     *     |  |     |   __|      \   \        |  |
     *     |  |     |  |____ .----)   |       |  |
     *     |__|     |_______||_______/        |__|
     *
     */
    public static final String FONT_STARWARS = "starwars";

    /**
     *
     * |              |
     * |--- ,---.,---.|---
     * |    |---'`---.|
     * `---'`---'`---'`---'
     *
     */
    public static final String FONT_THIN = "thin";

    /**
     * Get banner text from <a href="https://www.bootschool.net/ascii">BootSchool<a/>
     * @param asciiFont 字体
     * @param asciiWord 输入
     * @return          SpringBoot Banner
     */
    public static String genBannerOnline(String asciiFont, String asciiWord) {
        String bannerUrl = "https://www.bootschool.net/ascii";
        try {
            Map<String, Object> params = Maps.newHashMap();
            params.put("asciiFont", asciiFont);
            params.put("asciiWord", asciiWord);

            String responseStr = HttpService.post(bannerUrl, params);

            String regex = "<pre id=\"asciiResult\">([\\s\\S]+)<\\/pre>";
            return "\n" + RegexUtils.group(responseStr, regex);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}

















