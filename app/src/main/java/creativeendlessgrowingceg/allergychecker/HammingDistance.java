package creativeendlessgrowingceg.allergychecker;

import creativeendlessgrowingceg.allergychecker.bktree.Metric;

/**
 * @author Jonathan Alexander Norberg
 * @version 2018-04-07
 */

public class HammingDistance {
    private static final String TAG = "HammingDistance";
    /**
     * Hamming distance for the word
     *
     * distance from a word to another depending on their length and char differences.
     *
     */
    public static Metric<String> hammingDistance = new Metric<String>() {
        @Override
        public int distance(String x, String y) {
           /* if (!(x.length() != y.length() || x.length() - 1 != y.length() || x.length() + 1 != y.length())) {
                throw new IllegalArgumentException();
            }*/
            if(x.length() == 0 || y.length() == 0){

                return y.length();
            }
            //Log.d(TAG, x + " : " + y);
            int distance = 0;
            if (y.length() > x.length()) {
                int before = 0;
                for (int i = 0; i < y.length() - 1; i++) {
                    //Log.d(TAG, "distance: "+ i + " : " + (i+before) +x.length());
                    if(i == x.length()-1 || i+before >= y.length()){

                        distance += y.length() - x.length();
                        break;
                    }
                    if (x.charAt(i) != y.charAt(i+before)) {
                        distance++;
                        before++;
                        i--;
                    }
                }
            } else if (y.length() < x.length()) {
                for (int i = 0; i < x.length() - 1; i++) {

                    if(i == x.length() || i == y.length()){

                        break;
                    }
                    if (x.charAt(i) != y.charAt(i)) {
                        distance++;
                    }
                }
                distance += x.length() - y.length();
            } else {
                for (int i = 0; i < x.length(); i++) {
                    if(i == x.length() || i == y.length()){
                        break;
                    }
                    if (x.charAt(i) != y.charAt(i)) {
                        distance++;
                    }
                }
            }
            //Log.d(TAG, "distance: "+ distance + " : "+ x + " : " + y);
            return distance;
        }
    };

}
