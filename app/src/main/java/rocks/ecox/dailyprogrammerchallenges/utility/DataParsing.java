package rocks.ecox.dailyprogrammerchallenges.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.crashlytics.android.Crashlytics;

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
                Crashlytics.logException(e);
                Timber.e("%s threw the exception: %s", cnm.group().substring(1), e);
            }
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
            challengeDifficulty = "Medium";
        } else if (lowerTitle.contains("hard")) {
            challengeDifficulty = "Hard";
        } else {
            Timber.w("Can't find a difficulty in: %s", lowerTitle);
        }

        return challengeDifficulty;
    }

    static int getChallengeDifficultyNumber(String difficulty) {
        int difficultyNumber = 0;

        if (difficulty.contains("Easy")) {
            difficultyNumber = 1;
        } else if (difficulty.contains("Medium")) {
            difficultyNumber = 2;
        } else if (difficulty.contains("Hard")) {
            difficultyNumber = 3;
        } else {
            Timber.w("Can't find a difficulty number for: %s", difficulty);
        }

        return difficultyNumber;
    }

    static String getCleanPostTitle(String title) {
        String cleanPostTitle = "";
        final String titlePattern = "([^\\]]+)$";
        final Pattern tp = Pattern.compile(titlePattern);
        Matcher tm = tp.matcher(title);

        if (tm.find()) {
            cleanPostTitle = tm.group().substring(1);
        } else {
            Timber.w("Couldn't clean post title: %s", title);
        }

        return cleanPostTitle;
    }

    public static void setPageNum(int position, Context c) {
        SharedPreferences currentPage = c.getSharedPreferences("PageNum", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = currentPage.edit();
        editor.putString("PageNum", String.valueOf(position));
        editor.apply();
    }

    public static String getPageNum(Context c) {
        SharedPreferences currentPage = c.getSharedPreferences("PageNum", Context.MODE_PRIVATE);
        return currentPage.getString("PageNum", "0");
    }

}
