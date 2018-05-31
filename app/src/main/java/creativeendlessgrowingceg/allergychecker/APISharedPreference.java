package creativeendlessgrowingceg.allergychecker;

/**
 * @author Jonathan Alexander Norberg
 * @version 2018-05-16
 */

public class APISharedPreference {
    public static String getScannedText = "ScannedText";
    public static String getHistory = "History";
    public static String dateAndHistory = "DateAndHistory";
    public static String theme = "Theme";
    public static String interstitial = "Interstitial";
    public static String experimental = "Experimental";
    public static String timesScanned = "TimesScanned";

    public static String getFlash(){
        return "Flash";
    }
    public static String getFocus(){
        return "Focus";
    }
    public static String getTimeSleep(){
        return "TimeSleep";
    }
    public static String getFirstTime(){
        return "FirstTime";
    }

    public static String getWordCount(){
        return "StatisticWordCount";
    }
    public static String getFoundCount(){
        return "StatisticFoundCount";
    }
    public static String getSpinnerPosition(){
        return "SpinnerPosition";
    }

    public static String getFoundENumbers() {
        return  "FoundENumbers";
    }
}
