package rocks.ecox.dailyprogrammerchallenges.utility;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataParsing {

    static Integer getChallengeNumber(String title) {
        Integer challengeNumber = 0;
        final String challengeNumberPattern = "([\\?]*)#(\\d*)";
        final Pattern cnp = Pattern.compile(challengeNumberPattern);
        Matcher cnm = cnp.matcher(title);

        if (cnm.find()) {
            try {
                challengeNumber = Integer.parseInt(cnm.group().substring(1));
            } catch (NumberFormatException e) {
                Log.e("ERROR", e + " " + cnm.group().substring(1));
            }

            Log.d("REGEX", "Challenge Number: " + challengeNumber);
        } else {
            Log.w("WARNING", "Couldn't find challenge number! " + title);
        }
        return challengeNumber;
    }

    static String getChallengeDifficulty(String title) {
        String challengeDifficulty = "Not a valid challenge";
        String lowerTitle = title.toLowerCase();

        if (lowerTitle.contains("easy")) {
            challengeDifficulty = "Easy";
        } else if (lowerTitle.contains("intermediate")) {
            challengeDifficulty = "Intermediate";
        } else if (lowerTitle.contains("hard")) {
            challengeDifficulty = "Hard";
        } else {
            Log.d("REGEX", "Difficulty: " + "No difficulty" + " " + lowerTitle);
        }

        Log.d("REGEX", "Difficulty: " + challengeDifficulty);

        return challengeDifficulty;
    }

    static String getCleanPostTitle(String title) {
        String cleanPostTitle = "";
        final String titlePattern = "([^\\]]+)$";
        final Pattern tp = Pattern.compile(titlePattern);
        Matcher tm = tp.matcher(title);

        if (tm.find()) {
            cleanPostTitle = tm.group().substring(1);
            Log.d("REGEX", "Cleaned Title: " + cleanPostTitle);
        } else {
            Log.w("WARNING", "Couldn't clean post title! " + title);
        }

        return cleanPostTitle;
    }

}
