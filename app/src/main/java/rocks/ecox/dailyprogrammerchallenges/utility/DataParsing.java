package rocks.ecox.dailyprogrammerchallenges.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import timber.log.Timber;

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
                Timber.e("%s threw the exception: %s", cnm.group().substring(1), e);
            }

            Timber.d("Cleaned challenge number: %s", challengeNumber);
        } else {
            Timber.w("Couldn't find challenge number in: %s.", title);
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
            Timber.w("Can't find a difficulty in: %s", lowerTitle);
        }

        Timber.d("Difficulty: %s", challengeDifficulty);

        return challengeDifficulty;
    }

    static String getCleanPostTitle(String title) {
        String cleanPostTitle = "";
        final String titlePattern = "([^\\]]+)$";
        final Pattern tp = Pattern.compile(titlePattern);
        Matcher tm = tp.matcher(title);

        if (tm.find()) {
            cleanPostTitle = tm.group().substring(1);
            Timber.d("Cleaned Title: %s", cleanPostTitle);
        } else {
            Timber.w("Couldn't clean post title: %s", title);
        }

        return cleanPostTitle;
    }

}
